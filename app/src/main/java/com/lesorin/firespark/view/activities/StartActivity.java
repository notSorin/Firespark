package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.fragments.AlphaPageTransformer;
import com.lesorin.firespark.view.fragments.LoginFragment;
import com.lesorin.firespark.view.fragments.SignupFragment;
import com.lesorin.firespark.view.fragments.ViewPagerAdapter;
import com.lesorin.firespark.view.fragments.WelcomeFragment;

public class StartActivity extends AppCompatActivity
{
    private ViewPager _viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initializeBackgroundImage();
        initializeViewPager();
    }

    private void initializeBackgroundImage()
    {
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.slowzoom);
        ImageView bgImage = findViewById(R.id.BackgroundImage);

        bgImage.startAnimation(animShake);
    }

    private void initializeViewPager()
    {
        _viewPager = findViewById(R.id.ViewPager);

        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());

        vpa.addFragment(new WelcomeFragment(), null);
        vpa.addFragment(new LoginFragment(), null);
        vpa.addFragment(new SignupFragment(), null);

        _viewPager.setAdapter(vpa);
        _viewPager.setPageTransformer(true, new AlphaPageTransformer());
        _viewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = findViewById(R.id.TabLayout);

        tabLayout.setupWithViewPager(_viewPager, true);
    }

    @Override
    public void onBackPressed()
    {
        if(_viewPager.getCurrentItem() != 0)
        {
            _viewPager.setCurrentItem(0);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void fakeLogInButtonPressed()
    {
        _viewPager.setCurrentItem(1);
    }

    public void fakeSignUpButtonPressed()
    {
        _viewPager.setCurrentItem(2);
    }

    public void logInButtonPressed(String email, String password)
    {
        Snackbar.make(_viewPager, R.string.NotYetImplemented, Snackbar.LENGTH_LONG).show();
    }

    public void signUpButtonPressed(String name, String email, String password, String passwordRepeat)
    {
        Snackbar.make(_viewPager, R.string.NotYetImplemented, Snackbar.LENGTH_LONG).show();
    }
}