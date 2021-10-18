package com.lesorin.firespark.presenter.pojo;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class Spark
{
    private String _id;
    private boolean _ownedByCurrentUser, _likedByCurrentUser, _containsCommentFromCurrentUser;

    @PropertyName(SPARK_OWNERID)
    private String _ownerId;

    @PropertyName(SPARK_OWNERFIRSTLASTNAME)
    private String _ownerFirstLastName;

    @PropertyName(SPARK_OWNERUSERNAME)
    private String _ownerUsername;

    @PropertyName(SPARK_BODY)
    private String _body;

    @PropertyName(SPARK_CREATED)
    private Timestamp _created;

    @PropertyName(SPARK_DELETED)
    private boolean _deleted;

    @PropertyName(SPARK_LIKES)
    private ArrayList<String> _likes;

    @PropertyName(SPARK_SUBSCRIBERS)
    private ArrayList<String> _subscribers;

    @PropertyName(SPARK_COMMENTS)
    private ArrayList<String> _comments;

    public Spark()
    {
        _likes = new ArrayList<>();
        _subscribers = new ArrayList<>();
        _comments = new ArrayList<>();
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

    public boolean isLikedByCurrentUser()
    {
        return _likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser)
    {
        _likedByCurrentUser = likedByCurrentUser;
    }

    public void update(Spark spark)
    {
        _id = spark.getId();
        _ownedByCurrentUser = spark.isOwnedByCurrentUser();
        _likedByCurrentUser = spark.isLikedByCurrentUser();
        _ownerId = spark.getOwnerId();
        _ownerFirstLastName = spark.getOwnerFirstLastName();
        _ownerUsername = spark.getOwnerUsername();
        _body = spark.getBody();
        _created = spark.getCreated();
        _deleted = spark.isDeleted();
        _likes = spark.getLikes();
        _comments = spark.getComments();
        _containsCommentFromCurrentUser = spark.containsCommentFromCurrentUser();
    }

    public boolean isDeleted()
    {
        return _deleted;
    }

    public ArrayList<String> getComments()
    {
        return _comments;
    }

    public boolean containsCommentFromCurrentUser()
    {
        return _containsCommentFromCurrentUser;
    }

    public void setContainsCommentFromCurrentUser(boolean contains)
    {
        _containsCommentFromCurrentUser = contains;
    }

    public int getLikesAmount()
    {
        return _likes.size();
    }
}