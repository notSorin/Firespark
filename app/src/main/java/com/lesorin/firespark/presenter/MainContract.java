package com.lesorin.firespark.presenter;

import java.util.ArrayList;

public interface MainContract
{
    //All methods starting with "request" are methods which can be initiated by the client user.
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void appStarted();
        void requestLogout();
        void requestProfileData(String userId);
        void requestHomeData();
        void requestPopularData();
        void requestSparkData(Spark spark);
        void requestSendSpark(String sparkBody);
        void requestDeleteSpark(Spark spark);
        void requestLikeDislikeSpark(Spark spark);
        void requestFollowUnfollowUser(User user);
        void requestSearchUserByUsername(String userName);
        void requestProfileDataRefresh(User user);
        void requestHomeDataRefresh();
        void requestSendComment(Spark spark, String commentBody, Comment replyComment);
        void requestDeleteComment(Comment comment);
    }

    //All methods starting with "response" are methods in response to client requests.
    interface PresenterModel
    {
        void responseHomeDataSuccess(ArrayList<Spark> sparks);
        void responseHomeDataFailure();
        void responsePopularDataSuccess(ArrayList<Spark> sparks);
        void responsePopularDataFailure();
        void responseSendSparkSuccess(Spark spark);
        void responseSendSparkFailure();
        void responseDeleteSparkSuccess(Spark spark);
        void responseDeleteSparkFailure();
        void responseLikeSparkSuccess(Spark spark);
        void responseLikeSparkFailure();
        void responseUnlikeSparkSuccess(Spark spark);
        void responseUnlikeSparkFailure();
        void responseFollowUserSuccess(User user);
        void responseFollowUserFailure();
        void responseUnfollowUserSuccess(User user);
        void responseUnfollowUserFailure();
        void responseSearchUserByUsernameSuccess(User user, ArrayList<Spark> sparks);
        void responseSearchUserByUsernameFailure();
        void responseProfileDataSuccess(User user, ArrayList<Spark> sparks);
        void responseProfileDataFailure();
        void responseSparkDataSuccess(Spark spark, ArrayList<Comment> comments);
        void responseSparkDataFailure();
        void responseSendCommentSuccess(Comment comment);
        void responseSendCommentFailure();
        void responseLogoutSuccess();
        void responseNetworkError();
        void invalidUserCredentialsDetected();
    }

    //All methods starting with "response" are methods in response to client requests.
    interface View
    {
        void responseLogoutSuccess();
        void responseHomeDataSuccess(ArrayList<Spark> sparks);
        void responseHomeDataFailure();
        void responseSendSparkSuccess(Spark spark);
        void responseSendSparkFailure();
        void responseSendSparkEmpty();
        void responseSendSparkInProgress();
        void responseSendSparkTooLong();
        void responseDeleteSparkSuccess(Spark spark);
        void responseDeleteSparkFailure();
        void responseLikeSparkSuccess(Spark spark);
        void responseLikeSparkFailure();
        void responseUnlikeSparkSuccess(Spark spark);
        void responseUnlikeSparkFailure();
        void responseFollowUserSuccess(User user);
        void responseFollowUserFailure();
        void responseUnfollowUserSuccess(User user);
        void responseUnfollowUserFailure();
        void responseSearchUserByUsernameSuccess(User user, ArrayList<Spark> sparks);
        void responseSearchUserByUsernameFailure();
        void responseProfileDataSuccess(User user, ArrayList<Spark> sparks);
        void responseProfileDataFailure();
        void responseSparkDataSuccess(Spark spark, ArrayList<Comment> comments);
        void responseSparkDataFailure();
        void responsePopularDataSuccess(ArrayList<Spark> sparks);
        void responsePopularDataFailure();
        void responseProfileDataRefreshSuccess(User user, ArrayList<Spark> sparks);
        void responseProfileDataRefreshFailure();
        void responseHomeDataRefreshSuccess(ArrayList<Spark> sparks);
        void responseHomeDataRefreshFailure();
        void responseSendCommentSuccess(Comment comment);
        void responseSendCommentFailure();
        void responseSendCommentEmptyBody();
        void responseDeleteCommentFailure();
        void responseNetworkError();
    }

    //All methods starting with "request" are methods which can be initiated by the client user.
    interface Model
    {
        void setPresenter(MainContract.PresenterModel presenter);
        void requestLogout();
        void requestProfileData(String userId);
        void requestHomeData();
        void requestPopularData();
        void requestSendSpark(String sparkBody);
        void requestDeleteSpark(Spark spark);
        void requestLikeDislikeSpark(Spark spark);
        void requestFollowUnfollowUser(User user);
        void requestSearchUserByUsername(String userName);
        void requestSparkData(Spark spark);
        void requestSendComment(Spark spark, String commentBody, Comment replyComment);
        void requestDeleteComment(Comment comment);
    }
}