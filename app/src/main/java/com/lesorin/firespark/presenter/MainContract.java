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
        void logOutButtonPressed();
        void requestProfileData(String userId);
        void requestHomeData();
        void requestPopularData();
        void requestSparkData(Spark spark);
        void sendSparkRequested(String sparkBody);
        void sparkDeleteClicked(Spark spark);
        void sparkLikeClicked(Spark spark);
        void userFollowClicked(User user);
        void requestSearchUserByUsername(String userName);
        void requestProfileDataRefresh(User user);
        void requestHomeDataRefresh();
    }

    interface PresenterModel
    {
        void requestHomeDataSuccess(ArrayList<Spark> sparks);
        void requestHomeDataFailure();
        void popularDataAcquired(ArrayList<Spark> sparks);
        void sendSparkResult(Spark spark);
        void deleteSparkFailure();
        void deleteSparkSuccess(Spark spark);
        void addSparkLikeSuccess(Spark spark);
        void addSparkLikeFailure(Spark spark);
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
        void addSparkLikeFailure(Spark spark);
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
    }

    interface Model
    {
        void setPresenter(MainContract.PresenterModel presenter);
        void logUserOut();
        void requestProfileData(String userId);
        void requestHomeData();
        void requestPopularData();
        String getUserName();
        void sendSpark(String sparkBody);
        void deleteSpark(Spark spark);
        void likeDislikeSpark(Spark spark);
        void followUnfollowUser(User user);
        void searchUserByUsername(String userName);
        void requestSparkData(Spark spark);
    }
}