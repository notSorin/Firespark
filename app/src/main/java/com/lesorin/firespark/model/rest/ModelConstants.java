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
    private static final String USING_API_URL = API_PRE_URL;

    //REST URLS.
    public static final String SIGN_UP_URL = USING_API_URL + "RegisterUser.php";
    public static final String LOG_IN_URL = USING_API_URL + "LoginUser.php";
    public static final String GET_HOME_DATA_URL = USING_API_URL + "GetHomeData.php";
    public static final String GET_PROFILE_DATA_URL = USING_API_URL + "GetProfileData.php";
    public static final String DELETE_SPARK_URL = USING_API_URL + "DeleteSpark.php";

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

    //Error codes.
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