package com.lesorin.firespark.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lesorin.firespark.R;
import com.lesorin.firespark.model.ModelFactory;
import com.lesorin.firespark.presenter.PresenterFactory;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.presenter.pojo.User;
import com.lesorin.firespark.view.fragments.HomeFragment;
import com.lesorin.firespark.view.fragments.PopularFragment;
import com.lesorin.firespark.view.fragments.ProfileFragment;
import com.lesorin.firespark.view.fragments.SearchUserFragment;
import com.lesorin.firespark.view.fragments.SendSparkFragment;
import com.lesorin.firespark.view.fragments.SparkFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View
{
    private MainContract.PresenterView _presenter;
    private BottomNavigationView _navigationView;
    private ProfileFragment _profileFragment;
    private HomeFragment _homeFragment;
    private PopularFragment _popularFragment;
    private SparkFragment _sparkFragment;
    private SearchUserFragment _searchUserFragment;
    private FloatingActionButton _mainFab, _newSparkFab, _searchFab;
    private SendSparkFragment _sendSparkFragment;
    private Vibrator _vibrator;
    private Animation _fabOpen, _fabClose, _fabFromBottom, _fabToBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        initializeMVP();
        initializeFragments();
        initializeNavigationView();
        initializeFABS();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        _presenter.appStarted();
    }

    private void initializeFABS()
    {
        initializeFABAnimations();
        initializeMainFAB();
        initializeNewSparkFab();
        initializeSearchFab();
    }

    private void initializeSearchFab()
    {
        _searchFab = findViewById(R.id.SearchFAB);

        _searchFab.setOnClickListener(view ->
        {
            openSearchUserFragment();
        });
    }

    private void initializeNewSparkFab()
    {
        _newSparkFab = findViewById(R.id.NewSparkFAB);

        _newSparkFab.setOnClickListener(view ->
        {
            //Only open the send spark fragment if it's not already open.
            if(getVisibleFragment() != _sendSparkFragment)
            {
                openSendSparkFragment();
            }

            _sendSparkFragment.resetSparkPosition();
            _sendSparkFragment.emptySparkBody();
        });
    }

    private Fragment getVisibleFragment()
    {
        Fragment ret = null;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if(!fragments.isEmpty())
        {
            ret =fragments.get(0);
        }

        return ret;
    }

    private void initializeFABAnimations()
    {
        _fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_open);
        _fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_close);
        _fabFromBottom = AnimationUtils.loadAnimation(this, R.anim.fab_from_bottom);
        _fabToBottom = AnimationUtils.loadAnimation(this, R.anim.fab_to_bottom);
    }

    private void initializeMainFAB()
    {
        _mainFab = findViewById(R.id.MainFAB);

        _mainFab.setOnClickListener(view ->
        {
            if(_searchFab.getVisibility() == View.INVISIBLE)
            {
                _newSparkFab.startAnimation(_fabFromBottom);
                _newSparkFab.setClickable(true);
                _searchFab.startAnimation(_fabFromBottom);
                _searchFab.setClickable(true);
                _mainFab.startAnimation(_fabOpen);
            }
            else
            {
                _newSparkFab.startAnimation(_fabToBottom);
                _newSparkFab.setClickable(false);
                _searchFab.startAnimation(_fabToBottom);
                _searchFab.setClickable(false);
                _mainFab.startAnimation(_fabClose);
            }

            _newSparkFab.setVisibility(_newSparkFab.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
            _searchFab.setVisibility(_searchFab.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void initializeMVP()
    {
        MainContract.PresenterView presenterView = PresenterFactory.getMainPresenter();
        MainContract.Model model = ModelFactory.getMainModel();
        MainContract.PresenterModel presenterModel = (MainContract.PresenterModel)presenterView;

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
        _searchUserFragment = new SearchUserFragment();
    }

    private void initializeNavigationView()
    {
        _navigationView = findViewById(R.id.NavigationView);

        _navigationView.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.ProfilePage:
                    User user = _profileFragment.getUser();

                    if(user == null)
                    {
                        requestProfileData(null);
                    }
                    else if(!user.isCurrentUser())
                    {
                        requestProfileData(null);
                    }
                    else
                    {
                        openProfileFragment();
                    }
                    break;
                case R.id.HomePage:
                    if(_homeFragment.getSparksList() == null)
                    {
                        requestHomeData();
                    }
                    else
                    {
                        openHomeFragment();
                    }
                    break;
                case R.id.PopularPage:
                    if(_homeFragment.getSparksList() == null)
                    {
                        requestPopularData();
                    }
                    else
                    {
                        openPopularFragment();
                    }
                    break;
            }

            return true;
        });

        _navigationView.setSelectedItemId(R.id.HomePage);
    }

    private void openProfileFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _profileFragment).commitNow();
    }

    private void openHomeFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _homeFragment).commitNow();
    }

    private void openPopularFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _popularFragment).commitNow();
    }

    private void openSearchUserFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _searchUserFragment).commitNow();
    }

    private void openSparkFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _sparkFragment).commitNow();
    }

    private void openSendSparkFragment()
    {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, _sendSparkFragment).commitNow();
    }

    public void logOutButtonPressed()
    {
        _presenter.logOutButtonPressed();
    }

    @Override
    public void userLoggedOutSuccessfully()
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
        openHomeFragment();
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
        _profileFragment.addSpark(spark);
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
    }

    @Override
    public void addSparkLikeSuccess(Spark spark)
    {
        _profileFragment.sparkLiked(spark);
        _homeFragment.sparkLiked(spark);
        _popularFragment.sparkLiked(spark);
        _sparkFragment.sparkLiked(spark);
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
        _sparkFragment.sparkLiked(spark);
    }

    @Override
    public void removeSparkLikeFailure(Spark spark)
    {
        Snackbar.make(_navigationView, R.string.RemoveSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void followUserSuccess(User user)
    {
        _profileFragment.setUser(user);
    }

    @Override
    public void followUserFailure()
    {
        Snackbar.make(_navigationView, R.string.FollowUserFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void unfollowUserSuccess(User user)
    {
        _profileFragment.setUser(user);
    }

    @Override
    public void unfollowUserFailure()
    {
        Snackbar.make(_navigationView, R.string.UnfollowUserFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void searchUserByUsernameFailure()
    {
        Snackbar.make(_navigationView, R.string.SearchUserNotFound, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void searchUserByUsernameSuccess(User user, ArrayList<Spark> sparks)
    {
        openProfileFragment();
        _profileFragment.setUser(user);
        _profileFragment.setSparks(sparks);
    }

    @Override
    public void requestProfileDataSuccess(User user, ArrayList<Spark> sparks)
    {
        openProfileFragment();
        _profileFragment.setUser(user);
        _profileFragment.setSparks(sparks);
    }

    @Override
    public void requestProfileDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestProfileDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestSparkDataSuccess(Spark spark, ArrayList<Comment> comments)
    {
        openSparkFragment();
        _sparkFragment.setSpark(spark);
        _sparkFragment.setComments(comments);
    }

    @Override
    public void requestSparkDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestSparkDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestPopularDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestPopularDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestPopularDataSuccess(ArrayList<Spark> sparks)
    {
        openPopularFragment();
        _popularFragment.setSparks(sparks);
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
        _presenter.requestSparkData(spark);
    }

    public void sparkLikeClicked(Spark spark)
    {
        _presenter.sparkLikeClicked(spark);
    }

    public void sparkOwnerClicked(Spark spark)
    {
        _presenter.requestProfileData(spark.getOwnerId());
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

    public void userFollowClicked(User user)
    {
        _presenter.userFollowClicked(user);
    }

    public void requestSearchUserByUsername(String userName)
    {
        _presenter.requestSearchUserByUsername(userName);
    }
}