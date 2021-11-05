package com.lesorin.firespark.presenter;

import java.util.Date;
import java.util.Set;

public abstract class Comment
{
    public abstract String getId();
    public abstract void setId(String id);

    public abstract boolean isOwnedByCurrentUser();
    public abstract void setOwnedByCurrentUser(boolean ownedByCurrentUser);

    public abstract String getSparkId();
    public abstract void setSparkId(String sparkId);

    public abstract String getUserId();
    public abstract void setUserId(String id);

    public abstract String getUserFirstLastName();
    public abstract void setUserFirstLastName(String name);

    public abstract String getUserUsername();
    public abstract void setUserUsername(String username);

    public abstract String getBody();
    public abstract void setBody(String body);

    public abstract Date getCreated();
    public abstract void setCreated(Date created);

    public abstract boolean isDeleted();
    public abstract void setDeleted(boolean deleted);

    public abstract Set<String> getLikes();
    public abstract void setLikes(Set<String> likes);

    public abstract String getReplyToFirstLastName();
    public abstract void setReplyToFirstLastName(String firstLastName);

    public abstract String getReplyToId();
    public abstract void setReplyToId(String id);

    public abstract String getReplyToUsername();
    public abstract void setReplyToUsername(String username);

    public abstract boolean isLikedByCurrentUser();
    public abstract void setLikedByCurrentUser(boolean liked);

    public void update(Comment comment)
    {
        setId(comment.getId());
        setOwnedByCurrentUser(comment.isOwnedByCurrentUser());
        setLikedByCurrentUser(comment.isLikedByCurrentUser());
        setSparkId(comment.getSparkId());
        setUserId(comment.getUserId());
        setUserFirstLastName(comment.getUserFirstLastName());
        setUserUsername(comment.getUserUsername());
        setBody(comment.getBody());
        setCreated(comment.getCreated());
        setDeleted(comment.isDeleted());
        setLikes(comment.getLikes());
        setReplyToFirstLastName(comment.getReplyToFirstLastName());
        setReplyToUsername(comment.getReplyToUsername());
    }
}