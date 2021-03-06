package com.lesorin.firespark.presenter;

import java.util.ArrayList;

/**
 * Main presenter of the app.
 */
class MainPresenter implements MainContract.PresenterView, MainContract.PresenterModel
{
    private static final int MAX_SPARK_LENGTH = 150;
    private static final int MAX_COMMENT_LENGTH = 150;

    private MainContract.View _view;
    private MainContract.Model _model;
    private boolean _lastRequestWasRefresh, _invalidCredentialsDetected;

    @Override
    public void setView(MainContract.View view)
    {
        _view = view;
        _lastRequestWasRefresh = false;
        _invalidCredentialsDetected = false;
    }

    @Override
    public void setModel(MainContract.Model model)
    {
        _model = model;
    }

    @Override
    public void appStarted()
    {
        _lastRequestWasRefresh = false;

        _model.requestHomeData();
    }

    @Override
    public void requestLogout()
    {
        _model.requestLogout();
    }

    @Override
    public void requestProfileData(String userId)
    {
        _lastRequestWasRefresh = false;

        _model.requestProfileData(userId);
    }

    @Override
    public void requestHomeData()
    {
        _lastRequestWasRefresh = false;

        _model.requestHomeData();
    }

    @Override
    public void requestPopularData()
    {
        _lastRequestWasRefresh = false;

        _model.requestPopularData();
    }

    @Override
    public void requestSparkData(Spark spark)
    {
        _lastRequestWasRefresh = false;

        _model.requestSparkData(spark);
    }

    @Override
    public void requestSendSpark(String sparkBody)
    {
        if(sparkBody != null)
        {
            String temp = sparkBody.replaceAll("\\s+", "");

            if(!temp.isEmpty())
            {
                //Remove all newlines and consecutive spaces.
                sparkBody = sparkBody.replaceAll("\\s+", " ");

                if(sparkBody.length() <= MAX_SPARK_LENGTH)
                {
                    _view.responseSendSparkInProgress();
                    _model.requestSendSpark(sparkBody);
                }
                else
                {
                    _view.responseSendSparkTooLong();
                }
            }
            else
            {
                _view.responseSendSparkEmpty();
            }
        }
        else
        {
            _view.responseSendSparkEmpty();
        }
    }

    @Override
    public void requestDeleteSpark(Spark spark)
    {
        if(spark.isOwnedByCurrentUser())
        {
            _model.requestDeleteSpark(spark);
        }
        else
        {
            _view.responseDeleteSparkFailure();
        }
    }

    @Override
    public void requestLikeUnlikeSpark(Spark spark)
    {
        if(spark.isLikedByCurrentUser()) //Current user already likes this spark, so remove their like.
        {
            _model.requestUnlikeSpark(spark);
        }
        else
        {
            _model.requestLikeSpark(spark);
        }
    }

    @Override
    public void requestFollowUnfollowUser(User user)
    {
        if(user.isFollowedByCurrentUser()) //Current user is following the other user: unfollow them.
        {
            _model.requestUnfollowUser(user);
        }
        else //Current user is not following the other user: follow them.
        {
            _model.requestFollowUser(user);
        }
    }

    @Override
    public void requestSearchUser(String name)
    {
        if(!name.isEmpty())
        {
            _model.requestSearchUser(name);
        }
        else
        {
            _view.responseSearchUserFailure();
        }
    }

    @Override
    public void requestProfileDataRefresh(User user)
    {
        _lastRequestWasRefresh = true;

        _model.requestProfileData(user.getId());
    }

    @Override
    public void requestHomeDataRefresh()
    {
        _lastRequestWasRefresh = true;

        _model.requestHomeData();
    }

    @Override
    public void requestSendComment(Spark spark, String commentBody, Comment replyComment)
    {
        if(spark != null && commentBody != null)
        {
            String temp = commentBody.replaceAll("\\s+", "");

            if(!temp.isEmpty())
            {
                //Remove all newlines and consecutive spaces.
                commentBody = commentBody.replaceAll("\\s+", " ");

                if(commentBody.length() <= MAX_COMMENT_LENGTH)
                {
                    _model.requestSendComment(spark, commentBody, replyComment);
                }
                else
                {
                    _view.responseSendCommentTooLong();
                }
            }
            else
            {
                _view.responseSendCommentEmptyBody();
            }
        }
        else
        {
            _view.responseSendCommentFailure();
        }
    }

    @Override
    public void requestDeleteComment(Comment comment)
    {
        if(comment.isOwnedByCurrentUser())
        {
            _model.requestDeleteComment(comment);
        }
        else
        {
            _view.responseDeleteCommentFailure();
        }
    }

    @Override
    public void requestSparkDataRefresh(Spark spark)
    {
        _lastRequestWasRefresh = true;

        _model.requestSparkData(spark);
    }

    @Override
    public void requestLikeUnlikeComment(Comment comment)
    {
        if(comment.isLikedByCurrentUser()) //Current user already likes this comment, so remove their like.
        {
            _model.requestUnlikeComment(comment);
        }
        else
        {
            _model.requestLikeComment(comment);
        }
    }

    @Override
    public void requestPopularDataRefresh()
    {
        _lastRequestWasRefresh = true;

        _model.requestPopularData();
    }

    @Override
    public void requestUserFollowers(User user)
    {
        _model.requestUserFollowers(user);
    }

    @Override
    public void requestUserFollowing(User user)
    {
        _model.requestUserFollowing(user);
    }

    @Override
    public void responseHomeDataSuccess(ArrayList<Spark> sparks)
    {
        if(_lastRequestWasRefresh)
        {
            _view.responseHomeDataRefreshSuccess(sparks);
        }
        else
        {
            _view.responseHomeDataSuccess(sparks);
        }
    }

    @Override
    public void responseHomeDataFailure()
    {
        if(_lastRequestWasRefresh)
        {
            _view.responseHomeDataRefreshFailure();
        }
        else
        {
            _view.responseHomeDataFailure();
        }
    }

    @Override
    public void responsePopularDataSuccess(ArrayList<Spark> sparks)
    {
        if(_lastRequestWasRefresh)
        {
            _view.responsePopularDataRefreshSuccess(sparks);
        }
        else
        {
            _view.responsePopularDataSuccess(sparks);
        }
    }

    @Override
    public void responsePopularDataFailure()
    {
        if(_lastRequestWasRefresh)
        {
            _view.responsePopularDataRefreshFailure();
        }
        else
        {
            _view.responsePopularDataFailure();
        }
    }


    @Override
    public void responseSendSparkSuccess(Spark spark)
    {
        _view.responseSendSparkSuccess(spark);
    }

    @Override
    public void responseSendSparkFailure()
    {
        _view.responseSendSparkFailure();
    }

    @Override
    public void responseDeleteSparkSuccess(Spark spark)
    {
        _view.responseDeleteSparkSuccess(spark);
    }

    @Override
    public void responseDeleteSparkFailure()
    {
        _view.responseDeleteSparkFailure();
    }

    @Override
    public void responseLikeSparkSuccess(Spark spark)
    {
        _view.responseLikeSparkSuccess(spark);
    }

    @Override
    public void responseLikeSparkFailure()
    {
        _view.responseLikeSparkFailure();
    }

    @Override
    public void responseUnlikeSparkSuccess(Spark spark)
    {
        _view.responseUnlikeSparkSuccess(spark);
    }

    @Override
    public void responseUnlikeSparkFailure()
    {
        _view.responseUnlikeSparkFailure();
    }

    @Override
    public void responseFollowUserSuccess(User user)
    {
        _view.responseFollowUserSuccess(user);
    }

    @Override
    public void responseFollowUserFailure()
    {
        _view.responseFollowUserFailure();
    }

    @Override
    public void responseUnfollowUserSuccess(User user)
    {
        _view.responseUnfollowUserSuccess(user);
    }

    @Override
    public void responseUnfollowUserFailure()
    {
        _view.responseUnfollowUserFailure();
    }

    @Override
    public void responseSearchUserSuccess(ArrayList<User> users)
    {
        _view.responseSearchUserSuccess(users);
    }

    @Override
    public void responseSearchUserFailure()
    {
        _view.responseSearchUserFailure();
    }

    @Override
    public void responseProfileDataSuccess(User user, ArrayList<Spark> sparks)
    {
        if(_lastRequestWasRefresh)
        {
            _view.responseProfileDataRefreshSuccess(user, sparks);
        }
        else
        {
            _view.responseProfileDataSuccess(user, sparks);
        }
    }

    @Override
    public void responseProfileDataFailure()
    {
        if(_lastRequestWasRefresh)
        {
            _view.responseProfileDataRefreshFailure();
        }
        else
        {
            _view.responseProfileDataFailure();
        }
    }

    @Override
    public void responseSparkDataSuccess(Spark spark, ArrayList<Comment> comments)
    {
        if(_lastRequestWasRefresh)
        {
            _view.responseSparkDataRefreshSuccess(spark, comments);
        }
        else
        {
            _view.responseSparkDataSuccess(spark, comments);
        }
    }

    @Override
    public void responseSparkDataFailure()
    {
        if(_lastRequestWasRefresh)
        {
            _view.responseSparkDataRefreshFailure();
        }
        else
        {
            _view.responseSparkDataFailure();
        }
    }

    @Override
    public void responseSendCommentSuccess(Comment comment)
    {
        _view.responseSendCommentSuccess(comment);
    }

    @Override
    public void responseSendCommentFailure()
    {
        _view.responseSendCommentFailure();
    }

    @Override
    public void responseLogoutSuccess()
    {
        if(_invalidCredentialsDetected)
        {
            _view.invalidUserCredentialsDetected();
        }
        else
        {
            _view.responseLogoutSuccess();
        }
    }

    @Override
    public void responseNetworkError()
    {
        _view.responseNetworkError();
    }

    @Override
    public void invalidUserCredentialsDetected()
    {
        _invalidCredentialsDetected = true;

        _model.requestLogout();
    }

    @Override
    public void responseDeleteCommentSuccess(Comment comment)
    {
        _view.responseDeleteCommentSuccess(comment);
    }

    @Override
    public void responseDeleteCommentFailure()
    {
        _view.responseDeleteCommentFailure();
    }

    @Override
    public void responseLikeCommentSuccess(Comment comment)
    {
        _view.responseLikeCommentSuccess(comment);
    }

    @Override
    public void responseLikeCommentFailure()
    {
        _view.responseLikeCommentFailure();
    }

    @Override
    public void responseUnlikeCommentSuccess(Comment comment)
    {
        _view.responseUnlikeCommentSuccess(comment);
    }

    @Override
    public void responseUnlikeCommentFailure()
    {
        _view.responseUnlikeCommentFailure();
    }

    @Override
    public void responseUserFollowersSuccess(User user, ArrayList<User> followers)
    {
        _view.responseUserFollowersSuccess(user, followers);
    }

    @Override
    public void responseUserFollowersFailure()
    {
        _view.responseUserFollowersFailure();
    }

    @Override
    public void responseUserFollowingSuccess(User user, ArrayList<User> following)
    {
        _view.responseUserFollowingSuccess(user, following);
    }

    @Override
    public void responseUserFollowingFailure()
    {
        _view.responseUserFollowingFailure();
    }
}