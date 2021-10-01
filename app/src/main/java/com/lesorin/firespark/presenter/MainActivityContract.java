package com.lesorin.firespark.presenter;

public interface MainActivityContract
{
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void appStarted();
        void logOutButtonPressed();
    }

    interface PresenterModel
    {

    }

    interface View
    {
        void openStartActivity();
    }

    interface Model
    {
        void setPresenter(MainActivityContract.PresenterModel presenter);
        void logUserOut();
    }
}