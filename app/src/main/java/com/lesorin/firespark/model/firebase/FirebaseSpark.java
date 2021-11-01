package com.lesorin.firespark.model.firebase;

import static com.lesorin.firespark.model.firebase.ModelConstants.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;
import java.util.Date;

class FirebaseSpark extends com.lesorin.firespark.presenter.Spark
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

    public FirebaseSpark()
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

    @Override
    public void setLikes(ArrayList<String> likes)
    {
        _likes = likes;
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

    @Override
    public String getUserFirstLastName()
    {
        return _ownerFirstLastName;
    }

    @Override
    public void setUserFirstLastName(String name)
    {
        _ownerFirstLastName = name;
    }

    public boolean isOwnedByCurrentUser()
    {
        return _ownedByCurrentUser;
    }

    public String getOwnerFirstLastName()
    {
        return _ownerFirstLastName;
    }

    public Date getCreated()
    {
        return _created.toDate();
    }

    @Override
    public void setCreated(Date created)
    {
        _created = new Timestamp(created);
    }

    @Override
    public String getUserUsername()
    {
        return _ownerUsername;
    }

    @Override
    public void setUserUsername(String username)
    {
        _ownerUsername = username;
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

    public boolean isDeleted()
    {
        return _deleted;
    }

    @Override
    public void setDeleted(boolean deleted)
    {
        _deleted = deleted;
    }

    public ArrayList<String> getComments()
    {
        return _comments;
    }

    @Override
    public void setComments(ArrayList<String> comments)
    {
        _comments = comments;
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

    @Override
    public String getUserId()
    {
        return _ownerId;
    }

    @Override
    public void setUserId(String userid)
    {
        _ownerId = userid;
    }

    public void addCommentFromUser(String userId)
    {
        _comments.add(userId);
    }

    public void removeOneCommentFromUser(String userId)
    {
        _comments.remove(userId);
    }

    public boolean containsCommentFromUser(String userId)
    {
        return _comments.contains(userId);
    }
}