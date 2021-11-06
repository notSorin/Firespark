package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.button.MaterialButton;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * One of the main fragments of the app, whose purpose is to display a "user's profile".
 */
public class ProfileFragment extends FragmentWithSparks
{
    private final String JOINED_FORMAT = "d MMM yyyy";
    private MaterialButton _followButton;
    private View _logoutButton, _originalIcon;
    private TextView _firstLastName, _username, _userFollowing, _userJoined;
    private User _user;
    private SimpleDateFormat _dateFormat;

    /**
     * Instantiates a new Profile fragment.
     *
     * @param activity Activity to be accessible from the fragment.
     */
    public ProfileFragment(MainActivity activity)
    {
        super(activity);

        _view = null;
        _sparksRVAdapter = new SparksRecycleViewAdapter();
        _rvLayoutManager = new LinearLayoutManager(getContext());
        _user = null;
        _dateFormat = new SimpleDateFormat(JOINED_FORMAT, Locale.getDefault());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_profile, container, false);

            initializeVerifiedIcon();
            initializeBackgroundText();
            initializeSwipeRefresh(() ->
            {
                _swipeRefresh.setRefreshing(true);
                _activity.refreshProfileData(_user);
            });
            initializeTexts();
            initializeLogoutButton();
            initializeFollowButton();
            initializeSparksRecyclerView();
        }

        return _view;
    }

    private void initializeVerifiedIcon()
    {
        _originalIcon = _view.findViewById(R.id.OriginalIcon);
    }

    private void initializeFollowButton()
    {
        _followButton = _view.findViewById(R.id.FollowButton);

        _followButton.setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).userFollowClicked(_user);
        });
    }

    @Override
    public void displayElements()
    {
        super.displayElements();

        if(_user != null)
        {
            updateOriginalIcon();
            updateFirstLastName();
            updateUserName();
            updateFollowing();
            updateJoined();
            updateFollowButton();
            updateLogoutButton();
        }
    }

    private void updateLogoutButton()
    {
        _logoutButton.setVisibility(_user.isCurrentUser() ? View.VISIBLE : View.GONE);
    }

    private void updateUserName()
    {
        _username.setText(String.format(_activity.getString(R.string.UserHandle), _user.getUsername()));
    }

    private void updateFirstLastName()
    {
        _firstLastName.setText(_user.getFirstLastName());
    }

    private void updateOriginalIcon()
    {
        _originalIcon.setVisibility(_user.isOriginal() ? View.VISIBLE : View.GONE);
    }

    private void updateFollowing()
    {
        _userFollowing.setText(String.format(_activity.getString(R.string.UserFollowers), _user.getFollowers().size(), _user.getFollowing().size()));
    }

    private void updateFollowButton()
    {
        _followButton.setVisibility(_user.isCurrentUser() ? View.GONE : View.VISIBLE);
        _followButton.setText(_user.isFollowedByCurrentUser() ? R.string.Unfollow : R.string.Follow);
    }

    private void updateJoined()
    {
        _userJoined.setText(String.format(_activity.getString(R.string.UserJoined), _dateFormat.format(_user.getJoined())));
    }

    private void initializeTexts()
    {
        _firstLastName = _view.findViewById(R.id.FirstLastName);
        _username = _view.findViewById(R.id.Username);
        _userFollowing = _view.findViewById(R.id.UserFollowing);
        _userJoined = _view.findViewById(R.id.UserJoined);
    }

    private void initializeLogoutButton()
    {
        _logoutButton = _view.findViewById(R.id.LogoutButton);

        _logoutButton.setOnClickListener(view -> _activity.logOutButtonPressed());
    }

    @Override
    public void setUser(User user)
    {
        _user = user;
    }

    @Override
    public User getUser()
    {
        return _user;
    }

    @Override
    public boolean isProfileFragment()
    {
        return true;
    }

    @Override
    public boolean isMainFragment()
    {
        return true;
    }

    @Override
    public void userFollowed()
    {
        updateFollowing();
        updateFollowButton();
    }

    @Override
    public void userUnfollowed()
    {
        updateFollowing();
        updateFollowButton();
    }

    @Override
    public void refreshProfile(User user, ArrayList<Spark> sparks)
    {
        setUser(user);
        setSparks(sparks);
        displayElements();
    }
}