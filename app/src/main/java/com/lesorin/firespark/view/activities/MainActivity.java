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
import com.lesorin.firespark.view.fragments.FiresparkFragment;
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
    private HomeFragment _homeFragment;
    private PopularFragment _popularFragment;
    private SearchUserFragment _searchUserFragment;
    private FloatingActionButton _mainFab, _newSparkFab, _searchFab;
    private SendSparkFragment _sendSparkFragment;
    private Vibrator _vibrator;
    private Animation _fabOpen, _fabClose, _fabFromBottom, _fabToBottom;
    private ArrayList<FiresparkFragment> _fragmentsStack; //todo make a class for this stack, and take care to not keep too many fragments

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        _fragmentsStack = new ArrayList<>();

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

        _searchFab.setOnClickListener(view -> openFragment(_searchUserFragment));
    }

    private void initializeNewSparkFab()
    {
        _newSparkFab = findViewById(R.id.NewSparkFAB);

        _newSparkFab.setOnClickListener(view -> openFragment(_sendSparkFragment));
    }

    private FiresparkFragment getVisibleFragment()
    {
        FiresparkFragment ret = null;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if(!fragments.isEmpty())
        {
            ret = (FiresparkFragment)fragments.get(0);
        }

        return ret;
    }

    @Override
    public void onBackPressed()
    {
        if(_fragmentsStack.size() == 1)
        {
            super.onBackPressed();
        }
        else
        {
            _fragmentsStack.remove(_fragmentsStack.size() - 1);
            openFragment(_fragmentsStack.get(_fragmentsStack.size() - 1));
        }
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
        _homeFragment = new HomeFragment(this);
        _popularFragment = new PopularFragment(this);
        _sendSparkFragment = new SendSparkFragment(this);
        _searchUserFragment = new SearchUserFragment(this);
    }

    private void initializeNavigationView()
    {
        _navigationView = findViewById(R.id.NavigationView);

        _navigationView.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.ProfilePage:
                    if(!getVisibleFragment().isProfileFragment())
                    {
                        _presenter.requestProfileData(null);
                    }
                    break;
                case R.id.HomePage:
                    if(_homeFragment.getSparksList() == null)
                    {
                        _presenter.requestHomeData();
                    }
                    else
                    {
                        openFragment(_homeFragment);
                    }
                    break;
                case R.id.PopularPage:
                    if(_popularFragment.getSparksList() == null)
                    {
                        _presenter.requestPopularData();
                    }
                    else
                    {
                        openFragment(_popularFragment);
                    }
                    break;
            }

            return true;
        });

        _navigationView.getMenu().getItem(1).setChecked(true);
    }

    private void openFragment(FiresparkFragment fragment)
    {
        if(fragment != null && getVisibleFragment() != fragment)
        {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                replace(R.id.FragmentContainer, fragment).commitNow();

            fragment.displayElements();

            if(fragment.isProfileFragment())
            {
                _navigationView.getMenu().getItem(0).setChecked(true);
                _navigationView.setVisibility(View.VISIBLE);
            }
            else if(fragment.isHomeFragment())
            {
                _navigationView.getMenu().getItem(1).setChecked(true);
                _navigationView.setVisibility(View.VISIBLE);
            }
            else if(fragment.isPopularFragment())
            {
                _navigationView.getMenu().getItem(2).setChecked(true);
                _navigationView.setVisibility(View.VISIBLE);
            }
            else
            {
                _navigationView.setVisibility(View.GONE);
            }

            if(_fragmentsStack.isEmpty())
            {
                _fragmentsStack.add(fragment);
            }
            else if(_fragmentsStack.get(_fragmentsStack.size() - 1) != fragment)
            {
                _fragmentsStack.add(fragment);
            }
        }
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
    public void requestHomeDataSuccess(ArrayList<Spark> sparks)
    {
        _homeFragment.setSparks(sparks);
        openFragment(_homeFragment);
    }

    @Override
    public void requestHomeDataRefreshFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestHomeDataRefreshFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestHomeDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestHomeDataFailure, Snackbar.LENGTH_LONG).show();
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
        openFragment(_homeFragment);
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
        _fragmentsStack.clear();
        _fragmentsStack.add(_homeFragment);
    }

    @Override
    public void deleteSparkError()
    {
        Snackbar.make(_navigationView, R.string.DeleteSparkError, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void deleteSparkSuccess(Spark spark)
    {
        //Need to update some fragments so they don't show the spark anymore.
        for(FiresparkFragment f : _fragmentsStack)
        {
            f.deleteSpark(spark);
        }

        //Go to the home fragment if the user wasn't already on one of the main fragments.
        if(!getVisibleFragment().isMainFragment())
        {
            _fragmentsStack.clear();
            openFragment(_homeFragment);
        }
    }

    @Override
    public void addSparkLikeSuccess(Spark spark)
    {
        _fragmentsStack.get(_fragmentsStack.size() - 1).sparkLiked(spark);
    }

    @Override
    public void addSparkLikeFailure(Spark spark)
    {
        Snackbar.make(_navigationView, R.string.AddSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeSparkLikeSuccess(Spark spark)
    {
        _fragmentsStack.get(_fragmentsStack.size() - 1).sparkLikeRemoved(spark);
    }

    @Override
    public void removeSparkLikeFailure(Spark spark)
    {
        Snackbar.make(_navigationView, R.string.RemoveSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void followUserSuccess(User user)
    {
        _fragmentsStack.get(_fragmentsStack.size() - 1).userFollowed();
    }

    @Override
    public void followUserFailure()
    {
        Snackbar.make(_navigationView, R.string.FollowUserFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void unfollowUserSuccess(User user)
    {
        _fragmentsStack.get(_fragmentsStack.size() - 1).userUnfollowed();
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
        requestProfileSuccess(user, sparks);
    }

    @Override
    public void requestProfileDataSuccess(User user, ArrayList<Spark> sparks)
    {
        requestProfileSuccess(user, sparks);
    }

    private void requestProfileSuccess(User user, ArrayList<Spark> sparks)
    {
        ProfileFragment pf = new ProfileFragment(this);

        pf.setUser(user);
        pf.setSparks(sparks);
        openFragment(pf);
    }

    @Override
    public void requestProfileDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestProfileDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void requestSparkDataSuccess(Spark spark, ArrayList<Comment> comments)
    {
        SparkFragment sf = new SparkFragment(this);

        sf.setSpark(spark);
        sf.setComments(comments);
        openFragment(sf);
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
        _popularFragment.setSparks(sparks);
        openFragment(_popularFragment);
    }

    @Override
    public void requestProfileDataRefreshSuccess(User user, ArrayList<Spark> sparks)
    {
        _fragmentsStack.get(_fragmentsStack.size() - 1).refreshProfile(user, sparks);
    }

    @Override
    public void requestHomeDataRefreshSuccess(ArrayList<Spark> sparks)
    {
        _homeFragment.refreshSparks(sparks);
    }

    @Override
    public void requestProfileDataRefreshFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestRefreshProfileFailure, Snackbar.LENGTH_LONG).show();
    }

    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(_navigationView.getWindowToken(), 0);
    }

    public void refreshProfileData(User user)
    {
        _presenter.requestProfileDataRefresh(user);
    }

    public void refreshHomeData()
    {
        _presenter.requestHomeDataRefresh();
    }

    public void refreshPopularData()
    {
        //todo
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
        User currentProfileUser = getVisibleFragment().getUser();

        //Request user profile only if there isn't already a user on the profile, or if
        //the one in the profile is different from the one being requested.
        if(currentProfileUser == null)
        {
            _presenter.requestProfileData(spark.getOwnerId());
        }
        else if(!currentProfileUser.getId().equals(spark.getOwnerId()))
        {
            _presenter.requestProfileData(spark.getOwnerId());
        }
    }

    public void sendSparkRequested(String sparkBody)
    {
        _presenter.sendSparkRequested(sparkBody);
    }

    public void sparkDeleteClicked(Spark spark)
    {
        MaterialAlertDialogBuilder madb = new MaterialAlertDialogBuilder(this);

        madb.setTitle(getString(R.string.DeleteSparkTitle)).setMessage(getString(R.string.DeleteSparkMessage)).
            setNegativeButton(getString(R.string.Cancel), (dialogInterface, i) -> {}).
            setPositiveButton(getString(R.string.Delete), (dialogInterface, i) -> _presenter.sparkDeleteClicked(spark)).
            show();
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