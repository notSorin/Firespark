package com.lesorin.firespark.view.fragments;

import com.lesorin.firespark.presenter.Comment;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

/**
 * An adapter of @{@link FiresparkFragment} with default implementations.
 */
public class FiresparkFragmentAdapter extends FiresparkFragment
{
    /**
     * Instantiates a new Firespark fragment adapter.
     *
     * @param activity Activity to be accessible from the fragment.
     */
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
    public void sparkUnliked(Spark spark)
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
    public void addComment(Comment comment)
    {
    }

    @Override
    public boolean isSparkFragment()
    {
        return false;
    }

    @Override
    public Spark getSpark()
    {
        return null;
    }

    @Override
    public void refreshSparkData(Spark spark, ArrayList<Comment> comments)
    {
    }

    @Override
    public void deleteComment(Comment comment)
    {
    }

    @Override
    public void commentLiked(Comment comment)
    {
    }

    @Override
    public void commentUnliked(Comment comment)
    {
    }

    @Override
    public void setCommentRelatedElementsState(boolean enabled)
    {
    }

    @Override
    public boolean isUsersFragment()
    {
        return false;
    }
}