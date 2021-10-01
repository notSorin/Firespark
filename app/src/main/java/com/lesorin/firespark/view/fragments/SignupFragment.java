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

public class SignupFragment extends Fragment
{
    private View _view;
    private MaterialButton _signupButton;
    private TextInputEditText _name, _email, _password, _passwordRepeat;

    public SignupFragment()
    {
        _view = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_signup, container, false);

            initializeInputs();
            initializeSignupButton();
        }

        return _view;
    }

    private void initializeInputs()
    {
        _name = _view.findViewById(R.id.SignupName);
        _email = _view.findViewById(R.id.SignupEmail);
        _password = _view.findViewById(R.id.SignupPassword);
        _passwordRepeat = _view.findViewById(R.id.SignupPasswordRepeat);
    }

    private void initializeSignupButton()
    {
        _signupButton = _view.findViewById(R.id.SignupButton);

        _signupButton.setOnClickListener(view ->
        {
            ((StartActivity)getContext()).signUpButtonPressed(_name.getText().toString(),
                    _email.getText().toString(), _password.getText().toString(),
                    _passwordRepeat.getText().toString());
        });
    }

    public void setSignUpButtonState(boolean enabled)
    {
        _signupButton.setEnabled(enabled);
    }
}