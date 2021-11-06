package com.lesorin.firespark.presenter;

import java.util.Date;
import java.util.Set;

/**
 * Model class for a comment in the network.
 */
public abstract class Comment
{
    /**
     *
     * @return The comment's id.
     */
    public abstract String getId();

    /**
     * Sets the comment's id.
     *
     * @param id New id for the comment.
     */
    public abstract void setId(String id);

    /**
     *
     * @return True if the comment is owned by the currently connected user, false otherwise.
     */
    public abstract boolean isOwnedByCurrentUser();

    /**
     * Sets whether the comment is owned by the currently connected user, or not.
     *
     * @param ownedByCurrentUser True if the comment is owned by the currently connected user, false otherwise.
     */
    public abstract void setOwnedByCurrentUser(boolean ownedByCurrentUser);

    /**
     *
     * @return The id of the spark to which this comment belongs.
     */
    public abstract String getSparkId();

    /**
     * Sets the spark id to which this comment belongs.
     *
     * @param sparkId The id of the spark to which this comment belongs.
     */
    public abstract void setSparkId(String sparkId);

    /**
     *
     * @return The id of the user owner of the comment.
     */
    public abstract String getUserId();

    /**
     * Sets the id of the user owner of the comment.
     *
     * @param id Id of the user owner of the comment.
     */
    public abstract void setUserId(String id);

    /**
     *
     * @return The first and last name of the user owner of the comment.
     */
    public abstract String getUserFirstLastName();

    /**
     * Sets the first and last name of the user owner of the comment.
     *
     * @param name First and last name of the user owner of the comment.
     */
    public abstract void setUserFirstLastName(String name);

    /**
     *
     * @return The username of the comment's owner.
     */
    public abstract String getUserUsername();

    /**
     * Sets the username of the comment's owner.
     *
     * @param username Username of the comment's owner.
     */
    public abstract void setUserUsername(String username);

    /**
     *
     * @return The comment's body.
     */
    public abstract String getBody();

    /**
     * Sets the comment's body.
     *
     * @param body The comment's body.
     */
    public abstract void setBody(String body);

    /**
     *
     * @return The date when the comment was created.
     */
    public abstract Date getCreated();

    /**
     * Sets the date when the comment was created.
     *
     * @param created The date when the comment was created.
     */
    public abstract void setCreated(Date created);

    /**
     *
     * @return True if this is a deleted comment, false otherwise.
     */
    public abstract boolean isDeleted();

    /**
     * Sets whether this comment is deleted or not.
     *
     * @param deleted True if the comment is deleted, false otherwise.
     */
    public abstract void setDeleted(boolean deleted);

    /**
     *
     * @return A set containing the ids of users who have liked this comment.
     */
    public abstract Set<String> getLikes();

    /**
     * Sets the ids of the users who have liked this comment.
     *
     * @param likes Set of users who have liked this comment.
     */
    public abstract void setLikes(Set<String> likes);

    /**
     *
     * @return The first and last name of the owner of the comment to which this comment is replying to.
     */
    public abstract String getReplyToFirstLastName();

    /**
     * Sets the first and last name of the owner of the comment to which this comment is replying to.
     *
     * @param firstLastName The first and last name of the owner of the comment to which this comment is replying to.
     */
    public abstract void setReplyToFirstLastName(String firstLastName);

    /**
     *
     * @return The id of the comment to which this comment is replying to.
     */
    public abstract String getReplyToId();

    /**
     * Sets the id of the comment to which this comment is replying to.
     *
     * @param id The id of the comment to which this comment is replying to.
     */
    public abstract void setReplyToId(String id);

    /**
     *
     * @return The username of the owner of the comment to which this comment is replying to.
     */
    public abstract String getReplyToUsername();

    /**
     * Sets the username of the owner of the comment to which this comment is replying to.
     *
     * @param username The username of the owner of the comment to which this comment is replying to.
     */
    public abstract void setReplyToUsername(String username);

    /**
     *
     * @return True if this comment is liked by the currently connected user, false otherwise.
     */
    public abstract boolean isLikedByCurrentUser();

    /**
     * Sets whether this comment is liked by the currently connected user.
     *
     * @param liked True if this comment is liked by the currently connected user, false otherwise.
     */
    public abstract void setLikedByCurrentUser(boolean liked);

    /**
     * Updates this comment with the data from another comment.
     *
     * @param comment The comment to use to update this comment.
     */
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
        setReplyToId(comment.getReplyToId());
        setReplyToFirstLastName(comment.getReplyToFirstLastName());
        setReplyToUsername(comment.getReplyToUsername());
    }
}