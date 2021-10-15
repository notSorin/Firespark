package com.lesorin.firespark.presenter;

import static com.lesorin.firespark.presenter.StartContract.USERNAME_REGEX;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import java.util.ArrayList;

class MainPresenter implements MainContract.PresenterView, MainContract.PresenterModel
{
    private static final int MAX_SPARK_LENGTH = 150;

    private MainContract.View _view;
    private MainContract.Model _model;

    @Override
    public void setView(MainContract.View view)
    {
        _view = view;
    }

    @Override
    public void setModel(MainContract.Model model)
    {
        _model = model;
    }

    @Override
    public void appStarted()
    {
        _model.requestHomeData();
    }

    @Override
    public void logOutButtonPressed()
    {
        _model.logUserOut();
        _view.userLoggedOutSuccessfully();
    }

    @Override
    public void requestProfileData(String userId)
    {
        _model.requestProfileData(userId);
    }

    @Override
    public void requestHomeData()
    {
        _model.requestHomeData();
    }

    @Override
    public void requestPopularData()
    {
        _model.requestPopularData();
    }

    @Override
    public void requestSparkData(Spark spark)
    {
        _model.requestSparkData(spark);
    }

    @Override
    public void sendSparkRequested(String sparkBody)
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
                    _view.informSendingSpark();
                    _model.sendSpark(sparkBody);
                }
                else
                {
                    _view.errorSendSparkTooLong();
                }
            }
            else
            {
                _view.errorSendSparkEmpty();
            }
        }
        else
        {
            _view.errorSendSparkEmpty();
        }
    }

    @Override
    public void sparkDeleteClicked(Spark spark)
    {
        _model.deleteSpark(spark);
    }

    @Override
    public void sparkLikeClicked(Spark spark)
    {
        _model.likeDislikeSpark(spark);
    }

    @Override
    public void userFollowClicked(User user)
    {
        _model.followUnfollowUser(user);
    }

    @Override
    public void requestSearchUserByUsername(String userName)
    {
        if(userName.matches(USERNAME_REGEX))
        {
            _model.searchUserByUsername(userName);
        }
        else
        {
            _view.searchUserByUsernameFailure();
        }
    }

    @Override
    public void profileDataAcquired(User user)
    {
        _view.displayProfileData(user);
    }

    @Override
    public void homeDataAcquired(ArrayList<Spark> sparks)
    {
        _view.displayHomeData(sparks);
    }

    @Override
    public void popularDataAcquired(ArrayList<Spark> sparks)
    {
        _view.displayPopularData(sparks);
    }


    @Override
    public void sendSparkResult(Spark spark)
    {
        if(spark != null)
        {
            _view.sparkSentSuccessfully(spark);
        }
        else
        {
            _view.errorSendSparkUnknown();
        }
    }

    @Override
    public void deleteSparkFailure()
    {
        _view.deleteSparkError();
    }

    @Override
    public void deleteSparkSuccess(Spark spark)
    {
        _view.deleteSparkSuccess(spark);
    }

    @Override
    public void addSparkLikeSuccess(Spark spark)
    {
        _view.addSparkLikeSuccess(spark);
    }

    @Override
    public void addSparkLikeFailure(Spark spark)
    {
        _view.addSparkLikeFailure(spark);
    }

    @Override
    public void removeSparkLikeSuccess(Spark spark)
    {
        _view.removeSparkLikeSuccess(spark);
    }

    @Override
    public void removeSparkLikeFailure(Spark spark)
    {
        _view.removeSparkLikeFailure(spark);
    }

    @Override
    public void followUserSuccess(User user)
    {
        _view.followUserSuccess(user);
    }

    @Override
    public void followUserFailure()
    {
        _view.followUserFailure();
    }

    @Override
    public void unfollowUserSuccess(User user)
    {
        _view.unfollowUserSuccess(user);
    }

    @Override
    public void unfollowUserFailure()
    {
        _view.unfollowUserFailure();
    }

    @Override
    public void searchUserFailure()
    {
        _view.searchUserByUsernameFailure();
    }

    @Override
    public void searchUserSuccess(User user, ArrayList<Spark> sparks)
    {
        _view.searchUserByUsernameSuccess(user, sparks);
    }

    @Override
    public void requestProfileDataSuccess(User user, ArrayList<Spark> sparks)
    {
        _view.requestProfileDataSuccess(user, sparks);
    }

    @Override
    public void requestProfileDataFailure()
    {
        _view.requestProfileDataFailure();
    }

    @Override
    public void requestSparkDataSuccess(Spark spark, ArrayList<Comment> comments)
    {
        _view.requestSparkDataSuccess(spark, comments);
    }
}