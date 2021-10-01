package com.lesorin.firespark.presenter;

public interface StartActivityContract
{
    interface PresenterView
    {
        void setView(View view);
        void setModel(Model model);
        void signUpButtonPressed(String name, String email, String password, String passwordRepeat);
    }

    interface PresenterModel
    {
        void userCreatedSuccessfully();
        void failedToCreateUserAlreadyExists();
        void failedToCreateUserWeakPassword();
        void failedToCreateUserUnknownError();
        void createUserVerificationEmailSent();
        void createUserVerificationEmailNotSent();
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
    }

    interface Model
    {
        void setPresenter(PresenterModel presenter);
        void createUser(String name, String email, String password);
    }
}