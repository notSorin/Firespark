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
        void failedToCreateUser();
    }

    interface View
    {
        void errorNameTooShort();
        void errorPasswordsDoNotMatch();
        void userCreatedSuccessfully();
        void errorPasswordTooShort();
    }

    interface Model
    {
        void setPresenter(PresenterModel presenter);
        void createUser(String name, String email, String password);
    }
}