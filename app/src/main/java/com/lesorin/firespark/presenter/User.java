package com.lesorin.firespark.presenter;

import java.util.Date;
import java.util.Set;

/**
 * Abstract model class for a User in the network.
 */
public abstract class User
{
    /**
     * Sets the user's id.
     *
     * @param id The user's id.
     */
    public abstract void setId(String id);

    /**
     *
     * @return The user's id.
     */
    public abstract String getId();

    /**
     *
     * @return The user's first and last name.
     */
    public abstract String getFirstLastName();

    /**
     * Sets the user's first and last name.
     *
     * @param name The user's first and last name.
     */
    public abstract void setFirstLastName(String name);

    /**
     *
     * @return The user's username.
     */
    public abstract String getUsername();

    /**
     *
     * @return The user's username in lowercase.
     */
    public abstract String getUsernameInsensitive();

    /**
     * Sets the user's username.
     *
     * @param username The user's username.
     */
    public abstract void setUsername(String username);

    /**
     *
     * @return A set with all the ids of the users who follow this user.
     */
    public abstract Set<String> getFollowers();

    /**
     * Sets the user's followers.
     *
     * @param followers A set with all the ids of the users who follow this user.
     */
    public abstract void setFollowers(Set<String> followers);

    /**
     *
     * @return A set with all the ids of the users who this user follows.
     */
    public abstract Set<String> getFollowing();

    /**
     * Sets the user's following.
     *
     * @param following A set with all the ids of the users who this user follows.
     */
    public abstract void setFollowing(Set<String> following);

    /**
     *
     * @return The date when this user joined the network.
     */
    public abstract Date getJoined();

    /**
     * Sets the date when this user joined the network.
     *
     * @param joined The date when this user joined the network.
     */
    public abstract void setJoined(Date joined);

    /**
     *
     * @return True if this user is the current user of the app, false otherwise.
     */
    public abstract boolean isCurrentUser();

    /**
     * Sets whether this user is the current user of the app.
     *
     * @param isCurrentUser True if this user is the current user of the app, false otherwise.
     */
    public abstract void setCurrentUser(boolean isCurrentUser);

    /**
     *
     * @return True if this user is followed by the current user of the app, false otherwise.
     */
    public abstract boolean isFollowedByCurrentUser();

    /**
     * Sets whether this user is followed by the current user of the app, false otherwise.
     *
     * @param followedByCurrentUser True if this user is followed by the current user of the app, false otherwise.
     */
    public abstract void setFollowedByCurrentUser(boolean followedByCurrentUser);

    /**
     *
     * @return True if this is a verified user, false otherwise.
     */
    public abstract boolean isVerified();

    /**
     * Sets whether this user this is verified or not.
     *
     * @param verified True if this is a verified user, false otherwise.
     */
    public abstract void setVerified(boolean verified);

    /**
     *
     * @return True if this is an original account user, false otherwise.
     */
    public abstract boolean isOriginal();

    /**
     * Sets whether this is account is original or not.
     *
     * @param original True if this is an original account user, false otherwise.
     */
    public abstract void setOriginal(boolean original);

    /**
     * Updates this user with the data of another user.
     *
     * @param user Another user to update this user with.
     */
    public void update(User user)
    {
        setId(user.getId());
        setCurrentUser(user.isCurrentUser());
        setFollowedByCurrentUser(user.isFollowedByCurrentUser());
        setFirstLastName(user.getFirstLastName());
        setUsername(user.getUsername());
        setJoined(user.getJoined());
        setFollowers(user.getFollowers());
        setFollowing(user.getFollowing());
        setVerified(user.isVerified());
        setOriginal(user.isOriginal());
    }
}