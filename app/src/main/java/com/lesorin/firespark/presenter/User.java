package com.lesorin.firespark.presenter;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class User
{
    private String _id;
    private boolean _isCurrentUser;

    @PropertyName(USER_FIRSTLASTNAME)
    private String _firstLastName;

    @PropertyName(USER_USERNAME)
    private String _username;

    @PropertyName(USER_USERNAMEINSENSITIVE)
    private String _usernameInsensitive;

    @PropertyName(USER_JOINED)
    private Timestamp _joined;

    @PropertyName(USER_FOLLOWERS)
    private ArrayList<String> _followers;

    @PropertyName(USER_FOLLOWING)
    private ArrayList<String> _following;

    public User()
    {
        _followers = new ArrayList<>();
        _following = new ArrayList<>();
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getFirstlastname()
    {
        return _firstLastName;
    }

    public String getUsername()
    {
        return _username;
    }

    public ArrayList<String> getFollowers()
    {
        return _followers;
    }

    public ArrayList<String> getFollowing()
    {
        return _following;
    }

    public Timestamp getJoined()
    {
        return _joined;
    }

    public void setCurrentUser(boolean isCurrentUser)
    {
        _isCurrentUser = isCurrentUser;
    }

    public boolean isCurrentUser()
    {
        return _isCurrentUser;
    }

    public String getId()
    {
        return _id;
    }
}