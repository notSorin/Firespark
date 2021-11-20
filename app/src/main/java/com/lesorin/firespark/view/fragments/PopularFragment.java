package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.MainActivity;

/**
 * One of the main fragments of the app, whose purpose is to display the "popular" sparks on the network.
 */
public class PopularFragment extends FragmentWithSparks
{
    /**
     * Instantiates a new Popular fragment.
     *
     * @param activity Activity to be accessible from the fragment.
     */
    public PopularFragment(MainActivity activity)
    {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_popular, container, false);

            initializeBackgroundText();
            initializeSwipeRefresh(() -> _activity.refreshPopularData());
            initializeSparksRecyclerView();
        }

        return _view;
    }

    @Override
    public boolean isPopularFragment()
    {
        return true;
    }

    @Override
    public boolean isMainFragment()
    {
        return true;
    }
}