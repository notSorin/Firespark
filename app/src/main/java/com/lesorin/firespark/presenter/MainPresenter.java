package com.lesorin.firespark.presenter;

import static com.lesorin.firespark.presenter.StartContract.USERNAME_REGEX;
import java.util.ArrayList;

class MainPresenter implements MainContract.PresenterView, MainContract.PresenterModel
{
    private static final int MAX_SPARK_LENGTH = 150;
    private static final int MAX_COMMENT_LENGTH = 150;

    private MainContract.View _view;
    private MainContract.Model _model;
    private boolean _lastRequestWasRefresh;

    @Override
    public void setView(MainContract.View view)
    {
        _view = view;
        _lastRequestWasRefresh = false;
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
    public void requestSearchUserByUsername(String userName)
    {
        if(userName.matches(USERNAME_REGEX))
        {
            _model.requestSearchUserByUsername(userName);
        }
        else
        {
            _view.responseSearchUserByUsernameFailure();
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
                    _view.responseSendSparkTooLong();
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
        //todo
    }

    @Override
    public void responsePopularDataFailure()
    {
        //todo
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
    public void responseSearchUserByUsernameSuccess(User user, ArrayList<Spark> sparks)
    {
        _view.responseSearchUserByUsernameSuccess(user, sparks);
    }

    @Override
    public void responseSearchUserByUsernameFailure()
    {
        _view.responseSearchUserByUsernameFailure();
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
        _view.responseLogoutSuccess();
    }

    @Override
    public void responseNetworkError()
    {
        _view.responseNetworkError();
    }

    @Override
    public void invalidUserCredentialsDetected()
    {
        //TODO inform the user that their credentials expired and take them to the login screen.
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
}