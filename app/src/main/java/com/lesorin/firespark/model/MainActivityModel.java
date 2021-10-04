package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lesorin.firespark.presenter.MainActivityContract;
import java.util.ArrayList;

public class MainActivityModel implements MainActivityContract.Model
{
    private MainActivityContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;
    private FirebaseFirestore _firestore;

    public MainActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
    }

    public void setPresenter(MainActivityContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void logUserOut()
    {
        _firebaseAuth.signOut();
    }

    @Override
    public void requestProfileData()
    {
        //todo get real data
    }

    @Override
    public void requestHomeData()
    {
        //todo get real data
    }

    @Override
    public void requestPopularData()
    {
        //todo get real data
    }
}