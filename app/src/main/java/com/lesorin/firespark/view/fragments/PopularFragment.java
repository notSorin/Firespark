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

public class PopularFragment extends Fragment
{
    private View _view;
    private RecyclerView _popularSparks;
    private SwipeRefreshLayout _swipeRefresh;
    private TextView _backgroundText;
    private SparksRecycleViewAdapter _sparksRVAdapter;
    private RecyclerView.LayoutManager _rvLayoutManager;

    public PopularFragment()
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
            _view = inflater.inflate(R.layout.fragment_popular, container, false);

            initializeBackgroundText();
            initializeSwipeRefresh();
            initializePopularSparks();
            ((MainActivity)getContext()).requestPopularData();
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

    private void initializePopularSparks()
    {
        _popularSparks = _view.findViewById(R.id.PopularSparks);

        _popularSparks.setLayoutManager(_rvLayoutManager);
        _popularSparks.setItemAnimator(new DefaultItemAnimator());
        _popularSparks.setAdapter(_sparksRVAdapter);
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

    public void addSpark(Spark spark)
    {
        _sparksRVAdapter.addSpark(spark);
    }

    public void deleteSpark(Spark spark)
    {
        _sparksRVAdapter.deleteSpark(spark);
    }
}