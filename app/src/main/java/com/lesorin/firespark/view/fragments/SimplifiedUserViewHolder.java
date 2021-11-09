package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

/**
 * RecyclerView holder for @{@link com.lesorin.firespark.presenter.Spark}.
 */
public class SimplifiedUserViewHolder extends RecyclerView.ViewHolder
{
    private TextView _firstLastName, _username, _joined;
    private ImageView _original;
    private ConstraintLayout _layout;

    /**
     * Instantiates a new @{@link SimplifiedUserViewHolder}.
     *
     * @param view A view belonging to a simplified user layout.
     */
    public SimplifiedUserViewHolder(@NonNull View view)
    {
        super(view);

        _firstLastName = view.findViewById(R.id.FirstLastName);
        _username = view.findViewById(R.id.Username);
        _joined = view.findViewById(R.id.UserJoined);
        _layout = view.findViewById(R.id.SimplifiedUserLayout);
        _original = view.findViewById(R.id.OriginalIcon);
    }

    /**
     *
     * @return The view related to the user's "layout".
     */
    public View getLayoutView()
    {
        return _layout;
    }

    public void setName(String firstLastName)
    {
        _firstLastName.setText(firstLastName);
    }

    public void setUsername(String username)
    {
        _username.setText(username);
    }

    public void setJoined(String joined)
    {
        _joined.setText(joined);
    }

    public void setOriginal(boolean isOriginal)
    {
        _original.setVisibility(isOriginal ? View.VISIBLE : View.GONE);
    }
}