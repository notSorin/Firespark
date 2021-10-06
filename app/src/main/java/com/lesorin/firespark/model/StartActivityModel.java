package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.lesorin.firespark.presenter.StartActivityContract;

public class StartActivityModel implements StartActivityContract.Model
{
    private StartActivityContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;

    public StartActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
    }

    public void setPresenter(StartActivityContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void createUser(String firstLastName, String username, String email, String password)
    {
        if(!firstLastName.isEmpty())
        {
            if(!email.isEmpty() && !password.isEmpty())
            {
                _firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
                {
                    if(task.isSuccessful())
                    {
                        setUserName(firstLastName);
                        sendVerificationEmail();
                    }
                    else
                    {
                        handleCreateUserException(task.getException());
                    }
                });
            }
            else
            {
                _presenter.failedToCreateUserEmptyEmailOrPassword();
            }
        }
        else
        {
            _presenter.failedToCreateUserEmptyName();
        }
    }

    private void setUserName(String name)
    {
        if(name != null && !name.isEmpty())
        {
            FirebaseUser user = _firebaseAuth.getCurrentUser();

            if(user != null)
            {
                UserProfileChangeRequest.Builder upcrb = new UserProfileChangeRequest.Builder();

                upcrb.setDisplayName(name);

                UserProfileChangeRequest upcr = upcrb.build();

                user.updateProfile(upcr);
            }
        }
    }

    private void handleCreateUserException(Exception exception)
    {
        try
        {
            throw exception;
        }
        catch(FirebaseAuthUserCollisionException e)
        {
            _presenter.failedToCreateUserAlreadyExists();
        }
        catch (FirebaseAuthWeakPasswordException wpe)
        {
            _presenter.failedToCreateUserWeakPassword();
        }
        catch(FirebaseAuthInvalidCredentialsException ice)
        {
            _presenter.failedToCreateUserInvalidEmail();
        }
        catch(Exception e)
        {
            _presenter.failedToCreateUserUnknownError();
        }
    }

    @Override
    public void logUserIn(String email, String password)
    {
        if(!email.isEmpty() && !password.isEmpty())
        {
            _firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    FirebaseUser user = _firebaseAuth.getCurrentUser();

                    if(user != null)
                    {
                        if(user.isEmailVerified())
                        {
                            _presenter.logUserInSuccess();
                        }
                        else
                        {
                            _presenter.logUserInFailureNotVerified();
                        }
                    }
                }
                else
                {
                    _presenter.logUserInFailure();
                }
            });
        }
        else
        {
            _presenter.logUserInFailure();
        }
    }

    @Override
    public boolean isUserSignedIn()
    {
        return _firebaseAuth.getCurrentUser() != null;
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = _firebaseAuth.getCurrentUser();

        if(user != null)
        {
            user.sendEmailVerification().addOnCompleteListener(task ->
            {
                if(task.isSuccessful())
                {
                    _presenter.createUserVerificationEmailSent();
                }
                else
                {
                    _presenter.createUserVerificationEmailNotSent();
                }
            });
        }
        else
        {
            _presenter.createUserVerificationEmailNotSent();
        }
    }
}