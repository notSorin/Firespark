package com.lesorin.firespark.presenter;

import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import java.util.ArrayList;

//todo change methods names so all requests start with "request".
public interface MainContract
{
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void appStarted();

        //All methods starting with "request" are methods which can be initiated by the client user.
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

    interface PresenterModel
    {
        //All methods starting with "response" are methods in response to client requests.
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
        void removeSparkLikeSuccess(Spark spark);
        void removeSparkLikeFailure(Spark spark);
        void followUserSuccess(User user);
        void followUserFailure();
        void unfollowUserSuccess(User user);
        void unfollowUserFailure();
        void searchUserFailure();
        void searchUserSuccess(User user, ArrayList<Spark> sparks);
        void requestProfileDataSuccess(User user, ArrayList<Spark> sparks);
        void requestProfileDataFailure();
        void requestSparkDataSuccess(Spark spark, ArrayList<Comment> comments);
        void requestSparkDataFailure();
        void requestPopularDataFailure();
        void requestPopularDataSuccess(ArrayList<Spark> sparks);
        void requestSendCommentFailure();
        void requestSendCommentSuccess(Comment comment);
    }

    interface View
    {
        void userLoggedOutSuccessfully();
        void requestHomeDataSuccess(ArrayList<Spark> sparks);
        void displayPopularData(ArrayList<Spark> sparks);
        void errorSendSparkEmpty();
        void informSendingSpark();
        void errorSendSparkTooLong();
        void errorSendSparkUnknown();
        void sparkSentSuccessfully(Spark spark);
        void deleteSparkError();
        void deleteSparkSuccess(Spark spark);
        void addSparkLikeSuccess(Spark spark);
        void addSparkLikeFailure();
        void removeSparkLikeSuccess(Spark spark);
        void removeSparkLikeFailure(Spark spark);
        void followUserSuccess(User user);
        void followUserFailure();
        void unfollowUserSuccess(User user);
        void unfollowUserFailure();
        void searchUserByUsernameFailure();
        void searchUserByUsernameSuccess(User user, ArrayList<Spark> sparks);
        void requestProfileDataSuccess(User user, ArrayList<Spark> sparks);
        void requestProfileDataFailure();
        void requestSparkDataSuccess(Spark spark, ArrayList<Comment> comments);
        void requestSparkDataFailure();
        void requestPopularDataFailure();
        void requestPopularDataSuccess(ArrayList<Spark> sparks);
        void requestProfileDataRefreshSuccess(User user, ArrayList<Spark> sparks);
        void requestHomeDataRefreshSuccess(ArrayList<Spark> sparks);
        void requestHomeDataRefreshFailure();
        void requestHomeDataFailure();
        void requestProfileDataRefreshFailure();
        void requestSendCommentFailure();
        void requestSendCommentFailureEmptyBody();
        void requestSendCommentSuccess(Comment comment);
    }

    interface Model
    {
        void setPresenter(MainContract.PresenterModel presenter);

        //All methods starting with "request" are methods which can be initiated by the client user.
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
    }
}