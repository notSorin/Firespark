package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lesorin.firespark.R;
import com.lesorin.firespark.model.MainActivityModel;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.presenter.MainActivityPresenter;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.fragments.HomeFragment;
import com.lesorin.firespark.view.fragments.PopularFragment;
import com.lesorin.firespark.view.fragments.ProfileFragment;
import com.lesorin.firespark.view.fragments.SendSparkFragment;
import com.lesorin.firespark.view.fragments.SparkFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View
{
    private MainActivityContract.PresenterView _presenter;
    private BottomNavigationView _navigationView;
    private ProfileFragment _profileFragment;
    private HomeFragment _homeFragment;
    private PopularFragment _popularFragment;
    private SparkFragment _sparkFragment;
    private FloatingActionButton _createNewSpark;
    private SendSparkFragment _sendSparkFragment;
    private Vibrator _vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        initializeMVP();
        initializeFragments();
        initializeNavigationView();
        initializeCreateNewSpark();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        _presenter.appStarted();
    }

    private void initializeCreateNewSpark()
    {
        _createNewSpark = findViewById(R.id.NewSparkFAB);

        _createNewSpark.setOnClickListener(view ->
        {
            _sendSparkFragment.resetSparkPosition();
            _sendSparkFragment.emptySparkBody();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                    replace(R.id.FragmentContainer, _sendSparkFragment).commit();
        });
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
        _sparkFragment = new SparkFragment();
        _sendSparkFragment = new SendSparkFragment();
    }

    private void initializeNavigationView()
    {
        _navigationView = findViewById(R.id.NavigationView);

        _navigationView.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.ProfilePage:
                    openProfileFragment();
                    requestProfileData(null);
                    break;
                case R.id.HomePage:
                    openHomeFragment();
                    break;
                case R.id.PopularPage:
                    openPopularFragment();
                    break;
            }

            return true;
        });

        _navigationView.setSelectedItemId(R.id.HomePage);
    }

    private void openProfileFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _profileFragment).commit();
    }

    private void openHomeFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _homeFragment).commit();
    }

    private void openPopularFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _popularFragment).commit();
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
    public void displayProfileData(User user)
    {
        _profileFragment.setUser(user);
    }

    @Override
    public void displayHomeData(ArrayList<Spark> sparks)
    {
        _homeFragment.setSparks(sparks);
    }

    @Override
    public void displayPopularData(ArrayList<Spark> sparks)
    {
        _popularFragment.setSparks(sparks);
    }

    @Override
    public void errorSendSparkEmpty()
    {
        Snackbar.make(_navigationView, R.string.SendSparkEmpty, Snackbar.LENGTH_LONG).show();
        _sendSparkFragment.resetSparkPosition();
    }

    @Override
    public void informSendingSpark()
    {
        hideKeyboard();
        _navigationView.setSelectedItemId(R.id.HomePage);
        Snackbar.make(_navigationView, R.string.SendingSpark, Snackbar.LENGTH_LONG).show();
        _vibrator.vibrate(200);
    }

    @Override
    public void errorSendSparkTooLong()
    {
        Snackbar.make(_navigationView, R.string.SendSparkTooLong, Snackbar.LENGTH_LONG).show();
        _sendSparkFragment.resetSparkPosition();
    }

    @Override
    public void errorSendSparkUnknown()
    {
        Snackbar.make(_navigationView, R.string.SendSparkUnknown, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void sparkSentSuccessfully(Spark spark)
    {
        _homeFragment.addSpark(spark);
    }

    @Override
    public void deleteSparkError()
    {
        Snackbar.make(_navigationView, R.string.DeleteSparkError, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void deleteSparkSuccess(Spark spark)
    {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment visibleFragment = fragments.get(0);

        //Need to update some fragments so they don't show the spark anymore.
        _profileFragment.deleteSpark(spark);
        _homeFragment.deleteSpark(spark);
        _popularFragment.deleteSpark(spark);

        //Go to the home fragment if the user wasn't already on one of the main fragments.
        if(visibleFragment != _profileFragment && visibleFragment != _homeFragment && visibleFragment != _popularFragment)
        {
            _navigationView.setSelectedItemId(R.id.HomePage);
        }

        Snackbar.make(_navigationView, R.string.DeleteSparkSuccess, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void addSparkLikeSuccess(Spark spark)
    {
        _profileFragment.sparkLiked(spark);
        _homeFragment.sparkLiked(spark);
        _popularFragment.sparkLiked(spark);
    }

    @Override
    public void addSparkLikeFailure(Spark spark)
    {
        Snackbar.make(_navigationView, R.string.AddSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeSparkLikeSuccess(Spark spark)
    {
        _profileFragment.sparkLikeRemoved(spark);
        _homeFragment.sparkLikeRemoved(spark);
        _popularFragment.sparkLikeRemoved(spark);
    }

    @Override
    public void removeSparkLikeFailure(Spark spark)
    {
        Snackbar.make(_navigationView, R.string.RemoveSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestProfileUserSuccess(User user)
    {
        _profileFragment.setUser(user);
    }

    @Override
    public void requestProfileUserFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestProfileUserFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestProfileSparksSuccess(ArrayList<Spark> sparks)
    {
        _profileFragment.setSparks(sparks);
    }

    @Override
    public void requestProfileSparksFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestProfileSparksFailure, Snackbar.LENGTH_LONG).show();
    }

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(_navigationView.getWindowToken(), 0);
    }

    public void requestProfileData(String userId)
    {
        _presenter.requestProfileData(userId);
    }

    public void requestHomeData()
    {
        _presenter.requestHomeData();
    }

    public void requestPopularData()
    {
        _presenter.requestPopularData();
    }

    public void sparkClicked(Spark spark)
    {
        _presenter.sparkClicked(spark);
    }

    public void sparkLikeClicked(Spark spark)
    {
        _presenter.sparkLikeClicked(spark);
    }

    public void sparkOwnerClicked(Spark spark)
    {
        _presenter.requestProfileData(spark.getOwnerId());
        openProfileFragment();
    }

    public void sendSparkRequested(String sparkBody)
    {
        _presenter.sendSparkRequested(sparkBody);
    }

    public void sparkDeleteClicked(Spark spark)
    {
        MaterialAlertDialogBuilder madb = new MaterialAlertDialogBuilder(this);

        madb.setTitle(getString(R.string.DeleteSparkTitle)).setMessage(getString(R.string.DeleteSparkMessage)).
            setNegativeButton(getString(R.string.Cancel), (dialogInterface, i) ->
            {

            }).setPositiveButton(getString(R.string.Delete), (dialogInterface, i) ->
            {
                _presenter.sparkDeleteClicked(spark);
            }).show();
    }
}