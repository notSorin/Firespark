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
        void requestProfileData();
        void requestHomeData();
        void requestPopularData();
        void sparkClicked(Spark spark);
        void sendSparkRequested(String sparkBody);
    }

    interface PresenterModel
    {
        void profileDataAcquired(User user);
        void homeDataAcquired(ArrayList<Spark> sparks);
        void popularDataAcquired(ArrayList<Spark> sparks);
        void sendSparkResult(Spark spark);
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
    }

    interface Model
    {
        void setPresenter(MainActivityContract.PresenterModel presenter);
        void logUserOut();
        void requestProfileData();
        void requestHomeData();
        void requestPopularData();
        String getUserName();
        void sendSpark(String sparkBody);
    }
}