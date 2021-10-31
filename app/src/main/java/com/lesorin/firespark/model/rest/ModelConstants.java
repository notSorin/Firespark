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

    //Replace USING_API_URL this with the version of the API to use.
    private static final String USING_API_URL = API_PRE_URL;

    //REST URLS.
    public static final String SIGN_UP_URL = USING_API_URL + "RegisterUser.php";
    public static final String LOG_IN_URL = USING_API_URL + "LoginUser.php";

    //JSON keys.
    public static final String KEY_CODE = "code";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TOKEN = "token";
}