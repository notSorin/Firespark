package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.fragments.LoginFragment;
import com.lesorin.firespark.view.fragments.RegisterFragment;
import com.lesorin.firespark.view.fragments.ViewPagerAdapter;
import com.lesorin.firespark.view.fragments.WelcomeFragment;

public class StartActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initializeViewPager();
    }

    private void initializeViewPager()
    {
        ViewPager vp = findViewById(R.id.ViewPager);
        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());

        vpa.addFragment(new WelcomeFragment(), null);
        vpa.addFragment(new LoginFragment(), null);
        vpa.addFragment(new RegisterFragment(), null);

        vp.setAdapter(vpa);
    }
}