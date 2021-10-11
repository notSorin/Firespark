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

    public FragmentWithSparks()
    {
        _view = null;
        _sparksRVAdapter = new SparksRecycleViewAdapter();
        _rvLayoutManager = new LinearLayoutManager(getContext());
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
        _sparksRVAdapter.setSparks(sparks);
        setBackGroundText(sparks.isEmpty() ? getString(R.string.NoDataText) : "");
        _swipeRefresh.setRefreshing(false);
    }

    public void setBackGroundText(String text)
    {
        _backgroundText.setText(text);
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