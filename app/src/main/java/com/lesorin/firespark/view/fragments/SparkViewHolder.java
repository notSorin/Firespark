package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

public class SparkViewHolder extends RecyclerView.ViewHolder
{
    private TextView _ownerName, _sparkBody, _likes, _timestmap;
    private ImageView _sparkLike;
    private ConstraintLayout _layout;

    public SparkViewHolder(@NonNull View view)
    {
        super(view);

        _ownerName = view.findViewById(R.id.SparkOwner);
        _sparkBody = view.findViewById(R.id.SparkBody);
        _likes = view.findViewById(R.id.SparkLikes);
        _timestmap = view.findViewById(R.id.SparkTimestamp);
        _sparkLike = view.findViewById(R.id.SparkLike);
        _layout = view.findViewById(R.id.SparkLayout);
    }

    public String getOwnerName()
    {
        return _ownerName.getText().toString();
    }

    public void setOwnerName(String name)
    {
        _ownerName.setText(name);
    }

    public String getSparkBody()
    {
        return _sparkBody.getText().toString();
    }

    public void setSparkBody(String body)
    {
        _sparkBody.setText(body);
    }

    public void setSparkLiked(boolean liked)
    {
        if(liked)
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_fill);
        }
        else
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_outline);
        }
    }

    public String getLikes()
    {
        return _likes.getText().toString();
    }

    public void setLikes(String likes)
    {
        _likes.setText(likes);
    }

    public String getTimestamp()
    {
        return _timestmap.getText().toString();
    }

    public void setTimestamp(String timestamp)
    {
        _timestmap.setText(timestamp);
    }

    public ConstraintLayout getLayout()
    {
        return _layout;
    }

    public View getLikeView()
    {
        return _sparkLike;
    }
}