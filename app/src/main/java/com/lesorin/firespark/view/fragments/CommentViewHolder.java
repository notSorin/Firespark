package com.lesorin.firespark.view.fragments;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

/**
 * RecyclerView holder for @{@link com.lesorin.firespark.presenter.Comment}.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder
{
    private final TextView _ownerName, _body, _timestamp, _likes, _replyOwner;
    private final View _replyLayout;
    private final ImageView _likeButton, _deleteButton, _replyButton;

    /**
     * Instantiates a new Comment view holder.
     *
     * @param view A view belonging to a comment layout.
     */
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

    /**
     * Sets the first and last name and username of a user, on the comment.
     *
     * @param firstLastName The first and last name of the comment's owner.
     * @param username      The username of the comment's owner.
     */
    public void setOwnerName(String firstLastName, String username)
    {
        _ownerName.setText(firstLastName + " (@" + username + ")");
    }

    /**
     * Sets the created date of the comment.
     *
     * @param created The created date as string.
     */
    public void setCreated(String created)
    {
        _timestamp.setText(created);
    }

    /**
     * Sets the amount of likes that the comment has.
     *
     * @param likesAmount The amount of likes that the comment has.
     */
    public void setLikesAmount(int likesAmount)
    {
        _likes.setText(String.valueOf(likesAmount));
    }

    /**
     * Sets the body text of a comment.
     *
     * @param body The comment's body.
     */
    public void setBody(String body)
    {
        _body.setText(body);
    }

    /**
     * Sets whether the comment is liked or not by the current user.
     *
     * @param liked True if the comment is liked by the current user, false otherwise.
     */
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

    /**
     * Sets whether or not the "delete" button is visible on the comment.
     *
     * @param visible True if the current user owns the comment, false otherwise.
     */
    public void setDeleteButtonVisibility(boolean visible)
    {
        _deleteButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * Sets whether or not the comment's name and username should be drawn differently from others,
     * to let the current user know it is a comment written by them.
     *
     * @param special True if the comment belongs to the current user, false otherwise.
     */
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

    /**
     * Sets the reply name and username on the comment, or it hides that view if the comment is not
     * a reply.
     *
     * @param replyToId     The id of the comment to which this comment is a reply.
     * @param firstLastName The first and last name of the owner of the comment to which this comment is a reply.
     * @param username      The username of the owner of the comment to which this comment is a reply.
     */
    public void setReply(@Nullable String replyToId, @Nullable String firstLastName, @Nullable String username)
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

    /**
     *
     * @return The view related to the "reply" button on the comment.
     */
    public View getReplyButton()
    {
        return _replyButton;
    }

    /**
     *
     * @return The view related to the "delete" button on the comment.
     */
    public View getDeleteView()
    {
        return _deleteButton;
    }

    /**
     *
     * @return The view related to the "like" button on the comment.
     */
    public View getLikeView()
    {
        return _likeButton;
    }

    /**
     *
     * @return The view related to the "owner" of the comment.
     */
    public View getOwnerView()
    {
        return _ownerName;
    }
}