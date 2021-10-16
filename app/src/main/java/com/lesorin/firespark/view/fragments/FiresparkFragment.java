package com.lesorin.firespark.view.fragments;

import androidx.fragment.app.Fragment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;

public abstract class FiresparkFragment extends Fragment
{
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
    public abstract void userFollowed(User user);
    public abstract void userUnfollowed(User user);
    public abstract void displayElements();
}