package com.lesorin.firespark.presenter;

public interface StartActivityContract
{
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void signUpButtonPressed(String name, String email, String password, String passwordRepeat);
        void logInButtonPressed(String email, String password);
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
    }

    interface View
    {
        void errorNameTooShort();
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
    }

    interface Model
    {
        void setPresenter(PresenterModel presenter);
        void createUser(String name, String email, String password);
        void logUserIn(String email, String password);
        String getUserName();
    }
}