package com.lesorin.firespark.view.fragments;

import androidx.fragment.app.Fragment;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

public abstract class FiresparkFragment extends Fragment
{
    protected MainActivity _activity;

    public abstract User getUser();
    public abstract boolean isProfileFragment();
    public abstract boolean isHomeFragment();
    public abstract boolean isPopularFragment();
    public abstract void addSpark(Spark spark);
    public abstract void deleteSpark(Spark spark);
    public abstract boolean isMainFragment();
    public abstract void sparkLiked(Spark spark);
    public abstract void sparkLikeRemoved(Spark spark);
    public abstract void setUser(User user);
    public abstract void userFollowed();
    public abstract void userUnfollowed();
    public abstract void displayElements();
    public abstract void refreshProfile(User user, ArrayList<Spark> sparks);
    public abstract void refreshSparks(ArrayList<Spark> sparks);
    public abstract void sendCommentSuccess(Comment comment);
}