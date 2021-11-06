package com.lesorin.firespark.model.rest;

import com.lesorin.firespark.presenter.Comment;
import java.util.Date;
import java.util.Set;

/**
 * REST Comment.
 */
public class RESTComment extends Comment
{
    private boolean _ownedByCurrentUser, _likedByCurrentUser;

    private String commentid, sparkid, userid, body, replytoid, username, firstlastname,
            replytousername, replytofirstlastname;
    private Date created;
    private int deleted;
    private Set<String> likes;

    @Override
    public String getId()
    {
        return commentid;
    }

    @Override
    public void setId(String id)
    {
        commentid = id;
    }

    @Override
    public boolean isOwnedByCurrentUser()
    {
        return _ownedByCurrentUser;
    }

    @Override
    public void setOwnedByCurrentUser(boolean ownedByCurrentUser)
    {
        _ownedByCurrentUser = ownedByCurrentUser;
    }

    @Override
    public String getSparkId()
    {
        return sparkid;
    }

    @Override
    public void setSparkId(String sparkId)
    {
        this.sparkid = sparkId;
    }

    @Override
    public String getUserId()
    {
        return userid;
    }

    @Override
    public void setUserId(String id)
    {
        userid = id;
    }

    @Override
    public String getUserFirstLastName()
    {
        return firstlastname;
    }

    @Override
    public void setUserFirstLastName(String name)
    {
        firstlastname = name;
    }

    @Override
    public String getUserUsername()
    {
        return username;
    }

    @Override
    public void setUserUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String getBody()
    {
        return body;
    }

    @Override
    public void setBody(String body)
    {
        this.body = body;
    }

    @Override
    public Date getCreated()
    {
        return created;
    }

    @Override
    public void setCreated(Date created)
    {
        this.created = created;
    }

    @Override
    public boolean isDeleted()
    {
        return deleted != 0;
    }

    @Override
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted ? 1 : 0;
    }

    @Override
    public Set<String> getLikes()
    {
        return likes;
    }

    @Override
    public void setLikes(Set<String> likes)
    {
        this.likes = likes;
    }

    @Override
    public String getReplyToFirstLastName()
    {
        return replytofirstlastname;
    }

    @Override
    public void setReplyToFirstLastName(String firstLastName)
    {
        replytofirstlastname = firstLastName;
    }

    @Override
    public String getReplyToId()
    {
        return replytoid;
    }

    @Override
    public void setReplyToId(String id)
    {
        replytoid = id;
    }

    @Override
    public String getReplyToUsername()
    {
        return replytousername;
    }

    @Override
    public void setReplyToUsername(String username)
    {
        replytousername = username;
    }

    @Override
    public boolean isLikedByCurrentUser()
    {
        return _likedByCurrentUser;
    }

    @Override
    public void setLikedByCurrentUser(boolean liked)
    {
        _likedByCurrentUser = liked;
    }
}