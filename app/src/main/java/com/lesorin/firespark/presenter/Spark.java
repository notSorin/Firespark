package com.lesorin.firespark.presenter;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class Spark
{
    private String _id;
    private boolean _ownedByCurrentUser;

    @PropertyName("ownerid")
    private String _ownerId;

    @PropertyName("ownerfirstlastname")
    private String _ownerFirstLastName;

    @PropertyName("ownerusername")
    private String _ownerUsername;

    @PropertyName("body")
    private String _body;

    @PropertyName("created")
    private Timestamp _created;

    @PropertyName("isdeleted")
    private Timestamp _isDeleted;

    @PropertyName("likes")
    private ArrayList<String> _likes;

    @PropertyName("subscribers")
    private ArrayList<String> _subscribers;

    public Spark()
    {
        _likes = new ArrayList<>();
        _subscribers = new ArrayList<>();
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public void setBody(String body)
    {
        _body = body;
    }

    public void addSubscriber(String subscriberId)
    {
        _subscribers.add(subscriberId);
    }

    public String getBody()
    {
        return _body;
    }

    public ArrayList<String> getLikes()
    {
        return _likes;
    }

    public ArrayList<String> getSubscribers()
    {
        return _subscribers;
    }

    public String getOwnerId()
    {
        return _ownerId;
    }

    public void setOwnedByCurrentUser(boolean ownedByCurrentUser)
    {
        _ownedByCurrentUser = ownedByCurrentUser;
    }

    public boolean isOwnedByCurrentUser()
    {
        return _ownedByCurrentUser;
    }

    public String getOwnerFirstLastName()
    {
        return _ownerFirstLastName;
    }

    public Timestamp getCreated()
    {
        return _created;
    }

    public String getOwnerUsername()
    {
        return _ownerUsername;
    }
}