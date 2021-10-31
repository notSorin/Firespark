package com.lesorin.firespark.model.rest;

import com.lesorin.firespark.presenter.StartContract;

public class StartModel implements StartContract.Model
{
    private StartContract.PresenterModel _presenter;

    public StartModel()
    {
    }

    public void setPresenter(StartContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void requestSignUp(String firstLastName, String username, String email, String password)
    {

    }

    @Override
    public void requestLogIn(String emailOrUsername, String password)
    {

    }

    @Override
    public boolean isUserSignedIn()
    {
        return false;
    }
}