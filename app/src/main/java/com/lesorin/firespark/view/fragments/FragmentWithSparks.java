package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

/**
 * An abstract fragment which contains a list of sparks.
 */
public abstract class FragmentWithSparks extends FiresparkFragmentAdapter
{
    protected View _view;
    protected RecyclerView _sparks;
    protected TextView _backgroundText;
    protected SwipeRefreshLayout _swipeRefresh;
    protected SparksRecycleViewAdapter _sparksRVAdapter;
    protected RecyclerView.LayoutManager _rvLayoutManager;
    protected ArrayList<Spark> _sparksList;

    /**
     * Instantiates a new @{@link FragmentWithSparks}.
     *
     * @param activity {@link MainActivity} to be accessible from all @{@link FiresparkFragment}s.
     */
    public FragmentWithSparks(MainActivity activity)
    {
        super(activity);

        _view = null;
        _sparksRVAdapter = new SparksRecycleViewAdapter();
        _rvLayoutManager = new LinearLayoutManager(getContext());
        _sparksList = null;
    }

    /**
     * Initialize background text.
     */
    protected void initializeBackgroundText()
    {
        _backgroundText = _view.findViewById(R.id.BackgroundText);
    }

    /**
     * Initialize the swipe refresh on the fragment.
     *
     * @param refreshListener Listener to be called when the swipe refresh is triggered.
     */
    protected void initializeSwipeRefresh(SwipeRefreshLayout.OnRefreshListener refreshListener)
    {
        _swipeRefresh = _view.findViewById(R.id.SwipeRefresh);

        _swipeRefresh.setOnRefreshListener(refreshListener);
    }

    /**
     * Initialize the sparks recycler view.
     */
    protected void initializeSparksRecyclerView()
    {
        _sparks = _view.findViewById(R.id.SparksRV);

        _sparks.setLayoutManager(_rvLayoutManager);
        _sparks.setItemAnimator(new DefaultItemAnimator());
        _sparks.setAdapter(_sparksRVAdapter);
    }

    /**
     * Sets sparks on the fragment.
     *
     * @param sparks Sparks to be set on the fragment.
     */
    public void setSparks(ArrayList<Spark> sparks)
    {
        _sparksList = sparks;
    }

    /**
     * Displays the elements available on the fragment.
     */
    public void displayElements()
    {
        if(_sparksList != null)
        {
            if(_sparksRVAdapter != null)
            {
                _sparksRVAdapter.setSparks(_sparksList);
                updateBackgroundText();
            }

            if(_swipeRefresh != null)
            {
                _swipeRefresh.setRefreshing(false);
            }
        }
    }

    private void updateBackgroundText()
    {
        setBackGroundText(_sparksList.isEmpty() ? _activity.getString(R.string.NoDataText) : "");
    }

    /**
     * Sets background text.
     *
     * @param text Text to be set on the background of the fragment.
     */
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
        updateBackgroundText();
    }

    @Override
    public void deleteSpark(Spark spark)
    {
        _sparksRVAdapter.deleteSpark(spark);
        updateBackgroundText();
    }

    @Override
    public void sparkLiked(Spark spark)
    {
        _sparksRVAdapter.sparkLiked(spark);
    }

    @Override
    public void sparkUnliked(Spark spark)
    {
        _sparksRVAdapter.sparkUnliked(spark);
    }

    /**
     *
     * @return The list with sparks on the fragment.
     */
    public ArrayList<Spark> getSparksList()
    {
        return _sparksList;
    }

    @Override
    public void refreshSparks(ArrayList<Spark> sparks)
    {
        setSparks(sparks);
        displayElements();
    }
}