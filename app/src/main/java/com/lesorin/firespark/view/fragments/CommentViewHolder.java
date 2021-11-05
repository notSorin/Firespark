package com.lesorin.firespark.view.fragments;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

public class CommentViewHolder extends RecyclerView.ViewHolder
{
    private TextView _ownerName, _body, _timestamp, _likes, _replyOwner;
    private View _replyLayout;
    private ImageView _likeButton, _deleteButton, _replyButton;

    public CommentViewHolder(@NonNull View view)
    {
        super(view);

        _ownerName = view.findViewById(R.id.CommentOwner);
        _body = view.findViewById(R.id.CommentBody);
        _timestamp = view.findViewById(R.id.CommentTimestamp);
        _replyLayout = view.findViewById(R.id.CommentReplyLayout);
        _replyOwner = view.findViewById(R.id.CommentReplyOwner);
        _likes = view.findViewById(R.id.CommentLikes);
        _likeButton = view.findViewById(R.id.CommentLikeButton);
        _deleteButton = view.findViewById(R.id.CommentDelete);
        _replyButton  = view.findViewById(R.id.CommentReplyButton);
    }

    public void setOwnerName(String firstLastName, String username)
    {
        _ownerName.setText(firstLastName + " (@" + username + ")");
    }

    public void setCreated(String created)
    {
        _timestamp.setText(created);
    }

    public void setLikes(int likesAmount)
    {
        _likes.setText(String.valueOf(likesAmount));
    }

    public void setBody(String body)
    {
        _body.setText(body);
    }

    public void setCommentLiked(boolean liked)
    {
        if(liked)
        {
            _likeButton.setImageResource(R.drawable.thumbs_up_fill);
            _likeButton.setColorFilter(_likeButton.getContext().getColor(R.color.primaryColor));
        }
        else
        {
            _likeButton.setImageResource(R.drawable.thumbs_up_outline);
            _likeButton.clearColorFilter();
        }
    }

    public void setDeleteButtonVisibility(boolean visible)
    {
        _deleteButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setSpecialOwnerName(boolean special)
    {
        if(special)
        {
            _ownerName.setBackgroundResource(R.drawable.spark_owner_background);
        }
        else
        {
            _ownerName.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void setReply(String replyToId, String firstLastName, String username)
    {
        if(replyToId != null) //It's a reply to another comment.
        {
            _replyLayout.setVisibility(View.VISIBLE);

            if(firstLastName != null && username != null)
            {
                _replyOwner.setText(firstLastName + " (@" + username + ")");
            }
            else //If the first and last name and username are null, then the comment was deleted.
            {
                _replyOwner.setText(R.string.DeletedComment);
            }
        }
        else
        {
            _replyLayout.setVisibility(View.GONE);
        }
    }

    public View getReplyButton()
    {
        return _replyButton;
    }

    public View getDeleteView()
    {
        return _deleteButton;
    }

    public View getLikeView()
    {
        return _likeButton;
    }
}