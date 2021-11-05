package com.lesorin.firespark.model.rest;

import static com.lesorin.firespark.model.rest.ModelConstants.*;
import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lesorin.firespark.presenter.Comment;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainModel implements MainContract.Model
{
    private MainContract.PresenterModel _presenter;
    private final HashMap<String, RESTSpark> _sparksCache;
    private final HashMap<String, RESTUser> _usersCache;
    private final HashMap<String, Comment> _commentsCache;
    private final RequestQueue _requestQueue;
    private final SharedPreferences _preferences;
    private final String _token, _userid;
    private final Gson _gson;

    public MainModel(Context context)
    {
        _sparksCache = new HashMap<>();
        _usersCache = new HashMap<>();
        _commentsCache = new HashMap<>();
        _requestQueue = Volley.newRequestQueue(context);
        _preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        _token = _preferences.getString(KEY_TOKEN, "");
        _userid = _preferences.getString(KEY_USERID, "");
        _gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public void setPresenter(MainContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void requestLogout()
    {
        SharedPreferences.Editor editor = _preferences.edit();

        //Remove the user's id and token from the preferences.
        editor.remove(KEY_USERID);
        editor.remove(KEY_TOKEN);
        editor.apply();

        _presenter.responseLogoutSuccess();
    }

    @Override
    public void requestProfileData(String userId)
    {
        final String userid = userId == null ? _userid : userId;

        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == 200)
                {
                    JSONObject message = json.getJSONObject(KEY_MESSAGE);
                    JSONObject jsonUser = message.getJSONObject(KEY_PROFILE);
                    JSONArray jsonSparks = message.getJSONArray(KEY_PROFILE_SPARKS);
                    RESTUser user = getUserFromJSONObject(jsonUser);
                    ArrayList<Spark> sparks = getSparksFromJSONArray(jsonSparks);

                    _presenter.responseProfileDataSuccess(user, sparks);
                }
                else
                {
                    _presenter.responseProfileDataFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseProfileDataFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, GET_PROFILE_DATA_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_USERID, userid);

                return params;
            }

            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_TOKEN_AUTH, _token);

                return params;
            }
        };

        _requestQueue.add(request);
    }

    private RESTUser getUserFromJSONObject(JSONObject jsonProfile) throws JSONException
    {
        RESTUser user = _gson.fromJson(jsonProfile.toString(), RESTUser.class);

        user = processUser(user);

        return user;
    }

    private RESTUser processUser(RESTUser user)
    {
        user.setCurrentUser(user.getId().equals(_userid));
        user.setFollowedByCurrentUser(user.getFollowers().contains(_userid));

        return updateUsersCache(user);
    }

    private RESTUser updateUsersCache(RESTUser user)
    {
        RESTUser userInCache = _usersCache.get(user.getId());

        //If it is in the cache, then update it with the latest data read
        //from the database, because the cache might contain old values.
        if(userInCache != null)
        {
            userInCache.update(user);
        }
        else //If the user is not in cache, insert it.
        {
            _usersCache.put(user.getId(), user);

            userInCache = user;
        }

        return userInCache;
    }

    private ArrayList<Spark> getSparksFromJSONArray(JSONArray sparksArray) throws JSONException
    {
        ArrayList<Spark> sparks = new ArrayList<>();

        for(int i = 0; i < sparksArray.length(); i++)
        {
            RESTSpark spark = _gson.fromJson(sparksArray.getJSONObject(i).toString(), RESTSpark.class);

            sparks.add(processSpark(spark));
        }

        return sparks;
    }

    @Override
    public void requestHomeData()
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == 200)
                {
                    JSONArray jsonSparks = json.getJSONArray(KEY_MESSAGE);
                    ArrayList<Spark> sparks = getSparksFromJSONArray(jsonSparks);

                    _presenter.responseHomeDataSuccess(sparks);
                }
                else
                {
                    _presenter.responseHomeDataFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseHomeDataFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, GET_HOME_DATA_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_TOKEN_AUTH, _token);

                return params;
            }
        };

        _requestQueue.add(request);
    }

    private RESTSpark processSpark(RESTSpark spark)
    {
        spark.setOwnedByCurrentUser(spark.getUserId().equals(_userid));
        spark.setLikedByCurrentUser(spark.getLikes().contains(_userid));
        spark.setContainsCommentFromCurrentUser(spark.getComments().contains(_userid));

        return updateSparksCache(spark);
    }

    private void handleResponseError(JSONObject json)
    {
        try
        {
            int code = json.getInt(KEY_CODE);

            if(code == 400)
            {
                if(json.has(KEY_ERRNO))
                {
                    String errno = json.getString(KEY_ERRNO);

                    if(errno.equals(ERRNO_IC))
                    {
                        _presenter.invalidUserCredentialsDetected();
                    }
                }
            }
        }
        catch(JSONException ignored)
        {
        }
    }

    //Inserts the parameter spark into the cache map.
    //If a spark with the same id as the parameter already exists in the cache map, then the map spark
    //is updated and returned.
    //If a spark with the same id as the parameter is not contained in the cache map, then the
    //parameter is inserted into the map and returned.
    private RESTSpark updateSparksCache(RESTSpark spark)
    {
        RESTSpark sparkInMap = null;

        if(spark != null)
        {
            sparkInMap = _sparksCache.get(spark.getId());

            //If it is in the cache, then update it with the latest data read
            //from the database, because the cache might contain old values.
            if(sparkInMap != null)
            {
                sparkInMap.update(spark);
            }
            else //If the spark is not in cache, insert it.
            {
                _sparksCache.put(spark.getId(), spark);

                sparkInMap = spark;
            }
        }

        return sparkInMap;
    }

    @Override
    public void requestPopularData()
    {

    }

    @Override
    public void requestSendSpark(String sparkBody)
    {

    }

    @Override
    public void requestDeleteSpark(Spark spark)
    {

    }

    @Override
    public void requestLikeDislikeSpark(Spark spark)
    {

    }

    @Override
    public void requestFollowUnfollowUser(User user)
    {

    }

    @Override
    public void requestSearchUserByUsername(String userName)
    {

    }

    @Override
    public void requestSparkData(Spark spark)
    {

    }

    @Override
    public void requestSendComment(Spark spark, String commentBody, Comment replyComment)
    {

    }

    @Override
    public void requestDeleteComment(Comment comment)
    {

    }
}