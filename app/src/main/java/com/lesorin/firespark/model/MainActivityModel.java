package com.lesorin.firespark.model;

import com.google.firebase.auth.FirebaseAuth;
import com.lesorin.firespark.presenter.MainActivityContract;
import java.util.ArrayList;

public class MainActivityModel implements MainActivityContract.Model
{
    private MainActivityContract.PresenterModel _presenter;
    private FirebaseAuth _firebaseAuth;

    public MainActivityModel()
    {
        _firebaseAuth = FirebaseAuth.getInstance();
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
        MainActivityContract.User user = new MainActivityContract.User();

        user._name = _firebaseAuth.getCurrentUser().getDisplayName();
        user._followers = new ArrayList<>();
        user._following = new ArrayList<>();

        _presenter.profileDataAcquired(user);
    }
}