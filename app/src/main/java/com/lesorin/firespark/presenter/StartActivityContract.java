package com.lesorin.firespark.presenter;

public interface StartActivityContract
{
    interface Presenter
    {
        void setView(View view);
        void setModel(Model model);
        void signUpButtonPressed(String name, String email, String password, String passwordRepeat);
        void userCreatedSuccessfully();
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
        void setPresenter(Presenter presenter);
        void createUser(String name, String email, String password);
    }
}