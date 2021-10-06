package com.lesorin.firespark.presenter;

public interface StartActivityContract
{
    String FIRST_LAST_NAME_REGEX = "^[a-zA-Z0-9 ]{1,30}$";
    String USERNAME_REGEX = "^[a-zA-Z0-9]{1,20}$";

    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void signUpButtonPressed(String firstLastName, String username, String email, String password, String passwordRepeat);
        void logInButtonPressed(String email, String password);
        void appStarted();
    }

    interface PresenterModel
    {
        void userCreatedSuccessfully();
        void failedToCreateUserAlreadyExists();
        void failedToCreateUserWeakPassword();
        void failedToCreateUserUnknownError();
        void createUserVerificationEmailSent();
        void createUserVerificationEmailNotSent();
        void logUserInSuccess();
        void logUserInFailureNotVerified();
        void logUserInFailure();
        void failedToCreateUserEmptyName();
        void failedToCreateUserEmptyEmailOrPassword();
        void failedToCreateUserInvalidEmail();
    }

    interface View
    {
        void errorPasswordsDoNotMatch();
        void userCreatedSuccessfully();
        void errorCreateUserAlreadyExists();
        void errorCreateUserWeakPassword();
        void errorCreateUserUnknownError();
        void notifyVerificationEmailSent();
        void notifyVerificationEmailNotSent();
        void openLogInView();
        void errorUserNotVerified();
        void errorCannotLogIn();
        void openMainActivity();
        void errorCreateUserEmptyName();
        void errorCreateUserEmptyEmailOrPassword();
        void errorCreateUserInvalidEmail();
        void errorInvalidFirstLastName();
        void errorInvalidUsername();
        void errorEmptyEmail();
    }

    interface Model
    {
        void setPresenter(PresenterModel presenter);
        void createUser(String firstLastName, String username, String email, String password);
        void logUserIn(String email, String password);
        boolean isUserSignedIn();
    }
}