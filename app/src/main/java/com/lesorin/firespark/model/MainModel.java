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
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class MainModel implements MainContract.Model
{
    private MainContract.PresenterModel _presenter;
    private final FirebaseAuth _firebaseAuth;
    private final FirebaseFirestore _firestore;
    private final HashMap<String, Spark> _sparksCache;
    private final HashMap<String, User> _usersCache;
    private final HashMap<String, Comment> _commentsCache;

    public MainModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
        _sparksCache = new HashMap<>();
        _usersCache = new HashMap<>();
        _commentsCache = new HashMap<>();
    }

    public void setPresenter(MainContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void requestLogout()
    {
        _firebaseAuth.signOut();
    }

    @Override
    public void requestProfileData(String userId)
    {
        if(userId == null)
            userId = _firebaseAuth.getUid();

        _firestore.collection(COLLECTION_USERS).document(userId).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                User user = updateUsersCache(createUserFromDocumentSnapshot(task.getResult()));

                //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
                _firestore.collection(COLLECTION_SPARKS).whereEqualTo(SPARK_OWNERID, user.getId()).
                        whereEqualTo(SPARK_DELETED, false).orderBy(SPARK_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task2 ->
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

    private User updateUsersCache(User user)
    {
        User userInCache = _usersCache.get(user.getId());

        //If it is in the cache, then update it with the latest data read
        //from the database, because the cache might contain old values.
        if(userInCache != null)
        {
            userInCache.update(user);
        }
        else //If the user is not in cache, insert it.
        {
            _usersCache.put(user.getId(), user);

            userInCache = user;
        }

        return userInCache;
    }

    @Override
    public void requestHomeData()
    {
        //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
        _firestore.collection(COLLECTION_SPARKS).whereArrayContains(SPARK_SUBSCRIBERS, _firebaseAuth.getCurrentUser().getUid()).
                whereEqualTo(SPARK_DELETED, false).orderBy(SPARK_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task ->
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

                _presenter.responseHomeDataSuccess(sparks);
            }
            else
            {
                _presenter.responseHomeDataFailure();
            }
        });
    }

    //Inserts the parameter spark into the cache map.
    //If a spark with the same id as the parameter already exists in the cache map, then the map spark
    //is updated and returned.
    //If a spark with the same id as the parameter is not contained in the cache map, then the
    //parameter is inserted into the map and returned.
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
        _presenter.requestPopularDataSuccess(new ArrayList<>());
    }

    @Override
    public void requestSendSpark(String sparkBody)
    {
        String userId = _firebaseAuth.getCurrentUser().getUid();

        _firestore.collection(COLLECTION_USERS).document(userId).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                User user = updateUsersCache(createUserFromDocumentSnapshot(task.getResult()));
                HashMap<String, Object> toInsert = createSparkMapForInserting(sparkBody, user);

                _firestore.collection(COLLECTION_SPARKS).add(toInsert).addOnCompleteListener(task2 ->
                {
                    if(task2.isSuccessful())
                    {
                        task2.getResult().get().addOnCompleteListener(task3 ->
                        {
                            if(task3.isSuccessful())
                            {
                                Spark spark = createSparkFromDocumentSnapshot(task3.getResult());

                                spark = updateSparksCache(spark);

                                _presenter.responseSendSparkSuccess(spark);
                            }
                            else
                            {
                                _presenter.responseSendSparkFailure();
                            }
                        });
                    }
                    else
                    {
                        _presenter.responseSendSparkFailure();
                    }
                });
            }
            else
            {
                _presenter.responseSendSparkFailure();
            }
        });
    }

    @Override
    public void requestDeleteSpark(Spark spark)
    {
        if(spark.isOwnedByCurrentUser())
        {
            HashMap<String, Object> updateFields = new HashMap<>();

            updateFields.put(SPARK_DELETED, true);

            _firestore.collection(COLLECTION_SPARKS).document(spark.getId()).update(updateFields).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    _sparksCache.remove(spark.getId());
                    _presenter.responseDeleteSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseDeleteSparkFailure();
                }
            });
        }
        else
        {
            _presenter.responseDeleteSparkFailure();
        }
    }

    @Override
    public void requestLikeDislikeSpark(Spark spark)
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
    public void requestFollowUnfollowUser(User user)
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
    public void requestSearchUserByUsername(String userName)
    {
        if(!userName.isEmpty())
        {
            _firestore.collection(COLLECTION_USERS).whereEqualTo(USER_USERNAMEINSENSITIVE, userName.toLowerCase()).
                get().addOnCompleteListener(task ->
                {
                    if(task.isSuccessful())
                    {
                        if(task.getResult().size() >= 1)
                        {
                            User user = updateUsersCache(createUserFromDocumentSnapshot(task.getResult().getDocuments().get(0)));

                            //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
                            _firestore.collection(COLLECTION_SPARKS).whereEqualTo(SPARK_OWNERID, user.getId()).
                                whereEqualTo(SPARK_DELETED, false).orderBy(SPARK_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task2 ->
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

    @Override
    public void requestSparkData(Spark spark)
    {
        //todo probably need to limit this query in the future, and figure out how to keep requesting data after the limit
        _firestore.collection(COLLECTION_COMMENTS).whereEqualTo(COMMENT_SPARKID, spark.getId()).
                whereEqualTo(COMMENT_DELETED, false).orderBy(COMMENT_CREATED, Query.Direction.DESCENDING).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                ArrayList<Comment> comments = new ArrayList<>();

                for(QueryDocumentSnapshot document : task.getResult())
                {
                    Comment comment = createCommentFromDocumentSnapshot(document);

                    comments.add(comment);
                }

                _presenter.requestSparkDataSuccess(spark, comments);
            }
            else
            {
                _presenter.requestSparkDataFailure();
            }
        });

    }

    @Override
    public void requestSendComment(Spark spark, String commentBody, Comment replyComment)
    {
        _firestore.collection(COLLECTION_USERS).document(_firebaseAuth.getUid()).get().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                User currentUser = updateUsersCache(createUserFromDocumentSnapshot(task.getResult()));
                HashMap<String, Object> toInsert = createCommentMapForInserting(currentUser, spark, commentBody, replyComment);

                _firestore.collection(COLLECTION_COMMENTS).add(toInsert).addOnCompleteListener(task2 ->
                {
                    if(task2.isSuccessful())
                    {
                        task2.getResult().get().addOnCompleteListener(task3 ->
                        {
                            if(task3.isSuccessful())
                            {
                                Comment comment = updateCommentsCache(createCommentFromDocumentSnapshot(task3.getResult()));

                                spark.addCommentFromUser(currentUser.getId());
                                spark.setContainsCommentFromCurrentUser(true);

                                HashMap<String, Object> updateFields = new HashMap<>();

                                updateFields.put(SPARK_COMMENTS, spark.getComments());

                                _firestore.collection(COLLECTION_SPARKS).document(spark.getId()).update(updateFields).
                                    addOnCompleteListener(task4 ->
                                    {
                                        if(task4.isSuccessful())
                                        {
                                            _presenter.requestSendCommentSuccess(comment);
                                        }
                                        else
                                        {
                                            spark.removeOneCommentFromUser(currentUser.getId());
                                            spark.setContainsCommentFromCurrentUser(spark.containsCommentFromUser(currentUser.getId()));
                                            _presenter.requestSendCommentFailure();
                                        }
                                    });
                            }
                            else
                            {
                                _presenter.requestSendCommentFailure();
                            }
                        });
                    }
                    else
                    {
                        _presenter.requestSendCommentFailure();
                    }
                });
            }
            else
            {
                _presenter.requestSendCommentFailure();
            }
        });
    }

    private Comment updateCommentsCache(Comment comment)
    {
        Comment commentInCache = _commentsCache.get(comment.getId());

        //If it is in the cache, then update it with the latest data read
        //from the database, because the cache might contain old values.
        if(commentInCache != null)
        {
            commentInCache.update(comment);
        }
        else //If the comment is not in cache, insert it.
        {
            _commentsCache.put(comment.getId(), comment);

            commentInCache = comment;
        }

        return commentInCache;
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
        DocumentReference currentUserRef = _firestore.collection(COLLECTION_USERS).document(currentUserId);
        DocumentReference otherUserRef = _firestore.collection(COLLECTION_USERS).document(otherUserId);

        batch.update(currentUserRef, currentUserUpdate);
        batch.update(otherUserRef, otherUserUpdate);

        batch.commit().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                user.setFollowedByCurrentUser(true);
                user.getFollowers().add(currentUserId);

                //Also update the current user if they are in the cache.
                User currentUser = _usersCache.get(currentUserId);

                if(currentUser != null)
                {
                    currentUser.getFollowing().add(otherUserId);
                }

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
        DocumentReference currentUserRef = _firestore.collection(COLLECTION_USERS).document(currentUserId);
        DocumentReference otherUserRef = _firestore.collection(COLLECTION_USERS).document(otherUserId);

        batch.update(currentUserRef, currentUserUpdate);
        batch.update(otherUserRef, otherUserUpdate);

        batch.commit().addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                user.setFollowedByCurrentUser(false);
                user.getFollowers().remove(currentUserId);

                //Also update the current user if they are in the cache.
                User currentUser = _usersCache.get(currentUserId);

                if(currentUser != null)
                {
                    currentUser.getFollowing().remove(otherUserId);
                }

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

        _firestore.collection(COLLECTION_SPARKS).document(sparkId).update(updateFields).
            addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    spark.getLikes().add(userId);
                    spark.setLikedByCurrentUser(true);
                    _presenter.responseLikeSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseLikeSparkFailure();
                }
            });
    }

    private void removeLikeFromSpark(Spark spark)
    {
        String sparkId = spark.getId();
        String userId = _firebaseAuth.getUid();
        HashMap<String, Object> updateFields = new HashMap<>();

        updateFields.put(SPARK_LIKES, FieldValue.arrayRemove(userId));

        _firestore.collection(COLLECTION_SPARKS).document(sparkId).update(updateFields).
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
        ret.put(SPARK_DELETED, false);
        ret.put(SPARK_LIKES, Arrays.asList());
        ret.put(SPARK_COMMENTS, Arrays.asList());

        ArrayList<String> subscribers = user.getFollowers();

        //Need to add the current user to the subscribers list so they can too be able to view
        //their own sparks on the home feed.
        subscribers.add(_firebaseAuth.getCurrentUser().getUid());

        ret.put(SPARK_SUBSCRIBERS, subscribers);

        return ret;
    }

    private HashMap<String, Object> createCommentMapForInserting(User currentUser, Spark spark, String commentBody, Comment replyComment)
    {
        HashMap<String, Object> ret = new HashMap<>();

        ret.put(COMMENT_SPARKID, spark.getId());
        ret.put(COMMENT_OWNERID, currentUser.getId());
        ret.put(COMMENT_OWNERFIRSTLASTNAME, currentUser.getFirstlastname());
        ret.put(COMMENT_OWNERUSERNAME, currentUser.getUsername());
        ret.put(COMMENT_BODY, commentBody);
        ret.put(COMMENT_CREATED, FieldValue.serverTimestamp());
        ret.put(COMMENT_DELETED, false);
        ret.put(COMMENT_LIKES, Arrays.asList());

        if(replyComment != null)
        {
            ret.put(COMMENT_REPLYTOFIRSTLASTNAME, replyComment.getOwnerFirstLastName());
            ret.put(COMMENT_REPLYTOUSERNAME, replyComment.getOwnerUsername());
        }

        return ret;
    }

    private Spark createSparkFromDocumentSnapshot(DocumentSnapshot document)
    {
        Spark spark = document.toObject(Spark.class);

        spark.setId(document.getId());
        spark.setOwnedByCurrentUser(spark.getOwnerId().equals(_firebaseAuth.getUid()));
        spark.setLikedByCurrentUser(spark.getLikes().contains(_firebaseAuth.getUid()));
        spark.setContainsCommentFromCurrentUser(spark.getComments().contains(_firebaseAuth.getUid()));

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

    private Comment createCommentFromDocumentSnapshot(DocumentSnapshot document)
    {
        Comment comment = document.toObject(Comment.class);

        comment.setId(document.getId());
        comment.setOwnedByCurrentUser(comment.getOwnerId().equals(_firebaseAuth.getUid()));
        comment.setLikedByCurrentUser(comment.getLikes().contains(_firebaseAuth.getUid()));

        return comment;
    }
}