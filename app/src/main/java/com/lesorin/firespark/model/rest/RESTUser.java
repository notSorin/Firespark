package com.lesorin.firespark.model.rest;

import com.lesorin.firespark.presenter.User;
import java.util.Date;
import java.util.Set;

/**
 * REST User.
 */
public class RESTUser extends User
{
    private boolean _isCurrentUser, _isFollowedByCurrentUser;

    private String userid, username, firstlastname;
    private Date joined;
    private int verified, original;
    private Set<String> followers, following;

    @Override
    public void setId(String id)
    {
        userid = id;
    }

    @Override
    public String getId()
    {
        return userid;
    }

    @Override
    public String getFirstLastName()
    {
        return firstlastname;
    }

    @Override
    public void setFirstLastName(String name)
    {
        firstlastname = name;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public String getUsernameInsensitive()
    {
        return username.toLowerCase();
    }

    @Override
    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public Set<String> getFollowers()
    {
        return followers;
    }

    @Override
    public void setFollowers(Set<String> followers)
    {
        this.followers = followers;
    }

    @Override
    public Set<String> getFollowing()
    {
        return following;
    }

    @Override
    public void setFollowing(Set<String> following)
    {
        this.following = following;
    }

    @Override
    public Date getJoined()
    {
        return joined;
    }

    @Override
    public void setJoined(Date joined)
    {
        this.joined = joined;
    }

    @Override
    public boolean isCurrentUser()
    {
        return _isCurrentUser;
    }

    @Override
    public void setCurrentUser(boolean isCurrentUser)
    {
        _isCurrentUser = isCurrentUser;
    }

    @Override
    public boolean isFollowedByCurrentUser()
    {
        return _isFollowedByCurrentUser;
    }

    @Override
    public void setFollowedByCurrentUser(boolean followedByCurrentUser)
    {
        _isFollowedByCurrentUser = followedByCurrentUser;
    }

    @Override
    public boolean isVerified()
    {
        return verified == 1;
    }

    @Override
    public void setVerified(boolean verified)
    {
        this.verified = verified ? 1 : 0;
    }

    @Override
    public boolean isOriginal()
    {
        return original == 1;
    }

    @Override
    public void setOriginal(boolean original)
    {
        this.original = original ? 1 : 0;
    }
}