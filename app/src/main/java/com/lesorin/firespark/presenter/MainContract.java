package com.lesorin.firespark.presenter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Interface for the Main Contract of the app. It defines the interfaces for the VIEW, PRESENTER, and MODEL.
 */
public interface MainContract
{
    /**
     * This interface defines the methods required to be implemented by any class which acts as a
     * PRESENTER for a VIEW in the MVP Architectural Pattern.
     * All methods starting with "request" are methods which can be initiated by the client user.
     */
    interface PresenterView
    {
        /**
         * Sets the view with which the presenter will interact.
         *
         * @param view Any @{@link View}.
         */
        void setView(View view);

        /**
         * Sets model with which the presenter will interact.
         *
         * @param model Any @{@link Model}.
         */
        void setModel(Model model);

        /**
         * Called when the app starts up in case the presenter needs to perform certain operations
         * at that time.
         */
        void appStarted();

        /**
         * Called when the user requests to log out.
         */
        void requestLogout();

        /**
         * Called when the user requests a profile's data.
         *
         * @param userId The id of the user whose profile data to retrieve.
         */
        void requestProfileData(String userId);

        /**
         * Called when the user requests the home data.
         */
        void requestHomeData();

        /**
         * Called when the user requests the popular data.
         */
        void requestPopularData();

        /**
         * Called when the user requests a spark's data (spark + its comments).
         *
         * @param spark The spark for which to retrieve the data.
         */
        void requestSparkData(Spark spark);

        /**
         * Called when the user requests to send a spark.
         *
         * @param sparkBody The body of the new spark.
         */
        void requestSendSpark(String sparkBody);

        /**
         * Called when the user requests to delete a spark.
         *
         * @param spark The spark to delete.
         */
        void requestDeleteSpark(Spark spark);

        /**
         * Called when the user requests to like or unlike a spark. It is up to the presenter to
         * decide whether the spark will be liked or unliked according to the spark's data.
         *
         * @param spark The spark to like or unlike.
         */
        void requestLikeUnlikeSpark(Spark spark);

        /**
         * Called when the current user requests to follow or unfollow another user. It is up to the
         * presenter to decide whether the user will be followed or unfollowed according to the user's data.
         *
         * @param user The user to follow or unfollow.
         */
        void requestFollowUnfollowUser(User user);

        /**
         * Called when the current user requests to search for another user by their first and last
         * name, or by their username.
         *
         * @param usernameOrName The username or first and last name of the user to search for.
         */
        void requestSearchUser(String usernameOrName);

        /**
         * Called when the current user requests to refresh a user profile.
         *
         * @param user The user whose profile to refresh.
         */
        void requestProfileDataRefresh(User user);

        /**
         * Called when the current user requests to refresh the home data.
         */
        void requestHomeDataRefresh();

        /**
         * Called when the current user requests to send a comment on a specific spark.
         *
         * @param spark        The spark on which to add the comment.
         * @param commentBody  The body of the comment.
         * @param replyComment The comment to which to reply, or null if the new comment is not a reply.
         */
        void requestSendComment(Spark spark, String commentBody, @Nullable Comment replyComment);

        /**
         * Called when the current user requests to delete a comment.
         *
         * @param comment The comment to be deleted.
         */
        void requestDeleteComment(Comment comment);

        /**
         * Called when the current user requests to refresh a spark's data (spark + comments).
         *
         * @param spark The spark whose data to refresh.
         */
        void requestSparkDataRefresh(Spark spark);

        /**
         * Called when the current user requests to like or unlike a comment. It is up to the
         * presenter to decide whether the comment will be liked or unliked according to the comment's data.
         *
         * @param comment The comment to like or unlike.
         */
        void requestLikeUnlikeComment(Comment comment);
    }

    /**
     * This interface defines the methods required to be implemented by any class which acts as a
     * PRESENTER for a MODEL in the MVP Architectural Pattern.
     * All methods starting with "response" are methods in response to client requests.
     */
    interface PresenterModel
    {
        /**
         * Called if the home data was acquired.
         *
         * @param sparks Sparks for the home feed.
         */
        void responseHomeDataSuccess(ArrayList<Spark> sparks);

        /**
         * Called if the home data could not be acquired.
         */
        void responseHomeDataFailure();

        /**
         * Called if the popular sparks were acquired.
         *
         * @param sparks Popular sparks.
         */
        void responsePopularDataSuccess(ArrayList<Spark> sparks);

        /**
         * Called if the popular sparks could not be acquired.
         */
        void responsePopularDataFailure();

        /**
         * Called if a spark was sent successfully to the network.
         *
         * @param spark Spark that was sent to the network.
         */
        void responseSendSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be sent to the network.
         */
        void responseSendSparkFailure();

        /**
         * Called if a spark was removed from the network.
         *
         * @param spark The spark which was deleted.
         */
        void responseDeleteSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be deleted from the network.
         */
        void responseDeleteSparkFailure();

        /**
         * Called if a spark is successfully liked.
         *
         * @param spark The spark which was liked.
         */
        void responseLikeSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be liked.
         */
        void responseLikeSparkFailure();

        /**
         * Called if a spark is successfully unliked.
         *
         * @param spark The spark which was unliked.
         */
        void responseUnlikeSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be liked.
         */
        void responseUnlikeSparkFailure();

        /**
         * Called if a user was followed.
         *
         * @param user The user that was followed.
         */
        void responseFollowUserSuccess(User user);

        /**
         * Called if a user was not followed.
         */
        void responseFollowUserFailure();

        /**
         * Called if a user was unfollowed.
         *
         * @param user The user that was unfollowed.
         */
        void responseUnfollowUserSuccess(User user);

        /**
         * Called if a user could not be unfollowed.
         */
        void responseUnfollowUserFailure();

        /**
         * Called if a user was found during a search.
         *
         * @param user   The user that was found in the search.
         * @param sparks The sparks belonging to the user found in the search.
         */
        void responseSearchUserSuccess(User user, ArrayList<Spark> sparks);

        /**
         * Called if a user could not be searched for.
         */
        void responseSearchUserFailure();

        /**
         * Called if a profile data was acquired successfully.
         *
         * @param user   The user whose profile was acquired.
         * @param sparks The sparks of the user whose profile was acquired.
         */
        void responseProfileDataSuccess(User user, ArrayList<Spark> sparks);

        /**
         * Called if a profile data could not be acquired.
         */
        void responseProfileDataFailure();

        /**
         * Called if a spark's data was successfully acquired.
         *
         * @param spark    The spark acquired.
         * @param comments The comments of the spark acquired.
         */
        void responseSparkDataSuccess(Spark spark, ArrayList<Comment> comments);

        /**
         * Called if a spark's data could not be acquired.
         */
        void responseSparkDataFailure();

        /**
         * Called if a comment was successfully sent into the network.
         *
         * @param comment The comment which was sent.
         */
        void responseSendCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be sent into the network.
         */
        void responseSendCommentFailure();

        /**
         * Called if the log out was successful.
         */
        void responseLogoutSuccess();

        /**
         * Called whenever a network error is detected.
         */
        void responseNetworkError();

        /**
         * Called when invalid user credentials are detected, and the user will be logged out.
         */
        void invalidUserCredentialsDetected();

        /**
         * Called if a comment was successfully deleted from the network.
         *
         * @param comment The comment which was deleted.
         */
        void responseDeleteCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be deleted from the network.
         */
        void responseDeleteCommentFailure();

        /**
         * Called if a comment was successfully liked.
         *
         * @param comment The comment that was liked.
         */
        void responseLikeCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be liked.
         */
        void responseLikeCommentFailure();

        /**
         * Called if a comment was successfully unliked.
         *
         * @param comment The comment which was unliked.
         */
        void responseUnlikeCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be unliked.
         */
        void responseUnlikeCommentFailure();
    }

    /**
     * This interface defines the methods required to be implemented by any class which acts as a VIEW
     * in the MVP Architectural Pattern.
     * All methods starting with "response" are methods in response to client requests.
     */
    interface View
    {
        /**
         * Called if the log out was successful.
         */
        void responseLogoutSuccess();

        /**
         * Called if the home data was acquired.
         *
         * @param sparks Sparks for the home feed.
         */
        void responseHomeDataSuccess(ArrayList<Spark> sparks);

        /**
         * Called if the home data could not be acquired.
         */
        void responseHomeDataFailure();

        /**
         * Called if a spark was sent successfully to the network.
         *
         * @param spark Spark that was sent to the network.
         */
        void responseSendSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be sent to the network.
         */
        void responseSendSparkFailure();

        /**
         * Called if a spark could not be sent to the network because it had an empty body.
         */
        void responseSendSparkEmpty();

        /**
         * Called to notify the view that a spark is currently being sent to the network.
         */
        void responseSendSparkInProgress();

        /**
         * Called if a spark could not be sent to the network because it had a body that was too long.
         */
        void responseSendSparkTooLong();

        /**
         * Called if a spark was removed from the network.
         *
         * @param spark The spark which was deleted.
         */
        void responseDeleteSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be deleted from the network.
         */
        void responseDeleteSparkFailure();

        /**
         * Called if a spark is successfully liked.
         *
         * @param spark The spark which was liked.
         */
        void responseLikeSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be liked.
         */
        void responseLikeSparkFailure();

        /**
         * Called if a spark is successfully unliked.
         *
         * @param spark The spark which was unliked.
         */
        void responseUnlikeSparkSuccess(Spark spark);

        /**
         * Called if a spark could not be liked.
         */
        void responseUnlikeSparkFailure();

        /**
         * Called if a user was followed.
         *
         * @param user The user that was followed.
         */
        void responseFollowUserSuccess(User user);

        /**
         * Called if a user was not followed.
         */
        void responseFollowUserFailure();

        /**
         * Called if a user was unfollowed.
         *
         * @param user The user that was unfollowed.
         */
        void responseUnfollowUserSuccess(User user);

        /**
         * Called if a user could not be unfollowed.
         */
        void responseUnfollowUserFailure();

        /**
         * Called if a user was found during a search.
         *
         * @param user   The user that was found in the search.
         * @param sparks The sparks belonging to the user found in the search.
         */
        void responseSearchUserSuccess(User user, ArrayList<Spark> sparks);

        /**
         * Called if a user could not be searched for.
         */
        void responseSearchUserFailure();

        /**
         * Called if a profile data was acquired successfully.
         *
         * @param user   The user whose profile was acquired.
         * @param sparks The sparks of the user whose profile was acquired.
         */
        void responseProfileDataSuccess(User user, ArrayList<Spark> sparks);

        /**
         * Called if a profile data could not be acquired.
         */
        void responseProfileDataFailure();

        /**
         * Called if a spark's data was successfully acquired.
         *
         * @param spark    The spark acquired.
         * @param comments The comments of the spark acquired.
         */
        void responseSparkDataSuccess(Spark spark, ArrayList<Comment> comments);

        /**
         * Called if a spark's data could not be acquired.
         */
        void responseSparkDataFailure();

        /**
         * Called if the popular sparks were acquired.
         *
         * @param sparks Popular sparks.
         */
        void responsePopularDataSuccess(ArrayList<Spark> sparks);

        /**
         * Called if the popular sparks could not be acquired.
         */
        void responsePopularDataFailure();

        /**
         * Called if a profile data was acquired successfully.
         *
         * @param user   The user whose profile was acquired.
         * @param sparks The sparks of the user whose profile was acquired.
         */
        void responseProfileDataRefreshSuccess(User user, ArrayList<Spark> sparks);

        /**
         * Called if a profile data could not be acquired.
         */
        void responseProfileDataRefreshFailure();

        /**
         * Called if the home data was acquired.
         *
         * @param sparks Sparks for the home feed.
         */
        void responseHomeDataRefreshSuccess(ArrayList<Spark> sparks);

        /**
         * Called if the home data could not be acquired.
         */
        void responseHomeDataRefreshFailure();

        /**
         * Called if a comment was successfully sent into the network.
         *
         * @param comment The comment which was sent.
         */
        void responseSendCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be sent into the network.
         */
        void responseSendCommentFailure();

        /**
         * Called if a comment could not be sent because it had an empty body.
         */
        void responseSendCommentEmptyBody();

        /**
         * Called if a comment could not be sent because it was too long.
         */
        void responseSendCommentTooLong();

        /**
         * Called if a comment was successfully deleted from the network.
         *
         * @param comment The comment which was deleted.
         */
        void responseDeleteCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be deleted from the network.
         */
        void responseDeleteCommentFailure();

        /**
         * Called whenever a network error occurs.
         */
        void responseNetworkError();

        /**
         * Called if a spark's data was successfully acquired.
         *
         * @param spark    The spark acquired.
         * @param comments The comments of the spark acquired.
         */
        void responseSparkDataRefreshSuccess(Spark spark, ArrayList<Comment> comments);

        /**
         * Called if a spark's data could not be acquired.
         */
        void responseSparkDataRefreshFailure();

        /**
         * Called if a comment was successfully liked.
         *
         * @param comment The comment that was liked.
         */
        void responseLikeCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be liked.
         */
        void responseLikeCommentFailure();

        /**
         * Called if a comment was successfully unliked.
         *
         * @param comment The comment which was unliked.
         */
        void responseUnlikeCommentSuccess(Comment comment);

        /**
         * Called if a comment could not be unliked.
         */
        void responseUnlikeCommentFailure();
    }

    /**
     * This interface defines the methods required to be implemented by any class which acts as a MODEL
     * in the MVP Architectural Pattern.
     * All methods starting with "request" are methods which can be initiated by the client user.
     */
    interface Model
    {
        /**
         * Sets the presenter for the model to interact with.
         *
         * @param presenter The presenter which the model will interact with.
         */
        void setPresenter(MainContract.PresenterModel presenter);

        /**
         * Called when the user requests to log out.
         */
        void requestLogout();

        /**
         * Called when the user requests a profile's data.
         *
         * @param userId The id of the user whose profile data to retrieve.
         */
        void requestProfileData(String userId);

        /**
         * Called when the user requests the home data.
         */
        void requestHomeData();

        /**
         * Called when the user requests the popular data.
         */
        void requestPopularData();

        /**
         * Called when the user requests to send a spark.
         *
         * @param sparkBody The body of the new spark.
         */
        void requestSendSpark(String sparkBody);

        /**
         * Called when the user requests to delete a spark.
         *
         * @param spark The spark to delete.
         */
        void requestDeleteSpark(Spark spark);

        /**
         * Called when the user requests to follow a user.
         *
         * @param user The user to follow.
         */
        void requestFollowUser(User user);

        /**
         * Called when the user requests to unfollow a user.
         *
         * @param user The user to unfollow.
         */
        void requestUnfollowUser(User user);

        /**
         * Called when the current user requests to search for another user by their first and last
         * name, or by their username.
         *
         * @param usernameOrName The username or first and last name of the user to search for.
         */
        void requestSearchUser(String usernameOrName);

        /**
         * Called when the user requests a spark's data (spark + its comments).
         *
         * @param spark The spark for which to retrieve the data.
         */
        void requestSparkData(Spark spark);

        /**
         * Called when the current user requests to send a comment on a specific spark.
         *
         * @param spark        The spark on which to add the comment.
         * @param commentBody  The body of the comment.
         * @param replyComment The comment to which to reply, or null if the new comment is not a reply.
         */
        void requestSendComment(Spark spark, String commentBody, Comment replyComment);

        /**
         * Called when the current user requests to delete a comment.
         *
         * @param comment The comment to be deleted.
         */
        void requestDeleteComment(Comment comment);

        /**
         * Called when the current user requests to like a spark.
         *
         * @param spark The spark to like.
         */
        void requestLikeSpark(Spark spark);

        /**
         * Called when the current user requests to unlike a spark.
         *
         * @param spark The spark to unlike.
         */
        void requestUnlikeSpark(Spark spark);

        /**
         * Called when the current user requests to like a comment.
         *
         * @param comment The comment to like.
         */
        void requestLikeComment(Comment comment);

        /**
         * Called when the current user requests to unlike a comment.
         *
         * @param comment The comment to unlike.
         */
        void requestUnlikeComment(Comment comment);
    }
}