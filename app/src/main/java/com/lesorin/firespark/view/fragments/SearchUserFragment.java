package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.MainActivity;

public class SearchUserFragment extends Fragment
{
    private View _view;
    private TextInputEditText _username;
    private MaterialButton _searchButton;

    public SearchUserFragment()
    {
        _view = null;
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
        }

        return _view;
    }

    private void initializeSearchButton()
    {
        _searchButton = _view.findViewById(R.id.SearchButton);

        _searchButton.setOnClickListener(view ->
        {
            ((MainActivity)getContext()).requestSearchUserByUsername(_username.getText().toString());
        });
    }

    private void initializeUsernameField()
    {
        _username = _view.findViewById(R.id.SearchUsername);
    }
}