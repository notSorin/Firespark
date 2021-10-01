package com.lesorin.firespark.presenter;

public class StartActivityPresenter implements StartActivityContract.Presenter
{
    private StartActivityContract.View _view;
    private StartActivityContract.Model _model;

    public StartActivityPresenter()
    {
    }

    @Override
    public void setView(StartActivityContract.View view)
    {
        _view = view;
    }

    @Override
    public void setModel(StartActivityContract.Model model)
    {
        _model = model;
    }

    @Override
    public void signUpButtonPressed(String name, String email, String password, String passwordRepeat)
    {
        _model.createUser(name, email, password);
    }
}