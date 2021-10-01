package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
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
    private StartActivityContract.PresenterView _presenter;
    private WelcomeFragment _welcomeFragment;
    private LoginFragment _loginFragment;
    private SignupFragment _signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FirebaseApp.initializeApp(this);

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
        StartActivityContract.PresenterView presenterView = new StartActivityPresenter();
        StartActivityContract.Model model = new StartActivityModel();
        StartActivityContract.PresenterModel presenterModel = (StartActivityContract.PresenterModel)presenterView;

        presenterView.setView(this);
        presenterView.setModel(model);
        model.setPresenter(presenterModel);

        _presenter = presenterView;
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

        _welcomeFragment = new WelcomeFragment();
        _loginFragment = new LoginFragment();
        _signUpFragment = new SignupFragment();

        vpa.addFragment(_welcomeFragment, null);
        vpa.addFragment(_loginFragment, null);
        vpa.addFragment(_signUpFragment, null);

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
        _loginFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.PleaseWait, Snackbar.LENGTH_LONG).show();
        _presenter.logInButtonPressed(email, password);
    }

    public void signUpButtonPressed(String name, String email, String password, String passwordRepeat)
    {
        _signUpFragment.setElementsState(false);
        Snackbar.make(_viewPager, R.string.PleaseWait, Snackbar.LENGTH_LONG).show();
        _presenter.signUpButtonPressed(name, email, password, passwordRepeat);
    }

    @Override
    public void errorNameTooShort()
    {
        _signUpFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.NameTooShort, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorPasswordsDoNotMatch()
    {
        _signUpFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.PasswordsDoNotMatch, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void userCreatedSuccessfully()
    {
        Snackbar.make(_viewPager, R.string.UserCreateSuccess, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorCreateUserAlreadyExists()
    {
        _signUpFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.CreateUserAlreadyExists, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorCreateUserWeakPassword()
    {
        _signUpFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.CreateUserWeakPassword, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorCreateUserUnknownError()
    {
        _signUpFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.CreateUserUnknownError, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyVerificationEmailSent()
    {
        Snackbar.make(_viewPager, R.string.CreateUserVerificationEmailSent, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyVerificationEmailNotSent()
    {
        Snackbar.make(_viewPager, R.string.CreateUserVerificationEmailNotSent, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openLogInView()
    {
        _viewPager.setCurrentItem(1);
    }

    @Override
    public void errorUserNotVerified()
    {
        _loginFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.UserNotVerified, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void errorCannotLogIn()
    {
        _loginFragment.setElementsState(true);
        Snackbar.make(_viewPager, R.string.CannotLogIn, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openMainActivity()
    {

    }
}