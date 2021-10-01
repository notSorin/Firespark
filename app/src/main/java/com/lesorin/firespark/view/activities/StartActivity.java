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
import com.lesorin.firespark.model.StartActivityModel;
import com.lesorin.firespark.presenter.StartActivityContract;
import com.lesorin.firespark.presenter.StartActivityPresenter;
import com.lesorin.firespark.view.fragments.AlphaPageTransformer;
import com.lesorin.firespark.view.fragments.LoginFragment;
import com.lesorin.firespark.view.fragments.SignupFragment;
import com.lesorin.firespark.view.fragments.ViewPagerAdapter;
import com.lesorin.firespark.view.fragments.WelcomeFragment;

public class StartActivity extends AppCompatActivity implements StartActivityContract.View
{
    private ViewPager _viewPager;
    private StartActivityContract.Presenter _presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initializeMVP();
        initializeBackgroundImage();
        initializeViewPager();
    }

    private void initializeMVP()
    {
        //Although the view in MVP should not have any knowledge about the concrete implementation of the
        //Presenter and Model, for Android apps this is a spacial case because the entry point of the app
        //is a View, therefore it is acceptable for the view to directly access a concrete presenter
        //and model to correctly instantiate the presenter.
        StartActivityContract.Presenter presenter = new StartActivityPresenter();
        StartActivityContract.Model model = new StartActivityModel();

        presenter.setView(this);
        presenter.setModel(model);
        model.setPresenter(presenter);

        _presenter = presenter;
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
        _presenter.signUpButtonPressed(name, email, password, passwordRepeat);
    }
}