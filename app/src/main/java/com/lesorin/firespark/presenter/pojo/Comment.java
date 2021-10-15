package com.lesorin.firespark.presenter.pojo;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class Comment
{
    private String _id;
    private boolean _ownedByCurrentUser, _likedByCurrentUser;

    @PropertyName(COMMENT_OWNERID)
    private String _ownerId;

    @PropertyName(COMMENT_OWNERFIRSTLASTNAME)
    private String _ownerFirstLastName;

    @PropertyName(COMMENT_OWNERUSERNAME)
    private String _ownerUsername;

    @PropertyName(COMMENT_BODY)
    private String _body;

    @PropertyName(COMMENT_CREATED)
    private Timestamp _created;

    @PropertyName(COMMENT_DELETED)
    private boolean _deleted;

    @PropertyName(COMMENT_LIKES)
    private ArrayList<String> _likes;

    @PropertyName(COMMENT_REPLYTOFIRSTLASTNAME)
    private String _replyToFirstLastName;

    @PropertyName(COMMENT_REPLYTOUSERNAME)
    private String _replyToUsername;

    public Comment()
    {
        _likes = new ArrayList<>();
    }

    public String getId()
    {
        return _id;
    }

    public boolean isOwnedByCurrentUser()
    {
        return _ownedByCurrentUser;
    }

    public void setOwnedByCurrentUser(boolean ownedByCurrentUser)
    {
        _ownedByCurrentUser = ownedByCurrentUser;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getOwnerId()
    {
        return _ownerId;
    }

    public void setOwnerId(String id)
    {
        _ownerId = id;
    }

    public String getOwnerFirstLastName()
    {
        return _ownerFirstLastName;
    }

    public void setOwnerFirstLastName(String name)
    {
        _ownerFirstLastName = name;
    }

    public String getOwnerUsername()
    {
        return _ownerUsername;
    }

    public void setOwnerUserName(String username)
    {
        _ownerUsername = username;
    }

    public String getBody()
    {
        return _body;
    }

    public void setBody(String body)
    {
        _body = body;
    }

    public Timestamp getCreated()
    {
        return _created;
    }

    public void setCreated(Timestamp created)
    {
        _created = created;
    }

    public boolean getDeleted()
    {
        return _deleted;
    }

    public void setDeleted(boolean deleted)
    {
        _deleted = deleted;
    }

    public ArrayList<String> getLikes()
    {
        return _likes;
    }

    public void setLikes(ArrayList<String> likes)
    {
        _likes = likes;
    }

    public String getReplyToFirstLastName()
    {
        return _replyToFirstLastName;
    }

    public void setReplyToFirstLastName(String firstLastName)
    {
        _replyToFirstLastName = firstLastName;
    }

    public String getReplyToUsername()
    {
        return _replyToUsername;
    }

    public void setReplyToUsername(String username)
    {
        _replyToUsername = username;
    }

    public boolean isLikedByCurrentUser()
    {
        return _likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean liked)
    {
        _likedByCurrentUser = liked;
    }
}