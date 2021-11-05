package com.lesorin.firespark.presenter;

import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class Spark
{
    public abstract String getId();
    public abstract void setId(String id);

    public abstract String getBody();
    public abstract void setBody(String body);

    public abstract List<String> getSubscribers();
    public abstract void addSubscriber(String subscriberId);

    public abstract Set<String> getLikes();
    public abstract void setLikes(Set<String> likes);
    public abstract int getLikesAmount();

    public abstract String getUserId();
    public abstract void setUserId(String userid);

    public abstract boolean isOwnedByCurrentUser();
    public abstract void setOwnedByCurrentUser(boolean ownedByCurrentUser);

    public abstract String getUserFirstLastName();
    public abstract void setUserFirstLastName(String name);

    public abstract Date getCreated();
    public abstract void setCreated(Date created);

    public abstract String getUserUsername();
    public abstract void setUserUsername(String username);

    public abstract boolean isLikedByCurrentUser();
    public abstract void setLikedByCurrentUser(boolean likedByCurrentUser);

    public abstract boolean isDeleted();
    public abstract void setDeleted(boolean deleted);

    public abstract List<String> getComments();
    public abstract void setComments(List<String> comments);
    public abstract void addCommentFromUser(String userId);
    public abstract void removeOneCommentFromUser(String userId);
    public abstract boolean containsCommentFromUser(String userId);

    public abstract boolean containsCommentFromCurrentUser();
    public abstract void setContainsCommentFromCurrentUser(boolean contains);

    public void update(Spark spark)
    {
        setId(spark.getId());
        setOwnedByCurrentUser(spark.isOwnedByCurrentUser());
        setLikedByCurrentUser(spark.isLikedByCurrentUser());
        setUserId(spark.getUserId());
        setUserFirstLastName(spark.getUserFirstLastName());
        setUserUsername(spark.getUserUsername());
        setBody(spark.getBody());
        setCreated(spark.getCreated());
        setDeleted(spark.isDeleted());
        setLikes(spark.getLikes());
        setComments(spark.getComments());
        setContainsCommentFromCurrentUser(spark.containsCommentFromCurrentUser());
    }
}