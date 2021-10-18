package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.MainActivity;

public class HomeFragment extends FragmentWithSparks
{
    public HomeFragment(MainActivity activity)
    {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_home, container, false);

            initializeBackgroundText();
            initializeSwipeRefresh(() -> _activity.refreshHomeData());
            initializeSparksRecycleView();
        }

        return _view;
    }

    @Override
    public boolean isHomeFragment()
    {
        return true;
    }

    @Override
    public boolean isMainFragment()
    {
        return true;
    }
}