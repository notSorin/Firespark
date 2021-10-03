package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

public class SparkViewHolder extends RecyclerView.ViewHolder
{
    private TextView _ownerName, _sparkBody, _likesTimestamp;
    private ImageView _sparkLike;

    public SparkViewHolder(@NonNull View view)
    {
        super(view);

        _ownerName = view.findViewById(R.id.SparkOwner);
        _sparkBody = view.findViewById(R.id.SparkBody);
        _likesTimestamp = view.findViewById(R.id.SparkLikesTimestamp);
        _sparkLike = view.findViewById(R.id.SparkLike);
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

    public String getLikesTimestamp()
    {
        return _likesTimestamp.getText().toString();
    }

    public void setLikesTimestamp(String timestamp)
    {
        _likesTimestamp.setText(timestamp);
    }
}