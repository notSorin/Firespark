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

public class ProfileFragment extends FragmentWithSparks
{
    private MaterialButton _logoutButton;
    private TextView _userName, _userFollowing;

    public ProfileFragment()
    {
        _view = null;
        _sparksRVAdapter = new SparksRecycleViewAdapter();
        _rvLayoutManager = new LinearLayoutManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_profile, container, false);

            initializeBackgroundText();
            initializeSwipeRefresh(() -> {
                //todo
            });
            initializeTexts();
            initializeLogoutButton();
            initializeSparksRecycleView();

            //todo probably can't call requestProfileData from here because what if the user requests to view another user's profile before this fragment is created...
            ((MainActivity)getContext()).requestProfileData();
        }

        return _view;
    }

    private void initializeTexts()
    {
        _userName = _view.findViewById(R.id.UserName);
        _userFollowing = _view.findViewById(R.id.UserFollowing);
    }

    private void initializeLogoutButton()
    {
        _logoutButton = _view.findViewById(R.id.LogoutButton);

        _logoutButton.setOnClickListener(view ->
        {
            ((MainActivity)getContext()).logOutButtonPressed();
        });
    }

    public void setUserData(User user)
    {
        //todo split into 2 functions: 1 for user info, another for sparks
        /*_userName.setText(user._name);
        _userFollowing.setText(String.format(getResources().getString(R.string.UserFollowing), user._followers.size(), user._following.size()));

        SparksRecycleViewAdapter srva = new SparksRecycleViewAdapter();
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());

        _userSparks.setLayoutManager(lm);
        _userSparks.setItemAnimator(new DefaultItemAnimator());
        _userSparks.setAdapter(srva);*/
    }
}