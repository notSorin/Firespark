package com.lesorin.firespark.model.rest;

import com.lesorin.firespark.presenter.Spark;
import java.util.ArrayList;
import java.util.Date;

public class RESTSpark extends Spark
{
    private boolean _ownedByCurrentUser, _likedByCurrentUser, _containsCommentFromCurrentUser;

    private String sparkid, userid, body, username, firstlastname;
    private Date created;
    private int deleted;
    private ArrayList<String> likes, comments;

    @Override
    public String getId()
    {
        return sparkid;
    }

    @Override
    public void setId(String id)
    {
        sparkid = id;
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
    public ArrayList<String> getSubscribers()
    {
        return null; //Not used by the REST model.
    }

    @Override
    public void addSubscriber(String subscriberId)
    {
        //Not used by the REST model.
    }

    @Override
    public ArrayList<String> getLikes()
    {
        return likes;
    }

    @Override
    public void setLikes(ArrayList<String> likes)
    {
        this.likes = likes;
    }

    @Override
    public int getLikesAmount()
    {
        return this.likes.size();
    }

    @Override
    public String getUserId()
    {
        return userid;
    }

    @Override
    public void setUserId(String userid)
    {
        this.userid = userid;
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
    public boolean isLikedByCurrentUser()
    {
        return _likedByCurrentUser;
    }

    @Override
    public void setLikedByCurrentUser(boolean likedByCurrentUser)
    {
        _likedByCurrentUser = likedByCurrentUser;
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
    public ArrayList<String> getComments()
    {
        return comments;
    }

    @Override
    public void setComments(ArrayList<String> comments)
    {
        this.comments = comments;
    }

    @Override
    public void addCommentFromUser(String userId)
    {
        //No.
    }

    @Override
    public void removeOneCommentFromUser(String userId)
    {
        //No.
    }

    @Override
    public boolean containsCommentFromUser(String userId)
    {
        return false;
    }

    @Override
    public boolean containsCommentFromCurrentUser()
    {
        return _containsCommentFromCurrentUser;
    }

    @Override
    public void setContainsCommentFromCurrentUser(boolean contains)
    {
        _containsCommentFromCurrentUser = contains;
    }
}