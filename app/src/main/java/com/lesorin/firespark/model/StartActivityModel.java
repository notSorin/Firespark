package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
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
                _presenter.failedToCreateUser();
            }
        });
    }
}