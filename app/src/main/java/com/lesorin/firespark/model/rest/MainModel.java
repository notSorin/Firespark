package com.lesorin.firespark.model.rest;

import static com.lesorin.firespark.model.rest.ModelConstants.*;
import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import java.util.HashMap;

public class MainModel implements MainContract.Model
{
    private MainContract.PresenterModel _presenter;
    private final HashMap<String, Spark> _sparksCache;
    private final HashMap<String, User> _usersCache;
    private final HashMap<String, Comment> _commentsCache;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;

    public MainModel(Context context)
    {
        _sparksCache = new HashMap<>();
        _usersCache = new HashMap<>();
        _commentsCache = new HashMap<>();
        _requestQueue = Volley.newRequestQueue(context);
        _preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
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