package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

/**
 * Fragment dedicated to searching for users on the network.
 */
public class SearchUserFragment extends FiresparkFragmentAdapter
{
    private View _view;
    private TextInputEditText _nameInput;
    private MaterialButton _searchButton;
    private RecyclerView _usersRV;
    private SimplifiedUserRecycleViewAdapter _usersRVAdapter;

    /**
     * Instantiates a new Search user fragment.
     *
     * @param activity Activity to be accessible from the fragment.
     */
    public SearchUserFragment(MainActivity activity)
    {
        super(activity);

        _view = null;
        _usersRVAdapter = new SimplifiedUserRecycleViewAdapter(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_search_user, container, false);

            initializeUsernameField();
            initializeSearchButton();
            initializeUsersRV();
        }

        return _view;
    }

    @Override
    public void displayElements()
    {
        if(_usersRVAdapter != null)
        {
            _usersRVAdapter.displayData();
        }
    }

    private void initializeUsersRV()
    {
        _usersRV = _view.findViewById(R.id.UsersRV);

        _usersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        _usersRV.setItemAnimator(new DefaultItemAnimator());
        _usersRV.setAdapter(_usersRVAdapter);
    }

    private void initializeSearchButton()
    {
        _searchButton = _view.findViewById(R.id.SearchButton);

        _searchButton.setOnClickListener(view -> _activity.requestSearchUser(_nameInput.getText().toString()));
    }

    private void initializeUsernameField()
    {
        _nameInput = _view.findViewById(R.id.SearchUsername);
    }

    public void setUsers(ArrayList<User> users)
    {
        _usersRVAdapter.setUsers(users);
    }
}