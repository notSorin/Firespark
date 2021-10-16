package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.MainActivity;

public class PopularFragment extends FragmentWithSparks
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_popular, container, false);

            initializeSwipeRefresh(() -> ((MainActivity)getContext()).requestPopularData());
            initializeSparksRecycleView();
            ((MainActivity)getContext()).requestPopularData();
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