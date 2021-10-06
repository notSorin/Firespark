package com.lesorin.firespark.presenter;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class User
{
    public String _id;

    @PropertyName("firstlastname")
    public String _firstLastName;

    @PropertyName("username")
    public String _username;

    @PropertyName("usernameinsensitive")
    public String _usernameInsensitive;

    @PropertyName("joined")
    public Timestamp joined;

    @PropertyName("followers")
    public ArrayList<String> _followers;

    @PropertyName("following")
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