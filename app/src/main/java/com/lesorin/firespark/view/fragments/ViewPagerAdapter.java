package com.lesorin.firespark.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to be used by the NavigationView on @{@link com.lesorin.firespark.view.activities.MainActivity}.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> _fragmentsList;
    private final List<String> _fragmentsTitles;

    /**
     * Instantiates a new ViewPagerAdapter.
     *
     * @param manager @FragmentManager to be used by the adapter.
     */
    public ViewPagerAdapter(FragmentManager manager)
    {
        super(manager);

        _fragmentsList = new ArrayList<>();
        _fragmentsTitles = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return _fragmentsList.get(position);
    }

    @Override
    public int getCount()
    {
        return _fragmentsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return _fragmentsTitles.get(position);
    }

    /**
     * Adds a fragment to the fragments list which the adapter is holding.
     *
     * @param fragment Fragment to add.
     * @param title    Title of the fragment to add.
     */
    public void addFragment(Fragment fragment, String title)
    {
        _fragmentsList.add(fragment);
        _fragmentsTitles.add(title);
    }
}