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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.button.MaterialButton;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

public class ProfileFragment extends Fragment
{
    private View _view;
    private MaterialButton _logoutButton;
    private TextView _userName, _userFollowing, _backgroundText;
    private RecyclerView _userSparks;
    private SwipeRefreshLayout _swipeRefresh;
    private SparksRecycleViewAdapter _sparksRVAdapter;
    private RecyclerView.LayoutManager _rvLayoutManager;

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
            initializeSwipeRefresh();
            initializeTexts();
            initializeLogoutButton();
            initializeSparksRecycleView();

            //todo probably can't call requestProfileData from here because what if the user requests to view another user's profile before this fragment is created...
            ((MainActivity)getContext()).requestProfileData();
        }

        return _view;
    }

    private void initializeBackgroundText()
    {
        _backgroundText = _view.findViewById(R.id.BackgroundText);
    }

    private void initializeSwipeRefresh()
    {
        _swipeRefresh = _view.findViewById(R.id.SwipeRefresh);

        _swipeRefresh.setOnRefreshListener(() ->
        {
            //todo
            _swipeRefresh.setRefreshing(false);
        });
    }

    private void initializeSparksRecycleView()
    {
        _userSparks = _view.findViewById(R.id.UserSparks);

        _userSparks.setLayoutManager(_rvLayoutManager);
        _userSparks.setItemAnimator(new DefaultItemAnimator());
        _userSparks.setAdapter(_sparksRVAdapter);
    }

    public void setSparks(ArrayList<Spark> sparks)
    {
        _sparksRVAdapter.setSparks(sparks);

        if(!sparks.isEmpty())
        {
            _backgroundText.setText("");
        }
        else
        {
            _backgroundText.setText(R.string.NoDataText);
        }

        _swipeRefresh.setRefreshing(false);
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

    public void addSpark(Spark spark)
    {
        _sparksRVAdapter.addSpark(spark);
    }

    public void deleteSpark(Spark spark)
    {
        _sparksRVAdapter.deleteSpark(spark);
    }

    public void sparkLiked(Spark spark)
    {
        _sparksRVAdapter.sparkLiked(spark);
    }
}