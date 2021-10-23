package com.lesorin.firespark.view.fragments;

import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

public class FiresparkFragmentAdapter extends FiresparkFragment
{
    public FiresparkFragmentAdapter(MainActivity activity)
    {
        _activity = activity;
    }

    @Override
    public User getUser()
    {
        return null;
    }

    @Override
    public boolean isProfileFragment()
    {
        return false;
    }

    @Override
    public boolean isHomeFragment()
    {
        return false;
    }

    @Override
    public boolean isPopularFragment()
    {
        return false;
    }

    @Override
    public void addSpark(Spark spark)
    {
    }

    @Override
    public void deleteSpark(Spark spark)
    {
    }

    @Override
    public boolean isMainFragment()
    {
        return false;
    }

    @Override
    public void sparkLiked(Spark spark)
    {
    }

    @Override
    public void sparkLikeRemoved(Spark spark)
    {
    }

    @Override
    public void setUser(User user)
    {
    }

    @Override
    public void userFollowed()
    {
    }

    @Override
    public void userUnfollowed()
    {
    }

    @Override
    public void displayElements()
    {
    }

    @Override
    public void refreshProfile(User user, ArrayList<Spark> sparks)
    {
    }

    @Override
    public void refreshSparks(ArrayList<Spark> sparks)
    {
    }

    @Override
    public void sendCommentSuccess(Comment comment)
    {
    }
}
