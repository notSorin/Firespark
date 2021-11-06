package com.lesorin.firespark.model.firebase;

import static com.lesorin.firespark.model.firebase.ModelConstants.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.presenter.Comment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class MainModel implements MainContract.Model
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
        _presenter.responseLogoutSuccess();
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

                        _presenter.responseProfileDataSuccess(user, sparks);
                    }
                    else
                    {
                        _presenter.responseProfileDataFailure();
                    }
                });
            }
            else
            {
                _presenter.responseProfileDataFailure();
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
        //todo
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

    @Override
    public void requestFollowUser(User user) {

    }

    @Override
    public void requestUnfollowUser(User user) {

    }

    @Override
    public void requestSearchUser(String userName)
    {
        /*if(!userName.isEmpty())
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

                                        _presenter.responseSearchUserByUsernameSuccess(user, sparks);
                                    }
                                    else
                                    {
                                        _presenter.responseSearchUserByUsernameFailure();
                                    }
                                });
                        }
                        else
                        {
                            _presenter.responseSearchUserByUsernameFailure();
                        }
                    }
                    else
                    {
                        _presenter.responseSearchUserByUsernameFailure();
                    }
                });
        }
        else
        {
            _presenter.responseSearchUserByUsernameFailure();
        }*/
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

                _presenter.responseSparkDataSuccess(spark, comments);
            }
            else
            {
                _presenter.responseSparkDataFailure();
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

                                //fixme This is not correct because the spark's comments could be outdated.
                                spark.addCommentFromUser(currentUser.getId());
                                spark.setContainsCommentFromCurrentUser(true);

                                HashMap<String, Object> updateFields = new HashMap<>();

                                updateFields.put(SPARK_COMMENTS, spark.getComments());

                                _firestore.collection(COLLECTION_SPARKS).document(spark.getId()).update(updateFields).
                                    addOnCompleteListener(task4 ->
                                    {
                                        if(task4.isSuccessful())
                                        {
                                            _presenter.responseSendCommentSuccess(comment);
                                        }
                                        else
                                        {
                                            spark.removeOneCommentFromUser(currentUser.getId());
                                            spark.setContainsCommentFromCurrentUser(spark.containsCommentFromUser(currentUser.getId()));
                                            _presenter.responseSendCommentFailure();
                                        }
                                    });
                            }
                            else
                            {
                                _presenter.responseSendCommentFailure();
                            }
                        });
                    }
                    else
                    {
                        _presenter.responseSendCommentFailure();
                    }
                });
            }
            else
            {
                _presenter.responseSendCommentFailure();
            }
        });
    }

    @Override
    public void requestDeleteComment(Comment comment)
    {

    }

    @Override
    public void requestLikeSpark(Spark spark) {

    }

    @Override
    public void requestUnlikeSpark(Spark spark) {

    }

    @Override
    public void requestLikeComment(Comment comment) {

    }

    @Override
    public void requestUnlikeComment(Comment comment) {

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

                _presenter.responseFollowUserSuccess(user);
            }
            else
            {
                _presenter.responseFollowUserFailure();
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

                _presenter.responseUnfollowUserSuccess(user);
            }
            else
            {
                _presenter.responseUnfollowUserFailure();
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
                    _presenter.responseUnlikeSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseUnlikeSparkFailure();
                }
            });
    }

    private HashMap<String, Object> createSparkMapForInserting(String sparkBody, User user)
    {
        HashMap<String, Object> ret = new HashMap<>();

        ret.put(SPARK_OWNERID, _firebaseAuth.getCurrentUser().getUid());
        ret.put(SPARK_OWNERFIRSTLASTNAME, user.getFirstLastName());
        ret.put(SPARK_OWNERUSERNAME, user.getUsername());
        ret.put(SPARK_BODY, sparkBody);
        ret.put(SPARK_CREATED, FieldValue.serverTimestamp());
        ret.put(SPARK_DELETED, false);
        ret.put(SPARK_LIKES, Arrays.asList());
        ret.put(SPARK_COMMENTS, Arrays.asList());

        Set<String> subscribers = user.getFollowers();

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
        ret.put(COMMENT_OWNERFIRSTLASTNAME, currentUser.getFirstLastName());
        ret.put(COMMENT_OWNERUSERNAME, currentUser.getUsername());
        ret.put(COMMENT_BODY, commentBody);
        ret.put(COMMENT_CREATED, FieldValue.serverTimestamp());
        ret.put(COMMENT_DELETED, false);
        ret.put(COMMENT_LIKES, Arrays.asList());

        if(replyComment != null)
        {
            ret.put(COMMENT_REPLYTOFIRSTLASTNAME, replyComment.getUserFirstLastName());
            ret.put(COMMENT_REPLYTOUSERNAME, replyComment.getReplyToUsername());
        }

        return ret;
    }

    private FirebaseSpark createSparkFromDocumentSnapshot(DocumentSnapshot document)
    {
        FirebaseSpark spark = document.toObject(FirebaseSpark.class);

        spark.setId(document.getId());
        spark.setOwnedByCurrentUser(spark.getOwnerId().equals(_firebaseAuth.getUid()));
        spark.setLikedByCurrentUser(spark.getLikes().contains(_firebaseAuth.getUid()));
        spark.setContainsCommentFromCurrentUser(spark.getComments().contains(_firebaseAuth.getUid()));

        return spark;
    }

    private FirebaseUser createUserFromDocumentSnapshot(DocumentSnapshot document)
    {
        FirebaseUser user = document.toObject(FirebaseUser.class);

        user.setId(document.getId());
        user.setCurrentUser(document.getId().equals(_firebaseAuth.getUid()));
        user.setFollowedByCurrentUser(user.getFollowers().contains(_firebaseAuth.getUid()));

        return user;
    }

    private FirebaseComment createCommentFromDocumentSnapshot(DocumentSnapshot document)
    {
        FirebaseComment comment = document.toObject(FirebaseComment.class);

        comment.setId(document.getId());
        comment.setOwnedByCurrentUser(comment.getOwnerId().equals(_firebaseAuth.getUid()));
        comment.setLikedByCurrentUser(comment.getLikes().contains(_firebaseAuth.getUid()));

        return comment;
    }
}