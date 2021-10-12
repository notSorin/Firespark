package com.lesorin.firespark.presenter;

import java.util.ArrayList;

public interface MainActivityContract
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
        void sparkClicked(Spark spark);
        void sendSparkRequested(String sparkBody);
        void sparkDeleteClicked(Spark spark);
        void sparkLikeClicked(Spark spark);
        void userFollowClicked(User user);
        void requestSearchUserByUsername(String userName);
    }

    interface PresenterModel
    {
        void profileDataAcquired(User user);
        void homeDataAcquired(ArrayList<Spark> sparks);
        void popularDataAcquired(ArrayList<Spark> sparks);
        void sendSparkResult(Spark spark);
        void deleteSparkFailure();
        void deleteSparkSuccess(Spark spark);
        void addSparkLikeSuccess(Spark spark);
        void addSparkLikeFailure(Spark spark);
        void removeSparkLikeSuccess(Spark spark);
        void removeSparkLikeFailure(Spark spark);
        void requestProfileUserResult(User user);
        void requestProfileSparksResult(ArrayList<Spark> sparks);
        void followUserSuccess(User user);
        void followUserFailure();
        void unfollowUserSuccess(User user);
        void unfollowUserFailure();
        void searchUserFailure();
        void searchUserSuccess(User user, ArrayList<Spark> sparks);
    }

    interface View
    {
        void openStartActivity();
        void displayProfileData(User user);
        void displayHomeData(ArrayList<Spark> sparks);
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
        void requestProfileUserSuccess(User user);
        void requestProfileUserFailure();
        void requestProfileSparksSuccess(ArrayList<Spark> sparks);
        void requestProfileSparksFailure();
        void followUserSuccess(User user);
        void followUserFailure();
        void unfollowUserSuccess(User user);
        void unfollowUserFailure();
        void searchUserByUsernameFailure();
        void searchUserByUsernameSuccess(User user, ArrayList<Spark> sparks);
    }

    interface Model
    {
        void setPresenter(MainActivityContract.PresenterModel presenter);
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
    }
}