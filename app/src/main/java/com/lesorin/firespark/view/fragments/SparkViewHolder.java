package com.lesorin.firespark.view.fragments;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

public class SparkViewHolder extends RecyclerView.ViewHolder
{
    private TextView _ownerUsername, _sparkBody, _likes, _timestmap, _commentsAmount;
    private ImageView _sparkLike, _sparkDelete, _commentsIcon;
    private ConstraintLayout _layout;

    public SparkViewHolder(@NonNull View view)
    {
        super(view);

        _ownerUsername = view.findViewById(R.id.SparkUsername);
        _sparkBody = view.findViewById(R.id.SparkBody);
        _likes = view.findViewById(R.id.SparkLikes);
        _timestmap = view.findViewById(R.id.SparkTimestamp);
        _sparkLike = view.findViewById(R.id.SparkLike);
        _layout = view.findViewById(R.id.SparkLayout);
        _sparkDelete = view.findViewById(R.id.SparkDelete);
        _commentsAmount = view.findViewById(R.id.SparkCommentsAmount);
        _commentsIcon = view.findViewById(R.id.SparkCommentsIcon);
    }

    public String getOwnerUsername()
    {
        return _ownerUsername.getText().toString();
    }

    public void setOwnerUsername(String firstLastName, String username)
    {
        _ownerUsername.setText(firstLastName + " (@" + username + ")");
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
            _sparkLike.setColorFilter(getLikeView().getContext().getColor(R.color.primaryColor));
        }
        else
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_outline);
            _sparkLike.clearColorFilter();
        }
    }

    public String getLikes()
    {
        return _likes.getText().toString();
    }

    public void setLikes(int likes)
    {
        _likes.setText(String.valueOf(likes));
    }

    public String getTimestamp()
    {
        return _timestmap.getText().toString();
    }

    public void setTimestamp(String timestamp)
    {
        _timestmap.setText(timestamp);
    }

    public View getLayoutView()
    {
        return _layout;
    }

    public View getLikeView()
    {
        return _sparkLike;
    }

    public void setDeleteButtonVisibility(boolean visible)
    {
        _sparkDelete.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public View getOwnerView()
    {
        return _ownerUsername;
    }

    public void setCreated(String created)
    {
        _timestmap.setText(created);
    }

    public void setSpecialOwnerName(boolean special)
    {
        if(special)
        {
            _ownerUsername.setBackgroundResource(R.drawable.spark_owner_background);
        }
        else
        {
            _ownerUsername.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public View getDeleteSparkView()
    {
        return _sparkDelete;
    }

    public void setCommentsAmount(int commentsAmount)
    {
        _commentsAmount.setText(String.valueOf(commentsAmount));
    }

    public void setSpecialCommentIcon(boolean special)
    {
        if(special)
        {
            _commentsIcon.setColorFilter(_commentsIcon.getContext().getColor(R.color.primaryColor));
        }
        else
        {
            _commentsIcon.clearColorFilter();
        }
    }
}