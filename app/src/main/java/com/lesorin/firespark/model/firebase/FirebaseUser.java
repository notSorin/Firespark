package com.lesorin.firespark.model.firebase;

import static com.lesorin.firespark.model.firebase.ModelConstants.*;
import android.util.ArraySet;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.PropertyName;
import java.util.Date;
import java.util.Set;

class FirebaseUser extends com.lesorin.firespark.presenter.User
{
    private String _id;
    private boolean _isCurrentUser, _isFollowedByCurrentUser;

    @PropertyName(USER_FIRSTLASTNAME)
    private String _firstLastName;

    @PropertyName(USER_USERNAME)
    private String _username;

    @PropertyName(USER_USERNAMEINSENSITIVE)
    private String _usernameInsensitive;

    @PropertyName(USER_JOINED)
    private Timestamp _joined;

    @PropertyName(USER_FOLLOWERS)
    private Set<String> _followers;

    @PropertyName(USER_FOLLOWING)
    private Set<String> _following;

    @PropertyName(USER_VERIFIED)
    private boolean _verified;

    public FirebaseUser()
    {
        _followers = new ArraySet<>();
        _following = new ArraySet<>();
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

    @Override
    public String getUsernameInsensitive()
    {
        return _usernameInsensitive;
    }

    @Override
    public void setUsername(String username)
    {
        _username = username;
        _usernameInsensitive = username.toLowerCase();
    }

    public Set<String> getFollowers()
    {
        return _followers;
    }

    @Override
    public void setFollowers(Set<String> followers)
    {
        _followers = followers;
    }

    public Set<String> getFollowing()
    {
        return _following;
    }

    @Override
    public void setFollowing(Set<String> following)
    {
        _following = following;
    }

    public Date getJoined()
    {
        return _joined.toDate();
    }

    @Override
    public void setJoined(Date joined)
    {
        _joined = new Timestamp(joined);
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

    @Override
    public String getFirstLastName()
    {
        return _firstLastName;
    }

    @Override
    public void setFirstLastName(String name)
    {
        _firstLastName = name;
    }

    public boolean isFollowedByCurrentUser()
    {
        return _isFollowedByCurrentUser;
    }

    public void setFollowedByCurrentUser(boolean followedByCurrentUser)
    {
        _isFollowedByCurrentUser = followedByCurrentUser;
    }

    public String getUsernameinsensitive()
    {
        return _usernameInsensitive;
    }

    public boolean isVerified()
    {
        return false; //Not applicable to Firebase model.
    }

    @Override
    public void setVerified(boolean verified)
    {
        //Not applicable to Firebase model.
    }

    @Override
    public boolean isOriginal()
    {
        return _verified; //For the Firebase model the verified field indicates if a user is original.
    }

    @Override
    public void setOriginal(boolean original)
    {
        _verified = original; //For the Firebase model the verified field indicates if a user is original.
    }
}