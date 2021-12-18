package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

/**
 * Fragment dedicated to holding a list of @{@link SimplifiedUserViewHolder}.
 */
public class UsersFragment extends FiresparkFragmentAdapter
{
    private View _view;
    private TextView _title;
    private RecyclerView _usersRV;
    private SimplifiedUserRecycleViewAdapter _usersRVAdapter;
    private boolean _holdingFollowers;
    private User _user;

    /**
     * Instantiates a new UsersFragment.
     *
     * @param activity Activity to be accessible from the fragment.
     */
    public UsersFragment(MainActivity activity)
    {
        super(activity);

        _view = null;
        _usersRVAdapter = new SimplifiedUserRecycleViewAdapter(activity);
        _holdingFollowers = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_users, container, false);

            initializeTitle();
            initializeUsersRV();
        }

        return _view;
    }

    @Override
    public void displayElements()
    {
        if(_usersRVAdapter != null)
        {
            setTitle();
            _usersRVAdapter.displayData();
        }
    }

    private void setTitle()
    {
        String title = _activity.getString(isHoldingFollowers() ? R.string.FollowersTitle : R.string.FollowingTitle) ;

        _title.setText(String.format(title, _user.getUsername()));
    }

    private void initializeUsersRV()
    {
        _usersRV = _view.findViewById(R.id.UsersRV);

        _usersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        _usersRV.setItemAnimator(new DefaultItemAnimator());
        _usersRV.setAdapter(_usersRVAdapter);
    }

    private void initializeTitle()
    {
        _title = _view.findViewById(R.id.Title);
    }

    public void setUsers(ArrayList<User> users)
    {
        _usersRVAdapter.setUsers(users);
    }

    @Override
    public boolean isUsersFragment()
    {
        return true;
    }

    /**
     * Sets whether the fragment is holding a user's followers.
     *
     * @param holdingFollowers True if the fragment is holding a user's followers, false otherwise.
     */
    public void setHoldingFollowers(boolean holdingFollowers)
    {
        _holdingFollowers = holdingFollowers;
    }

    /**
     * Sets whether the fragment is holding a user's following.
     *
     * @param holdingFollowing True if the fragment is holding a user's following, false otherwise.
     */
    public void setHoldingFollowing(boolean holdingFollowing)
    {
        _holdingFollowers = !holdingFollowing;
    }

    /**
     * @return True if the fragment is holding a user's followers, false otherwise.
     */
    public boolean isHoldingFollowers()
    {
        return _holdingFollowers;
    }

    /**
     * @return True if the fragment is holding a user's following, false otherwise.
     */
    public boolean isHoldingFollowing()
    {
        return !_holdingFollowers;
    }

    @Override
    public void setUser(User user)
    {
        _user = user;
    }

    @Override
    public User getUser()
    {
        return _user;
    }
}