package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.fragments.HomeFragment;
import com.lesorin.firespark.view.fragments.PopularFragment;
import com.lesorin.firespark.view.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity
{
    private BottomNavigationView _navigationView;
    private ProfileFragment _profileFragment;
    private HomeFragment _homeFragment;
    private PopularFragment _popularFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFragments();
        initializeNavigationView();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, _profileFragment).commit();
                    break;
                case R.id.HomePage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, _homeFragment).commit();
                    break;
                case R.id.PopularPage:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, _popularFragment).commit();
                    break;
            }

            return true;
        });

        _navigationView.setSelectedItemId(R.id.HomePage);
    }
}