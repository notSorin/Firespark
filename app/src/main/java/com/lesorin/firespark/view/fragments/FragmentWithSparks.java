package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Spark;
import java.util.ArrayList;

public abstract class FragmentWithSparks extends Fragment
{
    protected View _view;
    protected RecyclerView _sparks;
    protected TextView _backgroundText;
    protected SwipeRefreshLayout _swipeRefresh;
    protected SparksRecycleViewAdapter _sparksRVAdapter;
    protected RecyclerView.LayoutManager _rvLayoutManager;
    protected ArrayList<Spark> _sparksList;

    public FragmentWithSparks()
    {
        _view = null;
        _sparksRVAdapter = new SparksRecycleViewAdapter();
        _rvLayoutManager = new LinearLayoutManager(getContext());
        _sparksList = null;
    }

    protected void initializeBackgroundText()
    {
        _backgroundText = _view.findViewById(R.id.BackgroundText);
    }

    protected void initializeSwipeRefresh(SwipeRefreshLayout.OnRefreshListener refreshListener)
    {
        _swipeRefresh = _view.findViewById(R.id.SwipeRefresh);

        _swipeRefresh.setOnRefreshListener(refreshListener);
    }

    protected void initializeSparksRecycleView()
    {
        _sparks = _view.findViewById(R.id.SparksRV);

        _sparks.setLayoutManager(_rvLayoutManager);
        _sparks.setItemAnimator(new DefaultItemAnimator());
        _sparks.setAdapter(_sparksRVAdapter);
    }

    public void setSparks(ArrayList<Spark> sparks)
    {
        _sparksList = sparks;

        displaySparks();
    }

    protected void displaySparks()
    {
        if(_sparksList != null)
        {
            if(_sparksRVAdapter != null)
            {
                _sparksRVAdapter.setSparks(_sparksList);
                setBackGroundText(_sparksList.isEmpty() ? getString(R.string.NoDataText) : "");
            }

            if(_swipeRefresh != null)
            {
                _swipeRefresh.setRefreshing(false);
            }
        }
    }

    public void setBackGroundText(String text)
    {
        if(_backgroundText != null)
        {
            _backgroundText.setText(text);
        }
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

    public void sparkLikeRemoved(Spark spark)
    {
        _sparksRVAdapter.sparkLikeRemoved(spark);
    }
}