package com.lesorin.firespark.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.StartActivity;

/**
 * The Welcome fragment of the app, which users see when they first open the app without being logged
 * into the network.
 */
public class WelcomeFragment extends Fragment
{
    private View _view;
    private MaterialButton _loginButton, _signUpButton;

    /**
     * Instantiates a new WelcomeFragment.
     */
    public WelcomeFragment()
    {
        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_welcome, container, false);

            initializeButtons();
        }

        return _view;
    }

    private void initializeButtons()
    {
        _loginButton = _view.findViewById(R.id.FakeLoginButton);
        _signUpButton = _view.findViewById(R.id.FakeSignUpButton);

        _loginButton.setOnClickListener(view ->
        {
            ((StartActivity)getContext()).fakeLogInButtonPressed();
        });

        _signUpButton.setOnClickListener(view ->
        {
            ((StartActivity)getContext()).fakeSignUpButtonPressed();
        });
    }
}