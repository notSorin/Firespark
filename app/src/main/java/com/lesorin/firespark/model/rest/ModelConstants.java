package com.lesorin.firespark.model.rest;

public class ModelConstants
{
    //Name for Shared Preferences.
    public static final String SHARED_PREFERENCES_NAME = "Firespark Preferences";

    //URL for the server's API.
    private static final String API_URL = "http://35.242.203.63/api/";

    //Versions of the REST API.
    private static final String API_V1_URL = API_URL + "v1/";
    private static final String API_PRE_URL = API_URL + "pre/";

    //Replace USING_API_URL with the version of the API to use.
    private static final String USING_API_URL = API_V1_URL;

    //REST URLS.
    public static final String SIGN_UP_URL = USING_API_URL + "RegisterUser.php";
    public static final String LOG_IN_URL = USING_API_URL + "LoginUser.php";
    public static final String GET_HOME_DATA_URL = USING_API_URL + "GetHomeData.php";
    public static final String GET_PROFILE_DATA_URL = USING_API_URL + "GetProfileData.php";
    public static final String DELETE_SPARK_URL = USING_API_URL + "DeleteSpark.php";
    public static final String LIKE_SPARK_URL = USING_API_URL + "LikeUnlikeSpark.php";
    public static final String UNLIKE_SPARK_URL = USING_API_URL + "LikeUnlikeSpark.php";
    public static final String FOLLOW_USER_URL = USING_API_URL + "FollowUnfollowUser.php";
    public static final String UNFOLLOW_USER_URL = USING_API_URL + "FollowUnfollowUser.php";
    public static final String SEND_SPARK_URL = USING_API_URL + "SendSpark.php";
    public static final String GET_SPARK_DATA_URL = USING_API_URL + "GetSparkData.php";
    public static final String DELETE_COMMENT_URL = USING_API_URL + "DeleteComment.php";
    public static final String LIKE_COMMENT_URL = USING_API_URL + "LikeUnlikeComment.php";
    public static final String UNLIKE_COMMENT_URL = USING_API_URL + "LikeUnlikeComment.php";
    public static final String SEND_COMMENT_URL = USING_API_URL + "SendComment.php";
    public static final String SEARCH_USERS_URL = USING_API_URL + "SearchProfiles.php";
    public static final String GET_POPULAR_DATA_URL = USING_API_URL + "GetPopularSparks.php";
    public static final String GET_USER_FOLLOWERS = USING_API_URL + "GetUserFollowers.php";
    public static final String GET_USER_FOLLOWING = USING_API_URL + "GetUserFollowing.php";

    //JSON keys.
    public static final String KEY_CODE = "code";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_USERID = "userid";
    public static final String KEY_ERRNO = "errno";
    public static final String KEY_TOKEN_AUTH = "Authorization";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_PROFILE_SPARKS = "profile_sparks";
    public static final String KEY_SPARKID = "sparkid";
    public static final String KEY_ACTION = "action";
    public static final String ACTION_LIKE_SPARK = "like";
    public static final String ACTION_UNLIKE_SPARK = "unlike";
    public static final String KEY_FOLLOWEE_ID = "followeeid";
    public static final String ACTION_FOLLOW_USER = "follow";
    public static final String ACTION_UNFOLLOW_USER = "unfollow";
    public static final String KEY_SPARK_BODY = "body";
    public static final String KEY_SPARK = "spark";
    public static final String KEY_SPARK_COMMENTS = "spark_comments";
    public static final String KEY_COMMENT_ID = "commentid";
    public static final String ACTION_LIKE_COMMENT = "like";
    public static final String ACTION_UNLIKE_COMMENT = "unlike";
    public static final String KEY_COMMENT_BODY = "body";
    public static final String KEY_COMMENT_REPLYTOID = "replytoid";
    public static final String KEY_USER_NAME = "name";

    //Response codes.
    public static final int RESPONSE_OK = 200;
    public static final int RESPONSE_ERROR = 400;
    public static final String ERRNO_IC = "ERRNO_IC"; //Invalid credentials.

    //Keys for logging in.
    public static final String KEY_LOG_IN_EMAIL_OR_USERNAME = "email_or_username";
    public static final String KEY_LOG_IN_PASSWORD = "password";

    //Keys for signing up.
    public static final String KEY_SIGN_UP_EMAIL = "email";
    public static final String KEY_SIGN_UP_PASSWORD = "password";
    public static final String KEY_SIGN_UP_USERNAME = "username";
    public static final String KEY_SIGN_UP_FIRST_LAST_NAME = "firstlastname";

}