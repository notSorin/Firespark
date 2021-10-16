package com.lesorin.firespark.view.fragments;

import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;

public class FiresparkFragmentAdapter extends FiresparkFragment
{
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
    public void userFollowed(User user)
    {
    }

    @Override
    public void userUnfollowed(User user)
    {
    }

    @Override
    public void displayElements()
    {
    }
}
