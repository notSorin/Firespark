package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.lesorin.firespark.presenter.StartActivityContract;

public class StartActivityModel implements StartActivityContract.Model
{
    private StartActivityContract.Presenter _presenter;
    private FirebaseAuth _firebaseAuth;

    public StartActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
    }

    public void setPresenter(StartActivityContract.Presenter presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void createUser(String name, String email, String password)
    {
        _firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            //todo
        });
    }
}