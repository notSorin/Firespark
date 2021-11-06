package com.lesorin.firespark.model.rest;

import static com.lesorin.firespark.model.rest.ModelConstants.*;
import android.content.Context;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lesorin.firespark.presenter.StartContract;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Start model of the app.
 */
public class StartModel implements StartContract.Model
{
    private StartContract.PresenterModel _presenter;
    private final RequestQueue _requestQueue;
    private final SharedPreferences _preferences;

    /**
     * Instantiates a new StartModel.
     *
     * @param context Context to be used by the model.
     */
    public StartModel(Context context)
    {
        _requestQueue = Volley.newRequestQueue(context);
        _preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void setPresenter(StartContract.PresenterModel presenter)
    {
        _presenter = presenter;
    }

    @Override
    public void requestSignUp(String firstLastName, String username, String email, String password)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);
                int code = json.getInt(KEY_CODE);
                String message = json.getString(KEY_MESSAGE);

                if(code == 200)
                {
                    _presenter.responseSignUpSuccess();
                }
                else if(code == 400)
                {
                    //TODO In the future, make the server return codes indicating why the sign up
                    //process failed, then call specific functions from the presenter, so that the view
                    //can display errors in different languages.
                    _presenter.responseSignupFailure(message);
                }
            }
            catch(JSONException e)
            {
                _presenter.responseSignUpUnknownError();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, SIGN_UP_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_SIGN_UP_EMAIL, email);
                params.put(KEY_SIGN_UP_PASSWORD, password);
                params.put(KEY_SIGN_UP_USERNAME, username);
                params.put(KEY_SIGN_UP_FIRST_LAST_NAME, firstLastName);

                return params;
            }
        };

        _requestQueue.add(request);
    }

    @Override
    public void requestLogIn(String emailOrUsername, String password)
    {
        Response.Listener<String> rl = response ->
        {
            try
            {
                JSONObject json = new JSONObject(response);

                if(json.getInt(KEY_CODE) == 200)
                {
                    JSONObject message = json.getJSONObject(KEY_MESSAGE);
                    String token = message.getString(KEY_TOKEN);
                    String userid = message.getString(KEY_USERID);

                    saveUserToken(token);
                    saveUserId(userid);
                    _presenter.responseLogInSuccess();
                }
                else
                {
                    _presenter.responseLogInFailure();
                }
            }
            catch(JSONException e)
            {
                _presenter.responseLogInFailure();
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, LOG_IN_URL, rl,
                error -> _presenter.responseNetworkError())
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                params.put(KEY_LOG_IN_EMAIL_OR_USERNAME, emailOrUsername);
                params.put(KEY_LOG_IN_PASSWORD, password);

                return params;
            }
        };

        _requestQueue.add(request);
    }

    private void saveUserId(String userid)
    {
        _preferences.edit().putString(KEY_USERID, userid).apply();
    }

    private void saveUserToken(String token)
    {
        _preferences.edit().putString(KEY_TOKEN, token).apply();
    }

    @Override
    public boolean isUserSignedIn()
    {
        return _preferences.getString(KEY_TOKEN, null) != null;
    }
}