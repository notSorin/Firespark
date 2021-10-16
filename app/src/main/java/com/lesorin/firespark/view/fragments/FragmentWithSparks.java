package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.pojo.Spark;
import java.util.ArrayList;

public abstract class FragmentWithSparks extends FiresparkFragmentAdapter
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
    }

    public void displayElements()
    {
        if(getContext() != null && _sparksList != null)
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

    @Override
    public void addSpark(Spark spark)
    {
        _sparksRVAdapter.addSpark(spark);
    }

    @Override
    public void deleteSpark(Spark spark)
    {
        _sparksRVAdapter.deleteSpark(spark);
    }

    @Override
    public void sparkLiked(Spark spark)
    {
        _sparksRVAdapter.sparkLiked(spark);
    }

    @Override
    public void sparkLikeRemoved(Spark spark)
    {
        _sparksRVAdapter.sparkLikeRemoved(spark);
    }

    public ArrayList<Spark> getSparksList()
    {
        return _sparksList;
    }
}