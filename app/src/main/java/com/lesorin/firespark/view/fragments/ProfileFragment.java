package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.view.activities.MainActivity;

public class ProfileFragment extends Fragment
{
    private View _view;
    private MaterialButton _logoutButton;
    private TextView _userName, _userFollowing;
    private RecyclerView _userSparks;

    public ProfileFragment()
    {
        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_profile, container, false);

            initializeTexts();
            initializeLogoutButton();
            initializeSparksRecycleView();
            ((MainActivity)getContext()).requestProfileData();
        }

        return _view;
    }

    private void initializeSparksRecycleView()
    {
        _userSparks = _view.findViewById(R.id.UserSparks);
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

    public void setUserData(MainActivityContract.User user)
    {
        _userName.setText(user._name);
        _userFollowing.setText(String.format(getResources().getString(R.string.UserFollowing), user._followers.size(), user._following.size()));

        SparksRecycleViewAdapter srva = new SparksRecycleViewAdapter(user._sparks);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());

        _userSparks.setLayoutManager(lm);
        _userSparks.setItemAnimator(new DefaultItemAnimator());
        _userSparks.setAdapter(srva);
    }
}