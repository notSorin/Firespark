package com.lesorin.firespark.presenter;

public class StartActivityPresenter implements StartActivityContract.PresenterView, StartActivityContract.PresenterModel
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
        if(name.length() > 0)
        {
            if(password.length() >= 8)
            {
                if(password.equals(passwordRepeat))
                {
                    _model.createUser(name, email, password);
                }
                else
                {
                    _view.errorPasswordsDoNotMatch();
                }
            }
            else
            {
                _view.errorPasswordTooShort();
            }
        }
        else
        {
            _view.errorNameTooShort();
        }
    }

    @Override
    public void userCreatedSuccessfully()
    {
        _view.userCreatedSuccessfully();
    }

    @Override
    public void failedToCreateUser()
    {

    }
}