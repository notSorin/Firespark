package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.model.MainActivityModel;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.presenter.MainActivityPresenter;
import com.lesorin.firespark.view.fragments.HomeFragment;
import com.lesorin.firespark.view.fragments.PopularFragment;
import com.lesorin.firespark.view.fragments.ProfileFragment;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View
{
    private MainActivityContract.PresenterView _presenter;
    private BottomNavigationView _navigationView;
    private ProfileFragment _profileFragment;
    private HomeFragment _homeFragment;
    private PopularFragment _popularFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMVP();
        initializeFragments();
        initializeNavigationView();
    }

    private void initializeMVP()
    {
        //Although the view in MVP should not have any knowledge about the concrete implementation of the
        //Presenter and Model, for Android apps this is a spacial case because the entry point of the app
        //is a View, therefore it is acceptable for the view to directly access a concrete presenter
        //and model to correctly instantiate the presenter.
        MainActivityContract.PresenterView presenterView = new MainActivityPresenter();
        MainActivityContract.Model model = new MainActivityModel();
        MainActivityContract.PresenterModel presenterModel = (MainActivityContract.PresenterModel)presenterView;

        presenterView.setView(this);
        presenterView.setModel(model);
        model.setPresenter(presenterModel);

        _presenter = presenterView;
    }

    private void initializeFragments()
    {
        _profileFragment = new ProfileFragment();
        _homeFragment = new HomeFragment();
        _popularFragment = new PopularFragment();
    }

    private void initializeNavigationView()
    {
        _navigationView = findViewById(R.id.NavigationView);

        _navigationView.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.ProfilePage:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                            replace(R.id.FragmentContainer, _profileFragment).commit();
                    break;
                case R.id.HomePage:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                            replace(R.id.FragmentContainer, _homeFragment).commit();
                    break;
                case R.id.PopularPage:
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                            replace(R.id.FragmentContainer, _popularFragment).commit();
                    break;
            }

            return true;
        });

        _navigationView.setSelectedItemId(R.id.HomePage);
    }

    public void logOutButtonPressed()
    {
        _presenter.logOutButtonPressed();
    }

    @Override
    public void openStartActivity()
    {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

    @Override
    public void displayProfileData(MainActivityContract.User user)
    {
        _profileFragment.setUserData(user);
    }

    @Override
    public void displayHomeData(ArrayList<MainActivityContract.Spark> sparks)
    {
        _homeFragment.setSparks(sparks);
    }

    @Override
    public void displayPopularData(ArrayList<MainActivityContract.Spark> sparks)
    {
        _popularFragment.setSparks(sparks);
    }

    public void requestProfileData()
    {
        _presenter.requestProfileData();
    }

    public void requestHomeData()
    {
        _presenter.requestHomeData();
    }

    public void requestPopularData()
    {
        _presenter.requestPopularData();
    }
}