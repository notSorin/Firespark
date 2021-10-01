package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
                _presenter.userCreatedSuccessfully();
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
}