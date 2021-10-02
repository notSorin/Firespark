package com.lesorin.firespark.presenter;

import java.util.List;

public interface MainActivityContract
{
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void appStarted();
        void logOutButtonPressed();
        void requestProfileData();
    }

    interface PresenterModel
    {
        void profileDataAcquired(User user);
    }

    interface View
    {
        void openStartActivity();
        void displayProfileData(User user);
    }

    interface Model
    {
        void setPresenter(MainActivityContract.PresenterModel presenter);
        void logUserOut();
        void requestProfileData();
    }

    class User
    {
        public String _name;
        public List<String> _followers;
        public List<String> _following;
    }

    class Spark
    {
        public String _id, _text, _ownerId, _ownerName;
        public boolean _isDeleted;
        public List<String> _likes;
        public List<String> _subscribers;
        public List<Comment> _comments;
    }

    class Comment
    {
        public String _text, _ownerId, _ownerName;
    }
}