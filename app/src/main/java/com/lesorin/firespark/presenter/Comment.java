package com.lesorin.firespark.presenter;

import static com.lesorin.firespark.model.ModelConstants.*;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.ArrayList;

public class Comment
{
    private String _id;
    private boolean _ownedByCurrentUser;

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
    private Timestamp _isDeleted;

    @PropertyName(COMMENT_LIKES)
    private ArrayList<String> _likes;

    public Comment()
    {
        _likes = new ArrayList<>();
    }
}