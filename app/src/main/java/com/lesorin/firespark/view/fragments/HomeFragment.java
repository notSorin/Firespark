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
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.view.activities.MainActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private View _view;
    private RecyclerView _homeSparks;

    public HomeFragment()
    {
        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_home, container, false);

            initializeHomeSparks();
            ((MainActivity)getContext()).requestHomeData();
        }

        return _view;
    }

    private void initializeHomeSparks()
    {
        _homeSparks = _view.findViewById(R.id.HomeSparks);
    }

    public void setSparks(ArrayList<MainActivityContract.Spark> sparks)
    {
        SparksRecycleViewAdapter srva = new SparksRecycleViewAdapter(sparks);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());

        _homeSparks.setLayoutManager(lm);
        _homeSparks.setItemAnimator(new DefaultItemAnimator());
        _homeSparks.setAdapter(srva);

    }
}