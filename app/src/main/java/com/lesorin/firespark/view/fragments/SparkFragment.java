package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Comment;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

public class SparkFragment extends Fragment
{
    private View _view;
    private RecyclerView _comments;
    private SwipeRefreshLayout _swipeRefresh;

    public SparkFragment()
    {
        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_spark, container, false);

            initializeSwipeRefresh();
            initializeHomeSparks();
            ((MainActivity)getContext()).requestHomeData();
        }

        return _view;
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

    private void initializeHomeSparks()
    {
        _comments = _view.findViewById(R.id.SparkComments);
    }

    public void setComments(ArrayList<Comment> comments)
    {
        CommentsRecycleViewAdapter srva = new CommentsRecycleViewAdapter(comments);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());

        _comments.setLayoutManager(lm);
        _comments.setItemAnimator(new DefaultItemAnimator());
        _comments.setAdapter(srva);
    }
}