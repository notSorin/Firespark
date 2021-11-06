package com.lesorin.firespark.presenter;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Abstract model class for a Spark in the network.
 */
public abstract class Spark
{
    /**
     *
     * @return The spark's id.
     */
    public abstract String getId();

    /**
     * Sets the spark's id.
     *
     * @param id The spark's id.
     */
    public abstract void setId(String id);

    /**
     *
     * @return The spark's body.
     */
    public abstract String getBody();

    /**
     * Sets the spark's body.
     *
     * @param body The spark's body.
     */
    public abstract void setBody(String body);

    /**
     * This method is only relevant for the Firebase model.
     *
     * @return A list with the ids of the users who are subscribers to this spark.
     */
    public abstract List<String> getSubscribers();

    /**
     * This method is only relevant for the Firebase model.
     * Adds a user as a subscriber of this spark.
     *
     * @param subscriberId The id of the user who wants to subscribe to the spark.
     */
    public abstract void addSubscriber(String subscriberId);

    /**
     *
     * @return A set with the ids of the users who have liked this spark.
     */
    public abstract Set<String> getLikes();

    /**
     * Sets the ids of the users who have liked this spark.
     *
     * @param likes A set with the ids of the users who have liked this spark.
     */
    public abstract void setLikes(Set<String> likes);

    /**
     *
     * @return The amount of likes that this spark has.
     */
    public abstract int getLikesAmount();

    /**
     *
     * @return The id of the user who owns this spark.
     */
    public abstract String getUserId();

    /**
     * Sets the id of the user who owns this spark.
     *
     * @param userid The id of the user who owns this spark.
     */
    public abstract void setUserId(String userid);

    /**
     *
     * @return True if this spark is owned by the current user, false otherwise.
     */
    public abstract boolean isOwnedByCurrentUser();

    /**
     * Sets whether this spark is owned by the current user.
     *
     * @param ownedByCurrentUser True if this spark is owned by the current user, false otherwise.
     */
    public abstract void setOwnedByCurrentUser(boolean ownedByCurrentUser);

    /**
     *
     * @return The first and last name of the user who is the owner of this spark.
     */
    public abstract String getUserFirstLastName();

    /**
     * Sets the first and last name of the user who is the owner of the spark..
     *
     * @param name The first and last name of the user who is the owner of the spark..
     */
    public abstract void setUserFirstLastName(String name);

    /**
     *
     * @return The date when the spark was created.
     */
    public abstract Date getCreated();

    /**
     * Sets the date when the spark was created.
     *
     * @param created The date when the spark was created.
     */
    public abstract void setCreated(Date created);

    /**
     *
     * @return The username of the user who owns this spark.
     */
    public abstract String getUserUsername();

    /**
     * Sets the username of the user who owns this spark.
     *
     * @param username The username of the user who owns this spark.
     */
    public abstract void setUserUsername(String username);

    /**
     *
     * @return True if this spark is liked by the current user, false otherwise.
     */
    public abstract boolean isLikedByCurrentUser();

    /**
     * Sets whether or not this spark is liked by the current user
     *
     * @param likedByCurrentUser True if this spark is liked by the current user, false otherwise.
     */
    public abstract void setLikedByCurrentUser(boolean likedByCurrentUser);

    /**
     *
     * @return True if this spark is deleted, false otherwise.
     */
    public abstract boolean isDeleted();

    /**
     * Sets whether this spark is deleted or not.
     *
     * @param deleted True if this spark is deleted, false otherwise.
     */
    public abstract void setDeleted(boolean deleted);

    /**
     *
     * @return A list with the ids of the users who have commented on this spark.
     */
    public abstract List<String> getComments();

    /**
     * Sets a list with the ids of the users who have commented on this spark.
     *
     * @param comments A list with the ids of the users who have commented on this spark.
     */
    public abstract void setComments(List<String> comments);

    /**
     * Adds a user id as a user who has commented on this spark.
     *
     * @param userId The id of the user to add.
     */
    public abstract void addCommentFromUser(String userId);

    /**
     * Removes one comment from user with userId from the list of users who have commented on the spark.
     *
     * @param userId The id of the user whose comment to remove.
     */
    public abstract void removeOneCommentFromUser(String userId);

    /**
     *
     * @param userId A user id.
     * @return True if the spark contains a comment from the user with userID, false otherwise.
     */
    public abstract boolean containsCommentFromUser(String userId);

    /**
     *
     * @return True if the spark contains a comment from the current user, false otherwise.
     */
    public abstract boolean containsCommentFromCurrentUser();

    /**
     * Sets whether or not the spark contains a comment from the current user.
     *
     * @param contains True if the spark contains a comment from the current user, false otherwise.
     */
    public abstract void setContainsCommentFromCurrentUser(boolean contains);

    /**
     * Updates this spark with the data from another spark.
     *
     * @param spark Any other spark.
     */
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