package com.lesorin.firespark.model.rest;

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

    public MainModel()
    {
        _sparksCache = new HashMap<>();
        _usersCache = new HashMap<>();
        _commentsCache = new HashMap<>();
    }

    public void setPresenter(MainContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void requestLogout()
    {

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