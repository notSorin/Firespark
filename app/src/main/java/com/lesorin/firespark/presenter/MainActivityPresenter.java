package com.lesorin.firespark.presenter;

public class MainActivityPresenter implements MainActivityContract.PresenterView, MainActivityContract.PresenterModel
{
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
}