package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
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
    public void createUser(String name, String email, String password)
    {
        _firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
        {
            if(task.isSuccessful())
            {
                sendVerificationEmail();
            }
            else
            {
                try
                {
                    throw task.getException();
                }
                catch(FirebaseAuthUserCollisionException e)
                {
                    _presenter.failedToCreateUserAlreadyExists();
                }
                catch (FirebaseAuthWeakPasswordException wpe)
                {
                    _presenter.failedToCreateUserWeakPassword();
                }
                catch(Exception e)
                {
                    _presenter.failedToCreateUserUnknownError();
                }
            }
        });
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
    public String getUserName()
    {
        String name = null;
        FirebaseUser user = _firebaseAuth.getCurrentUser();

        if(user != null)
        {
            name = user.getDisplayName();
        }

        return name;
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