package com.lesorin.firespark.presenter;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class User
{
    public String _id;

    @PropertyName(USER_FIRSTLASTNAME)
    public String _firstLastName;

    @PropertyName(USER_USERNAME)
    public String _username;

    @PropertyName(USER_USERNAMEINSENSITIVE)
    public String _usernameInsensitive;

    @PropertyName(USER_JOINED)
    public Timestamp joined;

    @PropertyName(USER_FOLLOWERS)
    public ArrayList<String> _followers;

    @PropertyName(USER_FOLLOWING)
    public ArrayList<String> _following;

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
}