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
    private final HashMap<String, Spark> _sparksCache;
    private final HashMap<String, User> _usersCache;
    private final HashMap<String, Comment> _commentsCache;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;
    private String _token, _userid;
    private Gson _gson;

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
        _preferences.edit().clear().apply();
        _presenter.responseLogoutSuccess();
    }

    @Override
    public void requestProfileData(String userId)
    {

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
                    JSONArray sparksArray = json.getJSONArray(KEY_MESSAGE);
                    ArrayList<Spark> sparks = new ArrayList<>();

                    for(int i = 0; i < sparksArray.length(); i++)
                    {
                        Spark spark = _gson.fromJson(sparksArray.getJSONObject(i).toString(), RESTSpark.class);

                        sparks.add(processSpark(spark));
                    }

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

    private Spark processSpark(Spark spark)
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
    private Spark updateSparksCache(Spark spark)
    {
        Spark sparkInMap = null;

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