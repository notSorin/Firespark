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
import com.lesorin.firespark.presenter.Comment;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.presenter.PresenterFactory;
import com.lesorin.firespark.presenter.MainContract;
import com.lesorin.firespark.view.fragments.FiresparkFragment;
import com.lesorin.firespark.view.fragments.HomeFragment;
import com.lesorin.firespark.view.fragments.PopularFragment;
import com.lesorin.firespark.view.fragments.ProfileFragment;
import com.lesorin.firespark.view.fragments.SearchUserFragment;
import com.lesorin.firespark.view.fragments.SendSparkFragment;
import com.lesorin.firespark.view.fragments.SparkFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private Stack<FiresparkFragment> _fragmentsStack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        _fragmentsStack = new Stack<>();

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
        if(_fragmentsStack.size() <= 1)
        {
            super.onBackPressed();
        }
        else
        {
            _fragmentsStack.pop();
            openFragment(_fragmentsStack.peek());
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
        MainContract.Model model = ModelFactory.getRESTMainModel(this);
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
                    FiresparkFragment ff = getVisibleFragment();

                    if(ff == null || !ff.isProfileFragment())
                    {
                        //Only request the current user's profile if it is not already in the fragments stack.
                        ProfileFragment pf = findCurrentUserProfileFragment();

                        if(pf == null)
                        {
                            _presenter.requestProfileData(null);
                        }
                        else
                        {
                            openFragment(pf);
                        }
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
            else if(_fragmentsStack.peek() != fragment)
            {
                _fragmentsStack.add(fragment);
            }
        }
    }

    public void logOutButtonPressed()
    {
        _presenter.requestLogout();
    }

    @Override
    public void responseLogoutSuccess()
    {
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

    @Override
    public void responseHomeDataSuccess(ArrayList<Spark> sparks)
    {
        _homeFragment.setSparks(sparks);
        openFragment(_homeFragment);
    }

    @Override
    public void responseHomeDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestHomeDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSendSparkSuccess(Spark spark)
    {
        _homeFragment.addSpark(spark);
        _fragmentsStack.clear();
        _fragmentsStack.add(_homeFragment);
    }

    @Override
    public void responseSendSparkFailure()
    {
        Snackbar.make(_navigationView, R.string.SendSparkUnknown, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSendSparkEmpty()
    {
        Snackbar.make(_navigationView, R.string.SendSparkEmpty, Snackbar.LENGTH_LONG).show();
        _sendSparkFragment.resetSparkPosition();
    }

    @Override
    public void responseSendSparkInProgress()
    {
        hideKeyboard();
        openFragment(_homeFragment);
        _vibrator.vibrate(200);
    }

    @Override
    public void responseSendSparkTooLong()
    {
        Snackbar.make(_navigationView, R.string.SendSparkTooLong, Snackbar.LENGTH_LONG).show();
        _sendSparkFragment.resetSparkPosition();
    }

    @Override
    public void responseDeleteSparkSuccess(Spark spark)
    {
        //Need to update some fragments so they don't show the spark anymore.
        for(FiresparkFragment f : _fragmentsStack)
        {
            f.deleteSpark(spark);
        }

        //Go to the home fragment if the user wasn't already on one of the main fragments.
        FiresparkFragment ff = getVisibleFragment();

        if(ff == null || !ff.isMainFragment())
        {
            _fragmentsStack.clear();
            openFragment(_homeFragment);
        }
    }

    @Override
    public void responseDeleteSparkFailure()
    {
        Snackbar.make(_navigationView, R.string.DeleteSparkError, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseLikeSparkSuccess(Spark spark)
    {
        _fragmentsStack.peek().sparkLiked(spark);
    }

    @Override
    public void responseLikeSparkFailure()
    {
        Snackbar.make(_navigationView, R.string.AddSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseUnlikeSparkSuccess(Spark spark)
    {
        _fragmentsStack.peek().sparkLikeRemoved(spark);
    }

    @Override
    public void responseUnlikeSparkFailure()
    {
        Snackbar.make(_navigationView, R.string.RemoveSparkLikeFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseFollowUserSuccess(User user)
    {
        _fragmentsStack.peek().userFollowed();
    }

    @Override
    public void responseFollowUserFailure()
    {
        Snackbar.make(_navigationView, R.string.FollowUserFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseUnfollowUserSuccess(User user)
    {
        _fragmentsStack.peek().userUnfollowed();
    }

    @Override
    public void responseUnfollowUserFailure()
    {
        Snackbar.make(_navigationView, R.string.UnfollowUserFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSearchUserByUsernameSuccess(User user, ArrayList<Spark> sparks)
    {
        requestProfileSuccess(user, sparks);
    }

    @Override
    public void responseSearchUserByUsernameFailure()
    {
        Snackbar.make(_navigationView, R.string.SearchUserNotFound, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseProfileDataSuccess(User user, ArrayList<Spark> sparks)
    {
        requestProfileSuccess(user, sparks);
    }

    private void requestProfileSuccess(User user, ArrayList<Spark> sparks)
    {
        ProfileFragment pf = findProfileFragment(user.getId());

        if(pf == null)
        {
            pf = new ProfileFragment(this);
        }

        pf.setUser(user);
        pf.setSparks(sparks);
        openFragment(pf);
    }

    @Override
    public void responseProfileDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestProfileDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSparkDataSuccess(Spark spark, ArrayList<Comment> comments)
    {
        SparkFragment sf = findSparkFragment(spark);

        if(sf == null)
        {
            sf = new SparkFragment(this);
        }

        sf.setSpark(spark);
        sf.setComments(comments);
        openFragment(sf);
    }

    @Override
    public void responseSparkDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestSparkDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responsePopularDataSuccess(ArrayList<Spark> sparks)
    {
        _popularFragment.setSparks(sparks);
        openFragment(_popularFragment);
    }

    @Override
    public void responsePopularDataFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestPopularDataFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseProfileDataRefreshSuccess(User user, ArrayList<Spark> sparks)
    {
        _fragmentsStack.peek().refreshProfile(user, sparks);
    }

    @Override
    public void responseProfileDataRefreshFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestRefreshProfileFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseHomeDataRefreshSuccess(ArrayList<Spark> sparks)
    {
        _homeFragment.refreshSparks(sparks);
    }

    @Override
    public void responseHomeDataRefreshFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestHomeDataRefreshFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSendCommentSuccess(Comment comment)
    {
        _fragmentsStack.peek().sendCommentSuccess(comment);
    }

    @Override
    public void responseSendCommentFailure()
    {
        Snackbar.make(_navigationView, R.string.RequestSendCommentFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSendCommentEmptyBody()
    {
        Snackbar.make(_navigationView, R.string.RequestSendCommentFailureEmptyBody, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseDeleteCommentSuccess(Comment comment)
    {
        _fragmentsStack.peek().deleteComment(comment);
    }

    @Override
    public void responseDeleteCommentFailure()
    {
        Snackbar.make(_navigationView, R.string.ResponseDeleteCommentFailure, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseNetworkError()
    {
        Snackbar.make(_navigationView, R.string.NetworkError, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void responseSparkDataRefreshSuccess(Spark spark, ArrayList<Comment> comments)
    {
        _fragmentsStack.peek().refreshSparkData(spark, comments);
    }

    @Override
    public void responseSparkDataRefreshFailure()
    {
        Snackbar.make(_navigationView, R.string.ResponseSparkDataRefreshFailure, Snackbar.LENGTH_LONG).show();
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
        SparkFragment sf = findSparkFragment(spark);

        if(sf == null)
        {
            _presenter.requestSparkData(spark);
        }
        else
        {
            openFragment(sf);
        }
    }

    private SparkFragment findSparkFragment(Spark spark)
    {
        SparkFragment sparkFragment = null;

        for(FiresparkFragment ff : _fragmentsStack)
        {
            if(ff.isSparkFragment() && ff.getSpark() == spark)
            {
                sparkFragment = (SparkFragment)ff;
            }
        }

        return sparkFragment;
    }

    public void sparkLikeClicked(Spark spark)
    {
        _presenter.requestLikeDislikeSpark(spark);
    }

    public void sparkOwnerClicked(Spark spark)
    {
        FiresparkFragment ff = findProfileFragment(spark.getUserId());

        //Request user profile only if there isn't already a fragment on the fragments stack with said user.
        if(ff == null)
        {
            _presenter.requestProfileData(spark.getUserId());
        }
        else
        {
            openFragment(ff);
        }
    }

    private ProfileFragment findProfileFragment(String userId)
    {
        ProfileFragment userFragment = null;

        for(FiresparkFragment ff : _fragmentsStack)
        {
            if(ff.isProfileFragment() && ff.getUser().getId().equals(userId))
            {
                userFragment = (ProfileFragment)ff;
            }
        }

        return userFragment;
    }

    private ProfileFragment findCurrentUserProfileFragment()
    {
        ProfileFragment userFragment = null;

        for(FiresparkFragment ff : _fragmentsStack)
        {
            if(ff.isProfileFragment() && ff.getUser().isCurrentUser())
            {
                userFragment = (ProfileFragment)ff;
            }
        }

        return userFragment;
    }

    public void sendSparkClicked(String sparkBody)
    {
        _presenter.requestSendSpark(sparkBody);
    }

    public void sparkDeleteClicked(Spark spark)
    {
        MaterialAlertDialogBuilder madb = new MaterialAlertDialogBuilder(this);

        madb.setTitle(getString(R.string.DeleteSparkTitle)).setMessage(getString(R.string.DeleteSparkMessage)).
            setNegativeButton(getString(R.string.Cancel), (dialogInterface, i) -> {}).
            setPositiveButton(getString(R.string.Delete), (dialogInterface, i) -> _presenter.requestDeleteSpark(spark)).
            show();
    }

    public void userFollowClicked(User user)
    {
        _presenter.requestFollowUnfollowUser(user);
    }

    public void requestSearchUserByUsername(String userName)
    {
        _presenter.requestSearchUserByUsername(userName);
    }

    public void sendComment(Spark spark, String commentBody, Comment replyComment)
    {
        _presenter.requestSendComment(spark, commentBody, replyComment);
    }

    public void commentDeleteClicked(Comment comment)
    {
        MaterialAlertDialogBuilder madb = new MaterialAlertDialogBuilder(this);

        madb.setTitle(getString(R.string.DeleteCommentTitle)).setMessage(getString(R.string.DeleteCommentMessage)).
                setNegativeButton(getString(R.string.Cancel), (dialogInterface, i) -> {}).
                setPositiveButton(getString(R.string.Delete), (dialogInterface, i) -> _presenter.requestDeleteComment(comment)).
                show();
    }

    public void refreshSparkData(Spark spark)
    {
        _presenter.requestSparkDataRefresh(spark);
    }
}