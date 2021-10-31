package com.lesorin.firespark.presenter;

import static com.lesorin.firespark.presenter.StartContract.FIRST_LAST_NAME_REGEX;
import static com.lesorin.firespark.presenter.StartContract.USERNAME_REGEX;

class StartPresenter implements StartContract.PresenterView, StartContract.PresenterModel
{
    private StartContract.View _view;
    private StartContract.Model _model;

    @Override
    public void setView(StartContract.View view)
    {
        _view = view;
    }

    @Override
    public void setModel(StartContract.Model model)
    {
        _model = model;
    }

    @Override
    public void requestSignUp(String firstLastName, String username, String email, String password, String passwordRepeat)
    {
        if(firstLastName.matches(FIRST_LAST_NAME_REGEX))
        {
            if(username.matches(USERNAME_REGEX))
            {
                if(password.equals(passwordRepeat))
                {
                    _model.requestSignUp(firstLastName, username, email, password);
                }
                else
                {
                    _view.responseSignUpPasswordsDoNotMatch();
                }
            }
            else
            {
                _view.responseSignUpInvalidUsername();
            }
        }
        else
        {
            _view.responseSignUpInvalidFirstLastName();
        }
    }

    @Override
    public void requestLogIn(String emailOrUsername, String password)
    {
        _model.requestLogIn(emailOrUsername, password);
    }

    @Override
    public void appStarted()
    {
        if(_model.isUserSignedIn())
        {
            _view.responseLogInSuccess();
        }
    }

    @Override
    public void responseSignUpSuccess()
    {
        _view.responseSignUpSuccess();
    }

    @Override
    public void responseSignUpEmailNotAvailable()
    {
        _view.responseSignUpEmailNotAvailable();
    }

    @Override
    public void responseSignUpWeakPassword()
    {
        _view.responseSignUpWeakPassword();
    }

    @Override
    public void responseSignUpUnknownError()
    {
        _view.responseSignUpUnknownError();
    }

    @Override
    public void responseSignUpVerificationEmailSent()
    {
        _view.responseSignUpVerificationEmailSent();
    }

    @Override
    public void responseSignUpVerificationEmailNotSent()
    {
        _view.responseSignUpVerificationEmailNotSent();
    }

    @Override
    public void responseLogInSuccess()
    {
        _view.responseLogInSuccess();
    }

    @Override
    public void responseLogInEmailNotVerified()
    {
        _view.responseLogInEmailNotVerified();
    }

    @Override
    public void responseLogInFailure()
    {
        _view.responseLogInFailure();
    }

    @Override
    public void responseSignUpEmptyName()
    {
        _view.responseSignUpEmptyName();
    }

    @Override
    public void responseSignUpEmptyEmailOrPassword()
    {
        _view.responseSignUpEmptyEmailOrPassword();
    }

    @Override
    public void responseSignUpInvalidEmail()
    {
        _view.responseSignUpInvalidEmail();
    }

    @Override
    public void responseSignUpUsernameNotAvailable()
    {
        _view.responseSignUpUsernameNotAvailable();
    }
}