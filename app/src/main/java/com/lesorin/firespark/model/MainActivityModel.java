package com.lesorin.firespark.model;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivityModel implements MainActivityContract.Model
{
    private MainActivityContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;
    private FirebaseFirestore _firestore;
    private HashMap<String, Spark> _sparksCache;

    public MainActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
        _sparksCache = new HashMap<>();
    }

    public void setPresenter(MainActivityContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void logUserOut()
    {
        _firebaseAuth.signOut();
    }

    @Override
    public void requestProfileData(String userId)
    {
        if(userId == null)
            userId = _firebaseAuth.getUid();

        _firestore.collection(USERS_COLLECTION).document(userId).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                User user = createUserFromDocumentSnapshot(task.getResult());

                //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
                _firestore.collection(SPARKS_COLLECTION).whereEqualTo(SPARK_OWNERID, user.getId()).
                        whereEqualTo(SPARK_ISDELETED, false).orderBy(SPARK_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task2 ->
                {
                    if(task2.isSuccessful())
                    {
                        ArrayList<Spark> sparks = new ArrayList<>();

                        for(QueryDocumentSnapshot document : task2.getResult())
                        {
                            Spark spark = createSparkFromDocumentSnapshot(document);

                            spark = updateSparksCache(spark);

                            sparks.add(spark);
                        }

                        _presenter.requestProfileDataSuccess(user, sparks);
                    }
                    else
                    {
                        _presenter.requestProfileDataFailure();
                    }
                });
            }
            else
            {
                _presenter.requestProfileDataFailure();
            }
        });
    }

    @Override
    public void requestHomeData()
    {
        //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
        _firestore.collection(SPARKS_COLLECTION).whereArrayContains(SPARK_SUBSCRIBERS, _firebaseAuth.getCurrentUser().getUid()).
                whereEqualTo(SPARK_ISDELETED, false).orderBy(SPARK_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                ArrayList<Spark> sparks = new ArrayList<>();

                for(QueryDocumentSnapshot document : task.getResult())
                {
                    Spark spark = createSparkFromDocumentSnapshot(document);

                    spark = updateSparksCache(spark);

                    sparks.add(spark);
                }

                _presenter.homeDataAcquired(sparks);
            }
        });
    }

    //Inserts the parameter spark into the cache map.
    //If a spark with the same id as the parameter spark already exists in the cache map, then the map spark
    //is updated and returned.
    //If a spark with the same id as the parameter spark is not contained in the cache map, then the
    //parameter map is inserted into the map and returned.
    private Spark updateSparksCache(Spark spark)
    {
        Spark sparkInMap = _sparksCache.get(spark.getId());

        //If it is in the cache, then update it with the latest data read
        //from the database, because the cache might contain old values.
        if(sparkInMap != null)
        {
           sparkInMap.update(spark);
        }
        else //If the spark is not in cache, insert it.
        {
            _sparksCache.put(spark.getId(), spark);

            sparkInMap = spark;
        }

        return sparkInMap;
    }

    @Override
    public void requestPopularData()
    {
        //todo get real data
    }

    @Override
    public String getUserName()
    {
        return _firebaseAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public void sendSpark(String sparkBody)
    {
        String userId = _firebaseAuth.getCurrentUser().getUid();

        _firestore.collection(USERS_COLLECTION).document(userId).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                DocumentSnapshot ds = task.getResult();
                User user = createUserFromDocumentSnapshot(ds);
                HashMap<String, Object> toInsert = createSparkMapForInserting(sparkBody, user);

                _firestore.collection(SPARKS_COLLECTION).add(toInsert).addOnCompleteListener(task2 ->
                {
                    if(task2.isSuccessful())
                    {
                        _firestore.collection(SPARKS_COLLECTION).document(task2.getResult().getId()).
                                get().addOnCompleteListener(task3 ->
                        {
                            if(task3.isSuccessful())
                            {
                                Spark spark = createSparkFromDocumentSnapshot(task3.getResult());

                                spark = updateSparksCache(spark);

                                _presenter.sendSparkResult(spark);
                            }
                            else
                            {
                                _presenter.sendSparkResult(null);
                            }
                        });
                    }
                    else
                    {
                        _presenter.sendSparkResult(null);
                    }
                });
            }
            else
            {
                _presenter.sendSparkResult(null);
            }
        });

    }

    @Override
    public void deleteSpark(Spark spark)
    {
        if(spark.isOwnedByCurrentUser())
        {
            HashMap<String, Object> updateFields = new HashMap<>();

            updateFields.put(SPARK_ISDELETED, true);

            _firestore.collection(SPARKS_COLLECTION).document(spark.getId()).update(updateFields).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    _sparksCache.remove(spark.getId());
                    _presenter.deleteSparkSuccess(spark);
                }
                else
                {
                    _presenter.deleteSparkFailure();
                }
            });
        }
        else
        {
            _presenter.deleteSparkFailure();
        }
    }

    @Override
    public void likeDislikeSpark(Spark spark)
    {
        String userId = _firebaseAuth.getUid();

        if(spark.getLikes().contains(userId)) //Current user already likes this spark, so remove their like.
        {
            removeLikeFromSpark(spark);
        }
        else
        {
            addLikeToSpark(spark);
        }
    }

    @Override
    public void followUnfollowUser(User user)
    {
        String userId = _firebaseAuth.getUid();

        if(user.getFollowers().contains(userId)) //Current user is following the other user: unfollow them.
        {
            unfollowUser(user);
        }
        else //Current user is not following the other user: follow them.
        {
            followUser(user);
        }
    }

    @Override
    public void searchUserByUsername(String userName)
    {
        if(!userName.isEmpty())
        {
            _firestore.collection(USERS_COLLECTION).whereEqualTo(USER_USERNAMEINSENSITIVE, userName.toLowerCase()).
                get().addOnCompleteListener(task ->
                {
                    if(task.isSuccessful())
                    {
                        if(task.getResult().size() >= 1)
                        {
                            User user = createUserFromDocumentSnapshot(task.getResult().getDocuments().get(0));

                            //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
                            _firestore.collection(SPARKS_COLLECTION).whereEqualTo(SPARK_OWNERID, user.getId()).
                                whereEqualTo(SPARK_ISDELETED, false).orderBy(SPARK_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task2 ->
                                {
                                    if(task2.isSuccessful())
                                    {
                                        ArrayList<Spark> sparks = new ArrayList<>();

                                        for(QueryDocumentSnapshot document : task2.getResult())
                                        {
                                            Spark spark = createSparkFromDocumentSnapshot(document);

                                            spark = updateSparksCache(spark);

                                            sparks.add(spark);
                                        }

                                        _presenter.searchUserSuccess(user, sparks);
                                    }
                                    else
                                    {
                                        _presenter.searchUserFailure();
                                    }
                                });
                        }
                        else
                        {
                            _presenter.searchUserFailure();
                        }
                    }
                    else
                    {
                        _presenter.searchUserFailure();
                    }
                });
        }
        else
        {
            _presenter.searchUserFailure();
        }
    }

    private void followUser(User user)
    {
        String currentUserId = _firebaseAuth.getUid();
        String otherUserId = user.getId();
        HashMap<String, Object> currentUserUpdate = new HashMap<>();
        HashMap<String, Object> otherUserUpdate = new HashMap<>();

        //Add the other user to the current user's following list.
        currentUserUpdate.put(USER_FOLLOWING, FieldValue.arrayUnion(otherUserId));

        //Add the current user to the other user's followers list.
        otherUserUpdate.put(USER_FOLLOWERS, FieldValue.arrayUnion(currentUserId));

        WriteBatch batch = _firestore.batch();
        DocumentReference currentUserRef = _firestore.collection(USERS_COLLECTION).document(currentUserId);
        DocumentReference otherUserRef = _firestore.collection(USERS_COLLECTION).document(otherUserId);

        batch.update(currentUserRef, currentUserUpdate);
        batch.update(otherUserRef, otherUserUpdate);

        batch.commit().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                user.setFollowedByCurrentUser(true);
                user.getFollowers().add(currentUserId);
                _presenter.followUserSuccess(user);
            }
            else
            {
                _presenter.followUserFailure();
            }
        });
    }

    private void unfollowUser(User user)
    {
        String currentUserId = _firebaseAuth.getUid();
        String otherUserId = user.getId();
        HashMap<String, Object> currentUserUpdate = new HashMap<>();
        HashMap<String, Object> otherUserUpdate = new HashMap<>();

        //Add the other user to the current user's following list.
        currentUserUpdate.put(USER_FOLLOWING, FieldValue.arrayRemove(otherUserId));

        //Add the current user to the other user's followers list.
        otherUserUpdate.put(USER_FOLLOWERS, FieldValue.arrayRemove(currentUserId));

        WriteBatch batch = _firestore.batch();
        DocumentReference currentUserRef = _firestore.collection(USERS_COLLECTION).document(currentUserId);
        DocumentReference otherUserRef = _firestore.collection(USERS_COLLECTION).document(otherUserId);

        batch.update(currentUserRef, currentUserUpdate);
        batch.update(otherUserRef, otherUserUpdate);

        batch.commit().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                user.setFollowedByCurrentUser(false);
                user.getFollowers().remove(currentUserId);
                _presenter.unfollowUserSuccess(user);
            }
            else
            {
                _presenter.unfollowUserFailure();
            }
        });
    }

    private void addLikeToSpark(Spark spark)
    {
        String sparkId = spark.getId();
        String userId = _firebaseAuth.getUid();
        HashMap<String, Object> updateFields = new HashMap<>();

        updateFields.put(SPARK_LIKES, FieldValue.arrayUnion(userId));

        _firestore.collection(SPARKS_COLLECTION).document(sparkId).update(updateFields).
            addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    spark.getLikes().add(userId);
                    spark.setLikedByCurrentUser(true);
                    _presenter.addSparkLikeSuccess(spark);
                }
                else
                {
                    _presenter.addSparkLikeFailure(spark);
                }
            });
    }

    private void removeLikeFromSpark(Spark spark)
    {
        String sparkId = spark.getId();
        String userId = _firebaseAuth.getUid();
        HashMap<String, Object> updateFields = new HashMap<>();

        updateFields.put(SPARK_LIKES, FieldValue.arrayRemove(userId));

        _firestore.collection(SPARKS_COLLECTION).document(sparkId).update(updateFields).
            addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    spark.getLikes().remove(userId);
                    spark.setLikedByCurrentUser(false);
                    _presenter.removeSparkLikeSuccess(spark);
                }
                else
                {
                    _presenter.removeSparkLikeFailure(spark);
                }
            });
    }

    private HashMap<String, Object> createSparkMapForInserting(String sparkBody, User user)
    {
        HashMap<String, Object> ret = new HashMap<>();

        ret.put(SPARK_OWNERID, _firebaseAuth.getCurrentUser().getUid());
        ret.put(SPARK_OWNERFIRSTLASTNAME, user.getFirstlastname());
        ret.put(SPARK_OWNERUSERNAME, user.getUsername());
        ret.put(SPARK_BODY, sparkBody);
        ret.put(SPARK_CREATED, FieldValue.serverTimestamp());
        ret.put(SPARK_ISDELETED, false);
        ret.put(SPARK_LIKES, Arrays.asList());

        ArrayList<String> subscribers = user.getFollowers();

        //Need to add the current user to the subscribers list so they can too be able to view
        //their own sparks on the home feed.
        subscribers.add(_firebaseAuth.getCurrentUser().getUid());

        ret.put(SPARK_SUBSCRIBERS, subscribers);

        return ret;
    }

    private Spark createSparkFromDocumentSnapshot(DocumentSnapshot document)
    {
        Spark spark = document.toObject(Spark.class);

        spark.setId(document.getId());
        spark.setOwnedByCurrentUser(spark.getOwnerId().equals(_firebaseAuth.getUid()));
        spark.setLikedByCurrentUser(spark.getLikes().contains(_firebaseAuth.getUid()));

        return spark;
    }

    private User createUserFromDocumentSnapshot(DocumentSnapshot document)
    {
        User user = document.toObject(User.class);

        user.setId(document.getId());
        user.setCurrentUser(document.getId().equals(_firebaseAuth.getUid()));
        user.setFollowedByCurrentUser(user.getFollowers().contains(_firebaseAuth.getUid()));

        return user;
    }
}