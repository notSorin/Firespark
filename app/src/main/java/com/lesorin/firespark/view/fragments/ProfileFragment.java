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
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ProfileFragment extends FragmentWithSparks
{
    private final String JOINED_FORMAT = "d MMM yyyy";
    private MaterialButton _logoutButton, _followButton;
    private TextView _firstLastName, _username, _userFollowing, _userJoined;
    private User _user;
    private SimpleDateFormat _dateFormat;

    public ProfileFragment()
    {
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

            initializeBackgroundText();
            initializeSwipeRefresh(() ->
            {
                _swipeRefresh.setRefreshing(true);
                ((MainActivity)getContext()).requestProfileData(_user == null ? null : _user.getId());
            });
            initializeTexts();
            initializeLogoutButton();
            initializeFollowButton();
            initializeSparksRecycleView();
            displayUserInfo();
            displaySparks();
        }

        return _view;
    }

    private void initializeFollowButton()
    {
        _followButton = _view.findViewById(R.id.FollowButton);

        _followButton.setOnClickListener(view ->
        {
            //todo
        });
    }

    private void displayUserInfo()
    {
        if(_user != null)
        {
            if(_firstLastName != null && _username != null && _userFollowing != null && _userJoined != null &&
                _followButton != null && _logoutButton != null)
            {
                _firstLastName.setText(_user.getFirstlastname());
                _username.setText("@" + _user.getUsername());
                _userFollowing.setText(String.format(getResources().getString(R.string.UserFollowing),
                    _user.getFollowers().size(), _user.getFollowing().size()));
                _userJoined.setText(String.format(getResources().getString(R.string.UserJoined),
                        _dateFormat.format(_user.getJoined().toDate())));
                _followButton.setVisibility(_user.isCurrentUser() ? View.GONE : View.VISIBLE);
                _followButton.setText(_user.isFollowedByCurrentUser() ? R.string.Unfollow : R.string.Follow);
                _logoutButton.setVisibility(_user.isCurrentUser() ? View.VISIBLE : View.GONE);
            }
        }
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

        _logoutButton.setOnClickListener(view ->
        {
            ((MainActivity)getContext()).logOutButtonPressed();
        });
    }

    public void setUser(User user)
    {
        _user = user;

        displayUserInfo();
    }
}