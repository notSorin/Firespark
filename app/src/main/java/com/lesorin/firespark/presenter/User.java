package com.lesorin.firespark.presenter;

import java.util.ArrayList;
import java.util.Date;

public abstract class User
{
    public abstract void setId(String id);
    public abstract String getId();

    public abstract String getFirstLastName();
    public abstract void setFirstLastName(String name);

    public abstract String getUsername();
    public abstract String getUsernameInsensitive();
    public abstract void setUsername(String username);

    public abstract ArrayList<String> getFollowers();
    public abstract void setFollowers(ArrayList<String> followers);

    public abstract ArrayList<String> getFollowing();
    public abstract void setFollowing(ArrayList<String> following);

    public abstract Date getJoined();
    public abstract void setJoined(Date joined);

    public abstract boolean isCurrentUser();
    public abstract void setCurrentUser(boolean isCurrentUser);

    public abstract boolean isFollowedByCurrentUser();
    public abstract void setFollowedByCurrentUser(boolean followedByCurrentUser);

    public abstract boolean isVerified();
    public abstract void setVerified(boolean verified);

    public abstract boolean isOriginal();
    public abstract void setOriginal(boolean original);

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