package com.lesorin.firespark.presenter;

import java.util.ArrayList;

public class MainActivityPresenter implements MainActivityContract.PresenterView, MainActivityContract.PresenterModel
{
    private final int MAX_SPARK_LENGTH = 150;

    private MainActivityContract.View _view;
    private MainActivityContract.Model _model;

    public MainActivityPresenter()
    {
    }

    @Override
    public void setView(MainActivityContract.View view)
    {
        _view = view;
    }

    @Override
    public void setModel(MainActivityContract.Model model)
    {
        _model = model;
    }

    @Override
    public void appStarted()
    {
    }

    @Override
    public void logOutButtonPressed()
    {
        _model.logUserOut();
        _view.openStartActivity();
    }

    @Override
    public void requestProfileData()
    {
        _model.requestProfileData();
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
    public void sparkClicked(MainActivityContract.Spark spark)
    {
        //todo
    }

    @Override
    public void sendSparkRequested(String sparkBody)
    {
        if(sparkBody != null)
        {
            if(sparkBody.length() == 0)
            {
                _view.errorSendSparkEmpty();
            }
            else
            {
                if(sparkBody.length() <= MAX_SPARK_LENGTH)
                {
                    MainActivityContract.Spark spark = new MainActivityContract.Spark();

                    spark._text = sparkBody.replaceAll("\\s+", " "); //Remove all newlines and consecutive spaces.

                    _view.informSendingSpark();
                    _model.sendSpark(spark);
                }
            }
        }
        else
        {
            _view.errorSendSparkEmpty();
        }
    }

    @Override
    public void profileDataAcquired(MainActivityContract.User user)
    {
        _view.displayProfileData(user);
    }

    @Override
    public void homeDataAcquired(ArrayList<MainActivityContract.Spark> sparks)
    {
        _view.displayHomeData(sparks);
    }

    @Override
    public void popularDataAcquired(ArrayList<MainActivityContract.Spark> sparks)
    {
        _view.displayPopularData(sparks);
    }

    @Override
    public void sendSparkFailure()
    {
        //todo
    }
}