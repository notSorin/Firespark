package com.lesorin.firespark.presenter;

public interface StartContract
{
    String FIRST_LAST_NAME_REGEX = "^[a-zA-Z0-9 ]{1,30}$";
    String USERNAME_REGEX = "^[a-zA-Z0-9]{1,20}$";

    //All methods starting with "request" are methods which can be initiated by the client user.
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void appStarted();
        void requestSignUp(String firstLastName, String username, String email, String password, String passwordRepeat);
        void requestLogIn(String emailOrUsername, String password);
    }

    //All methods starting with "response" are methods in response to client requests.
    interface PresenterModel
    {
        void responseSignUpSuccess();
        void responseSignUpEmailNotAvailable();
        void responseSignUpWeakPassword();
        void responseSignUpUnknownError();
        void responseSignUpVerificationEmailSent();
        void responseSignUpVerificationEmailNotSent();
        void responseSignUpEmptyName();
        void responseSignUpEmptyEmailOrPassword();
        void responseSignUpInvalidEmail();
        void responseSignUpUsernameNotAvailable();
        void responseLogInSuccess();
        void responseLogInEmailNotVerified();
        void responseLogInFailure();
    }

    //All methods starting with "response" are methods in response to client requests.
    interface View
    {
        void responseSignUpPasswordsDoNotMatch();
        void responseSignUpSuccess();
        void responseSignUpEmailNotAvailable();
        void responseSignUpWeakPassword();
        void responseSignUpUnknownError();
        void responseSignUpVerificationEmailSent();
        void responseSignUpVerificationEmailNotSent();
        void responseSignUpEmptyName();
        void responseSignUpEmptyEmailOrPassword();
        void responseSignUpInvalidEmail();
        void responseSignUpInvalidFirstLastName();
        void responseSignUpInvalidUsername();
        void responseSignUpEmptyEmail();
        void responseSignUpUsernameNotAvailable();
        void responseLogInSuccess();
        void responseLogInEmailNotVerified();
        void responseLogInFailure();
    }

    //All methods starting with "request" are methods which can be initiated by the client user.
    interface Model
    {
        void setPresenter(PresenterModel presenter);
        void requestSignUp(String firstLastName, String username, String email, String password);
        void requestLogIn(String emailOrUsername, String password);
        boolean isUserSignedIn();
    }
}