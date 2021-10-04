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
    }

    interface View
    {
        void openStartActivity();
        void displayProfileData(User user);
        void displayHomeData(ArrayList<Spark> sparks);
        void displayPopularData(ArrayList<Spark> sparks);
        void errorSendSparkEmpty();
    }

    interface Model
    {
        void setPresenter(MainActivityContract.PresenterModel presenter);
        void logUserOut();
        void requestProfileData();
        void requestHomeData();
        void requestPopularData();
        String getUserName();
        void sendSpark(Spark spark);
    }

    class User
    {
        public String _name;
        public ArrayList<String> _followers;
        public ArrayList<String> _following;
        public ArrayList<Spark> _sparks;

        public User()
        {
            _followers = new ArrayList<>();
            _following = new ArrayList<>();
            _sparks = new ArrayList<>();
        }
    }

    class Spark
    {
        public String _id, _text, _ownerId, _ownerName;
        public boolean _isDeleted, _ownedByCurrentUser;
        public ArrayList<String> _likes;
        public ArrayList<String> _subscribers;
        public ArrayList<Comment> _comments;

        public Spark()
        {
            _likes = new ArrayList<>();
            _subscribers = new ArrayList<>();
            _comments = new ArrayList<>();
        }
    }

    class Comment
    {
        public String _text, _ownerId, _ownerName;
    }
}