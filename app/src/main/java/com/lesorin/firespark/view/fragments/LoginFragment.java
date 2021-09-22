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
import com.lesorin.firespark.view.activities.StartActivity;

public class LoginFragment extends Fragment
{
    private View _view;
    private MaterialButton _loginButton;
    private TextInputEditText _email, _password;

    public LoginFragment()
    {
        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_login, container, false);

            initializeInputs();
            initializeLoginButton();
        }

        return _view;
    }

    private void initializeInputs()
    {
        _email = _view.findViewById(R.id.LoginEmail);
        _password = _view.findViewById(R.id.LoginPassword);
    }

    private void initializeLoginButton()
    {
        _loginButton = _view.findViewById(R.id.LoginButton);

        _loginButton.setOnClickListener(view ->
        {
            ((StartActivity)getContext()).logInButtonPressed(_email.getText().toString(), _password.getText().toString());
        });
    }
}