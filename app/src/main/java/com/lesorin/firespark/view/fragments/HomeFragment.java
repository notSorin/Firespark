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
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private View _view;
    private RecyclerView _homeSparks;
    private SwipeRefreshLayout _swipeRefresh;
    private TextView _backgroundText;
    private SparksRecycleViewAdapter _sparksRVAdapter;
    private RecyclerView.LayoutManager _rvLayoutManager;

    public HomeFragment()
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
            _view = inflater.inflate(R.layout.fragment_home, container, false);

            initializeBackgroundText();
            initializeSwipeRefresh();
            initializeHomeSparks();
            _swipeRefresh.setRefreshing(true);
            ((MainActivity)getContext()).requestHomeData();
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
            ((MainActivity)getContext()).requestHomeData();
        });
    }

    private void initializeHomeSparks()
    {
        _homeSparks = _view.findViewById(R.id.HomeSparks);

        _homeSparks.setLayoutManager(_rvLayoutManager);
        _homeSparks.setItemAnimator(new DefaultItemAnimator());
        _homeSparks.setAdapter(_sparksRVAdapter);
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

    public void setBackGroundText(String text)
    {
        _backgroundText.setText(text);
    }
}