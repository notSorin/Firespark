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

        for(int i = 0; i < 30; i++)
        {
            user._sparks.add(new MainActivityContract.Spark());
        }

        _presenter.profileDataAcquired(user);
    }

    @Override
    public void requestHomeData()
    {
        ArrayList<MainActivityContract.Spark> sparks = new ArrayList<>();

        for(int i = 0; i < 30; i++)
        {
            sparks.add(new MainActivityContract.Spark());
        }

        _presenter.homeDataAcquired(sparks);
    }
}