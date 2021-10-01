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
        if(password.equals(passwordRepeat))
        {
            _model.createUser(name, email, password);
        }
        else
        {
            _view.errorPasswordsDoNotMatch();
        }
    }

    @Override
    public void logInButtonPressed(String email, String password)
    {
        _model.logUserIn(email, password);
    }

    @Override
    public void appStarted()
    {
        if(_model.isUserSignedIn())
        {
            _view.openMainActivity();
        }
    }

    @Override
    public void userCreatedSuccessfully()
    {
        _view.userCreatedSuccessfully();
    }

    @Override
    public void failedToCreateUserAlreadyExists()
    {
        _view.errorCreateUserAlreadyExists();
    }

    @Override
    public void failedToCreateUserWeakPassword()
    {
        _view.errorCreateUserWeakPassword();
    }

    @Override
    public void failedToCreateUserUnknownError()
    {
        _view.errorCreateUserUnknownError();
    }

    @Override
    public void createUserVerificationEmailSent()
    {
        _view.notifyVerificationEmailSent();
        _view.openLogInView();
    }

    @Override
    public void createUserVerificationEmailNotSent()
    {
        _view.notifyVerificationEmailNotSent();
    }

    @Override
    public void logUserInSuccess()
    {
        _view.openMainActivity();
    }

    @Override
    public void logUserInFailureNotVerified()
    {
        _view.errorUserNotVerified();
    }

    @Override
    public void logUserInFailure()
    {
        _view.errorCannotLogIn();
    }

    @Override
    public void failedToCreateUserEmptyName()
    {
        _view.errorCreateUserEmptyName();
    }

    @Override
    public void failedToCreateUserEmptyEmailOrPassword()
    {
        _view.errorCreateUserEmptyEmailOrPassword();
    }

    @Override
    public void failedToCreateUserInvalidEmail()
    {
        _view.errorCreateUserInvalidEmail();
    }
}