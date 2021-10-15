package com.lesorin.firespark.model;

public class ModelConstants
{
    //Collections.
    public static final String SPARKS_COLLECTION = "sparks";
    public static final String USERS_COLLECTION = "users";
    public static final String COMMENTS_COLLECTION = "comments";

    //User fields.
    public static final String USER_FIRSTLASTNAME = "firstlastname";
    public static final String USER_USERNAME = "username";
    public static final String USER_USERNAMEINSENSITIVE = "usernameinsensitive";
    public static final String USER_JOINED = "joined";
    public static final String USER_FOLLOWERS = "followers";
    public static final String USER_FOLLOWING = "following";
    public static final String USER_VERIFIED = "verified";

    //Spark fields.
    public static final String SPARK_OWNERID = "ownerid";
    public static final String SPARK_OWNERFIRSTLASTNAME = "ownerfirstlastname";
    public static final String SPARK_OWNERUSERNAME = "ownerusername";
    public static final String SPARK_BODY = "body";
    public static final String SPARK_CREATED = "created";
    public static final String SPARK_DELETED = "deleted";
    public static final String SPARK_LIKES = "likes";
    public static final String SPARK_SUBSCRIBERS = "subscribers";

    //Comments fields.
    public static final String COMMENT_SPARKID = "sparkid";
    public static final String COMMENT_OWNERID = "ownerid";
    public static final String COMMENT_OWNERFIRSTLASTNAME = "ownerfirstlastname";
    public static final String COMMENT_OWNERUSERNAME = "ownerusername";
    public static final String COMMENT_BODY = "body";
    public static final String COMMENT_CREATED = "created";
    public static final String COMMENT_DELETED = "deleted";
    public static final String COMMENT_LIKES = "likes";
    public static final String COMMENT_REPLYTOFIRSTLASTNAME = "replytofirstlastname";
    public static final String COMMENT_REPLYTOUSERNAME = "replytousername";

}