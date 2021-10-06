package com.lesorin.firespark.presenter;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class Comment
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

    public Comment()
    {
        _likes = new ArrayList<>();
    }
}