package com.lesorin.firespark.view.fragments;

import androidx.fragment.app.Fragment;
import com.lesorin.firespark.presenter.Comment;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

/**
 * An abstract @{@link Fragment} to be extended by all fragments used in the app.
 */
public abstract class FiresparkFragment extends Fragment
{
    /**
     * Activity to be accessible from all the fragments of the app.
     */
    protected MainActivity _activity;

    /**
     *
     * @return The user related to the fragment, or null if no user is related to the fragment.
     */
    public abstract User getUser();

    /**
     *
     * @return True if the fragment is a @{@link ProfileFragment}, false otherwise.
     */
    public abstract boolean isProfileFragment();

    /**
     *
     * @return True if the fragment is a @{@link HomeFragment} fragment, false otherwise.
     */
    public abstract boolean isHomeFragment();

    /**
     *
     * @return True if the fragment is a @{@link PopularFragment} fragment, false otherwise.
     */
    public abstract boolean isPopularFragment();

    /**
     * Adds a spark to the current fragment.
     *
     * @param spark The spark to be added to the fragment.
     */
    public abstract void addSpark(Spark spark);

    /**
     * Deletes a spark from the current fragment.
     *
     * @param spark The spark to be deleted.
     */
    public abstract void deleteSpark(Spark spark);

    /**
     *
     * @return True if the fragment is considered to be one of the main fragments of the app, false otherwise.
     * Main fragments are @{@link ProfileFragment} @{@link HomeFragment} and @{@link PopularFragment},
     * or any other fragment that is part of the navigation view on the @{@link MainActivity}
     */
    public abstract boolean isMainFragment();

    /**
     * Notifies the fragment that a spark has been liked.
     *
     * @param spark The spark which was liked.
     */
    public abstract void sparkLiked(Spark spark);

    /**
     * Notifies the fragment that a spark has been unliked.
     *
     * @param spark The spark which was unliked.
     */
    public abstract void sparkUnliked(Spark spark);

    /**
     * Sets a user on the fragment.
     *
     * @param user The user to set on the fragment.
     */
    public abstract void setUser(User user);

    /**
     * Notifies the fragment that its user has been followed.
     */
    public abstract void userFollowed();

    /**
     * Notifies the fragment that its user has been unfollowed.
     */
    public abstract void userUnfollowed();

    /**
     * Force the elements on the fragment to be displayed.
     */
    public abstract void displayElements();

    /**
     * Refreshes the profile on the fragment.
     *
     * @param user   The refreshed user.
     * @param sparks The refreshed sparks.
     */
    public abstract void refreshProfile(User user, ArrayList<Spark> sparks);

    /**
     * Refreshes the sparks on the fragment.
     *
     * @param sparks The refreshed sparks.
     */
    public abstract void refreshSparks(ArrayList<Spark> sparks);

    /**
     * Adds a comment on the fragment.
     *
     * @param comment The comment to add.
     */
    public abstract void addComment(Comment comment);

    /**
     *
     * @return True if the fragment is a @{@link SparkFragment}, false otherwise.
     */
    public abstract boolean isSparkFragment();

    /**
     *
     * @return The spark on the fragment, or null if there is no spark on it.
     */
    public abstract Spark getSpark();

    /**
     * Refreshes the spark's data on the fragment.
     *
     * @param spark    The refreshed spark.
     * @param comments The refreshed comments.
     */
    public abstract void refreshSparkData(Spark spark, ArrayList<Comment> comments);

    /**
     * Deletes a comment from the fragment.
     *
     * @param comment The comment to be deleted.
     */
    public abstract void deleteComment(Comment comment);

    /**
     * Notifies the fragment that a comment has been liked.
     *
     * @param comment The comment which was liked.
     */
    public abstract void commentLiked(Comment comment);

    /**
     * Notifies the fragment that a comment has been unliked.
     *
     * @param comment The comment which was unliked.
     */
    public abstract void commentUnliked(Comment comment);

    /**
     * Sets the state of elements related to sending new comments.
     *
     * @param enabled True to enable the elements, false otherwise.
     */
    public abstract void setCommentRelatedElementsState(boolean enabled);
}