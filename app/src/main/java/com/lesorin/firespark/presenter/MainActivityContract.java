package com.lesorin.firespark.presenter;

import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;
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
        void sendSparkFailure();
    }

    interface View
    {
        void openStartActivity();
        void displayProfileData(User user);
        void displayHomeData(ArrayList<Spark> sparks);
        void displayPopularData(ArrayList<Spark> sparks);
        void errorSendSparkEmpty();
        void informSendingSpark();
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

        @PropertyName("followers")
        public ArrayList<String> _followers;

        @PropertyName("following")
        public ArrayList<String> _following;

        public User()
        {
            _followers = new ArrayList<>();
            _following = new ArrayList<>();
        }
    }

    class Spark
    {
        public String _id, _created;
        public boolean _ownedByCurrentUser;

        @PropertyName("body")
        public String _text;

        @PropertyName("ownerid")
        public String _ownerId;

        @PropertyName("ownername")
        public String _ownerName;

        @PropertyName("likes")
        public List<String> _likes;

        @PropertyName("subscribers")
        public List<String> _subscribers;

        public Spark()
        {
            _likes = new ArrayList<>();
            _subscribers = new ArrayList<>();
        }
    }

    class Comment
    {
        public String _text, _ownerId, _ownerName;
    }
}