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

/**
 * Main REST model of the app.
 */
public class MainModel implements MainContract.Model
{
    private MainContract.PresenterModel _presenter;
    private final HashMap<String, RESTSpark> _sparksCache;
    private final HashMap<String, RESTUser> _usersCache;
    private final HashMap<String, RESTComment> _commentsCache;
    private final RequestQueue _requestQueue;
    private final SharedPreferences _preferences;
    private final String _token, _userid;
    private final Gson _gson;

    /**
     * Instantiates a new MainModel.
     *
     * @param context Context required by the model.
     */
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

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
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

        user.setCurrentUser(user.getId().equals(_userid));

        if(user.getFollowers() != null)
        {
            user.setFollowedByCurrentUser(user.getFollowers().contains(_userid));
        }

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
            RESTSpark spark = getSparkFromJSONObject(sparksArray.getJSONObject(i));

            sparks.add(spark);
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

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
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

    private void handleResponseError(JSONObject json)
    {
        try
        {
            int code = json.getInt(KEY_CODE);

            if(code == RESPONSE_ERROR)
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
        //todo
    }

    @Override
    public void requestSendSpark(String sparkBody)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    RESTSpark spark = getSparkFromJSONObject(json.getJSONObject(KEY_MESSAGE));

                    _presenter.responseSendSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseSendSparkFailure();
                    handleResponseError(json);
                }
            }
            catch(Exception e)
            {
                _presenter.responseSendSparkFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, SEND_SPARK_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SPARK_BODY, sparkBody);

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

    @Override
    public void requestDeleteSpark(Spark spark)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    _sparksCache.remove(spark.getId());
                    _presenter.responseDeleteSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseDeleteSparkFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseDeleteSparkFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, DELETE_SPARK_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SPARKID, spark.getId());

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

    @Override
    public void requestFollowUser(User user)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    updateUsersAfterFollow(user);
                    _presenter.responseFollowUserSuccess(user);
                }
                else
                {
                    _presenter.responseFollowUserFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseFollowUserFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, FOLLOW_USER_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_FOLLOWEE_ID, user.getId());
                params.put(KEY_ACTION, ACTION_FOLLOW_USER);

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

    private void updateUsersAfterFollow(User followedUser)
    {
        followedUser.getFollowers().add(_userid);
        followedUser.setFollowedByCurrentUser(true);

        //Also update the current user if they are in the cache.
        User currentUser = _usersCache.get(_userid);

        if(currentUser != null)
        {
            currentUser.getFollowing().add(followedUser.getId());
        }
    }

    private void updateUsersAfterUnfollow(User unfollowedUser)
    {
        unfollowedUser.getFollowers().remove(_userid);
        unfollowedUser.setFollowedByCurrentUser(false);

        //Also update the current user if they are in the cache.
        User currentUser = _usersCache.get(_userid);

        if(currentUser != null)
        {
            currentUser.getFollowing().remove(unfollowedUser.getId());
        }
    }

    @Override
    public void requestUnfollowUser(User user)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    updateUsersAfterUnfollow(user);
                    _presenter.responseUnfollowUserSuccess(user);
                }
                else
                {
                    _presenter.responseUnfollowUserFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseUnfollowUserFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, UNFOLLOW_USER_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_FOLLOWEE_ID, user.getId());
                params.put(KEY_ACTION, ACTION_UNFOLLOW_USER);

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

    public void requestLikeSpark(Spark spark)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    spark.getLikes().add(_userid);
                    spark.setLikedByCurrentUser(true);
                    _presenter.responseLikeSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseLikeSparkFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseLikeSparkFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, LIKE_SPARK_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SPARKID, spark.getId());
                params.put(KEY_ACTION, ACTION_LIKE_SPARK);

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

    public void requestUnlikeSpark(Spark spark)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    spark.getLikes().remove(_userid);
                    spark.setLikedByCurrentUser(false);
                    _presenter.responseUnlikeSparkSuccess(spark);
                }
                else
                {
                    _presenter.responseUnlikeSparkFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseUnlikeSparkFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, UNLIKE_SPARK_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SPARKID, spark.getId());
                params.put(KEY_ACTION, ACTION_UNLIKE_SPARK);

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

    @Override
    public void requestLikeComment(Comment comment)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    comment.getLikes().add(_userid);
                    comment.setLikedByCurrentUser(true);
                    _presenter.responseLikeCommentSuccess(comment);
                }
                else
                {
                    _presenter.responseLikeCommentFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseLikeCommentFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, LIKE_COMMENT_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_COMMENT_ID, comment.getId());
                params.put(KEY_ACTION, ACTION_LIKE_COMMENT);

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

    @Override
    public void requestUnlikeComment(Comment comment)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    comment.getLikes().remove(_userid);
                    comment.setLikedByCurrentUser(false);
                    _presenter.responseUnlikeCommentSuccess(comment);
                }
                else
                {
                    _presenter.responseUnlikeCommentFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseUnlikeCommentFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, UNLIKE_COMMENT_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_COMMENT_ID, comment.getId());
                params.put(KEY_ACTION, ACTION_UNLIKE_COMMENT);

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

    @Override
    public void requestSearchUser(String usernameOrName)
    {
        //todo
    }

    @Override
    public void requestSparkData(Spark spark)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    JSONObject message = json.getJSONObject(KEY_MESSAGE);
                    JSONObject jsonSpark = message.getJSONObject(KEY_SPARK);
                    JSONArray jsonComments = message.getJSONArray(KEY_SPARK_COMMENTS);
                    RESTSpark newSpark = getSparkFromJSONObject(jsonSpark);
                    ArrayList<Comment> comments = getCommentsFromJSONArray(jsonComments);

                    _presenter.responseSparkDataSuccess(newSpark, comments);
                }
                else
                {
                    _presenter.responseSparkDataFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseSparkDataFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, GET_SPARK_DATA_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SPARKID, spark.getId());

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

    private RESTSpark getSparkFromJSONObject(JSONObject jsonSpark)
    {
        RESTSpark spark = _gson.fromJson(jsonSpark.toString(), RESTSpark.class);

        spark.setOwnedByCurrentUser(spark.getUserId().equals(_userid));
        spark.setLikedByCurrentUser(spark.getLikes().contains(_userid));
        spark.setContainsCommentFromCurrentUser(spark.getComments().contains(_userid));

        return updateSparksCache(spark);
    }

    private ArrayList<Comment> getCommentsFromJSONArray(JSONArray jsonComments) throws JSONException
    {
        ArrayList<Comment> comments = new ArrayList<>();

        for(int i = 0; i < jsonComments.length(); i++)
        {
            RESTComment comment = getCommentFromJSONObject(jsonComments.getJSONObject(i));

            comments.add(comment);
        }

        return comments;
    }

    private RESTComment getCommentFromJSONObject(JSONObject jsonObject)
    {
        RESTComment comment = _gson.fromJson(jsonObject.toString(), RESTComment.class);

        comment.setOwnedByCurrentUser(comment.getUserId().equals(_userid));
        comment.setLikedByCurrentUser(comment.getLikes().contains(_userid));

        return updateCommentsCache(comment);
    }

    //Inserts the parameter comment into the cache map.
    //If a comment with the same id as the parameter already exists in the cache map, then the map comment
    //is updated and returned.
    //If a comment with the same id as the parameter is not contained in the cache map, then the
    //parameter is inserted into the map and returned.
    private RESTComment updateCommentsCache(RESTComment comment)
    {
        RESTComment commentInMap = null;

        if(comment != null)
        {
            commentInMap = _commentsCache.get(comment.getId());

            //If it is in the cache, then update it with the latest data read
            //from the database, because the cache might contain old values.
            if(commentInMap != null)
            {
                commentInMap.update(comment);
            }
            else //If the spark is not in cache, insert it.
            {
                _commentsCache.put(comment.getId(), comment);

                commentInMap = comment;
            }
        }

        return commentInMap;
    }

    @Override
    public void requestSendComment(Spark spark, String commentBody, Comment replyComment)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    RESTComment comment = getCommentFromJSONObject(json.getJSONObject(KEY_MESSAGE));

                    addCommentToSpark(comment);
                    _presenter.responseSendCommentSuccess(comment);
                }
                else
                {
                    _presenter.responseSendCommentFailure();
                    handleResponseError(json);
                }
            }
            catch(Exception e)
            {
                _presenter.responseSendCommentFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, SEND_COMMENT_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SPARKID, spark.getId());
                params.put(KEY_COMMENT_BODY, commentBody);
                params.put(KEY_COMMENT_REPLYTOID, replyComment != null ? replyComment.getId() : "null");

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

    @Override
    public void requestDeleteComment(Comment comment)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == RESPONSE_OK)
                {
                    removeCommentFromSpark(comment);
                    _commentsCache.remove(comment.getId());
                    _presenter.responseDeleteCommentSuccess(comment);
                }
                else
                {
                    _presenter.responseDeleteCommentFailure();
                    handleResponseError(json);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseDeleteCommentFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, DELETE_COMMENT_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_COMMENT_ID, comment.getId());

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

    private void removeCommentFromSpark(Comment comment)
    {
        Spark spark = _sparksCache.get(comment.getSparkId());

        if(spark != null)
        {
            spark.getComments().remove(comment.getUserId());
            spark.setContainsCommentFromCurrentUser(spark.getComments().contains(_userid));
        }
    }

    private void addCommentToSpark(RESTComment comment)
    {
        Spark spark = _sparksCache.get(comment.getSparkId());

        if(spark != null)
        {
            spark.getComments().add(_userid);
            spark.setContainsCommentFromCurrentUser(true);
        }
    }
}