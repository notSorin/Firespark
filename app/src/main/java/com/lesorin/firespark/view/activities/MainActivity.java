package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lesorin.firespark.R;

public class MainActivity extends AppCompatActivity
{
    private BottomNavigationView _navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeNavigationView();
    }

    private void initializeNavigationView()
    {
        _navigationView = findViewById(R.id.NavigationView);

        _navigationView.setSelectedItemId(R.id.HomePage);
    }
}