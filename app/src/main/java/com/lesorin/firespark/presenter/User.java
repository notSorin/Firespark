package com.lesorin.firespark.presenter;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class User
{
    public String _userId;

    @PropertyName("firstlastname")
    public String _firstAndLastName;

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
}