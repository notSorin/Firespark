package com.lesorin.firespark.presenter;

/**
 * Interface for the Start Contract of the app. It defines the interfaces for the VIEW, PRESENTER, and MODEL.
 */
public interface StartContract
{
    String FIRST_LAST_NAME_REGEX = "^[a-zA-Z0-9 ]{1,30}$";
    String USERNAME_REGEX = "^[a-zA-Z0-9]{1,20}$";

    /**
     * This interface defines the methods required to be implemented by any class which acts as a
     * PRESENTER for a VIEW in the MVP Architectural Pattern.
     * All methods starting with "request" are methods which can be initiated by the client user.
     */
    interface PresenterView
    {
        /**
         * Sets the view.
         *
         * @param view Any @{@link View}.
         */
        void setView(View view);

        /**
         * Sets the model.
         *
         * @param model Any @{@link Model}
         */
        void setModel(Model model);

        /**
         * Called when the app starts to notify the presenter in case anything needs to be done at
         * that time.
         */
        void appStarted();

        /**
         * Called when a user wants to sign up in the network.
         *
         * @param firstLastName  The user's first and last name.
         * @param username       The user's username.
         * @param email          The user's email.
         * @param password       The user's password.
         * @param passwordRepeat The user's password repeated.
         */
        void requestSignUp(String firstLastName, String username, String email, String password, String passwordRepeat);

        /**
         * Called when a user wants to log into the network.
         *
         * @param emailOrUsername The user's email or username.
         * @param password        The user's password.
         */
        void requestLogIn(String emailOrUsername, String password);
    }

    /**
     * This interface defines the methods required to be implemented by any class which acts as a
     * PRESENTER for a MODEL in the MVP Architectural Pattern.
     * All methods starting with "response" are methods in response to client requests.
     */
    interface PresenterModel
    {
        /**
         * Called if the sign up process was successful.
         */
        void responseSignUpSuccess();

        /**
         * Called if the sign up process failed because the email is not available for use.
         */
        void responseSignUpEmailNotAvailable();

        /**
         * Called if the sign up process failed because the user's password is too weak.
         */
        void responseSignUpWeakPassword();

        /**
         * Called if the sign up process failed because of an unknown error.
         */
        void responseSignUpUnknownError();

        /**
         * Called if the sign up process was successful and a verification email was sent to the user's
         * email address.
         */
        void responseSignUpVerificationEmailSent();

        /**
         * Called if the sign up process failed because a verification email could not be sent to
         * the user's email address.
         */
        void responseSignUpVerificationEmailNotSent();

        /**
         * Called if the sign up process failed because the provided first and last name was empty.
         */
        void responseSignUpEmptyName();

        /**
         * Called if the sign up process failed because the provided email or password was empty.
         */
        void responseSignUpEmptyEmailOrPassword();

        /**
         * Called if the sign up process failed because the provided email was not a valid email.
         */
        void responseSignUpInvalidEmail();

        /**
         * Called if the sign up process failed because the provided username is already taken.
         */
        void responseSignUpUsernameNotAvailable();

        /**
         * Called if the log in process was successful.
         */
        void responseLogInSuccess();

        /**
         * Called if the log in process failed because the account's email is not yet verified.
         */
        void responseLogInEmailNotVerified();

        /**
         * Called if the log in process failed because of incorrect credentials, or because of an error.
         */
        void responseLogInFailure();

        /**
         * Called whenever a network error is detected.
         */
        void responseNetworkError();

        /**
         * Called if the sign up process fails.
         *
         * @param message A message containing the cause of the error.
         */
        void responseSignupFailure(String message);
    }

    /**
     * This interface defines the methods required to be implemented by any class which acts as a VIEW
     * in the MVP Architectural Pattern.
     * All methods starting with "response" are methods in response to client requests.
     */
    interface View
    {
        /**
         * Called if the sign up process failed because the provided password and repeated password
         * do not match each other.
         */
        void responseSignUpPasswordsDoNotMatch();

        /**
         * Called if the sign up process is successful.
         */
        void responseSignUpSuccess();

        /**
         * Called if the sign up process failed because the email is not available for use.
         */
        void responseSignUpEmailNotAvailable();

        /**
         * Called if the sign up process failed because the user's password is too weak.
         */
        void responseSignUpWeakPassword();

        /**
         * Called if the sign up process failed because of an unknown error.
         */
        void responseSignUpUnknownError();

        /**
         * Called if the sign up process was successful and a verification email was sent to the user's
         * email address.
         */
        void responseSignUpVerificationEmailSent();

        /**
         * Called if the sign up process failed because a verification email could not be sent to
         * the user's email address.
         */
        void responseSignUpVerificationEmailNotSent();

        /**
         * Called if the sign up process failed because the provided first and last name was empty.
         */
        void responseSignUpEmptyName();

        /**
         * Called if the sign up process failed because the provided email or password was empty.
         */
        void responseSignUpEmptyEmailOrPassword();

        /**
         * Called if the sign up process failed because the provided email was not a valid email.
         */
        void responseSignUpInvalidEmail();

        /**
         * Called if the sign up process failed because the provided first and last name is not valid.
         */
        void responseSignUpInvalidFirstLastName();

        /**
         * Called if the sign up process failed because the provided username is not valid.
         */
        void responseSignUpInvalidUsername();

        /**
         * Called if the sign up process failed because the provided email empty.
         */
        void responseSignUpEmptyEmail();

        /**
         * Called if the sign up process failed because the provided username is already taken.
         */
        void responseSignUpUsernameNotAvailable();

        /**
         * Called if the log in process was successful.
         */
        void responseLogInSuccess();

        /**
         * Called if the log in process failed because the account's email is not yet verified.
         */
        void responseLogInEmailNotVerified();

        /**
         * Called if the log in process failed because of incorrect credentials, or because of an error.
         */
        void responseLogInFailure();

        /**
         * Called whenever a network error is detected.
         */
        void responseNetworkError();

        /**
         * Called if the sign up process fails.
         *
         * @param message A message containing the cause of the error.
         */
        void responseSignupFailure(String message);
    }

    /**
     * This interface defines the methods required to be implemented by any class which acts as a MODEL
     * in the MVP Architectural Pattern.
     * All methods starting with "request" are methods which can be initiated by the client user.
     */
    interface Model
    {
        /**
         * Sets the presenter.
         *
         * @param presenter Any @{@link PresenterModel}.
         */
        void setPresenter(PresenterModel presenter);

        /**
         * Called when a user wants to sign up in the network.
         *
         * @param firstLastName  The user's first and last name.
         * @param username       The user's username.
         * @param email          The user's email.
         * @param password       The user's password.
         */
        void requestSignUp(String firstLastName, String username, String email, String password);

        /**
         * Called when a user wants to log into the network.
         *
         * @param emailOrUsername The user's email or username.
         * @param password        The user's password.
         */
        void requestLogIn(String emailOrUsername, String password);

        /**
         *
         * @return True if there is a user in the app's cache, false otherwise.
         */
        boolean isUserSignedIn();
    }
}