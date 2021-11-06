package com.lesorin.firespark.view.fragments;

import android.graphics.Color;
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
public class SparkViewHolder extends RecyclerView.ViewHolder
{
    private TextView _ownerUsername, _sparkBody, _likes, _timestmap, _commentsAmount;
    private ImageView _sparkLike, _sparkDelete, _commentsIcon;
    private ConstraintLayout _layout;

    /**
     * Instantiates a new SparkViewHolder.
     *
     * @param view A view belonging to a spark layout.
     */
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

    /**
     * Sets the first and last name and username of a user, on the spark.
     *
     * @param firstLastName The first and last name of the spark's owner.
     * @param username      The username of the spark's owner.
     */
    public void setOwnerUsername(String firstLastName, String username)
    {
        _ownerUsername.setText(firstLastName + " (@" + username + ")");
    }

    /**
     * Sets the body text of the spark.
     *
     * @param body The spark's body.
     */
    public void setSparkBody(String body)
    {
        _sparkBody.setText(body);
    }

    /**
     * Sets whether the spark is liked or not by the current user.
     *
     * @param liked True if the spark is liked by the current user, false otherwise.
     */
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

    /**
     * Sets the amount of likes that the spark has.
     *
     * @param likes The amount of likes that the spark has.
     */
    public void setLikes(int likes)
    {
        _likes.setText(String.valueOf(likes));
    }

    /**
     *
     * @return The view related to the spark's "layout".
     */
    public View getLayoutView()
    {
        return _layout;
    }

    /**
     *
     * @return The view related to the "like" button on the spark.
     */
    public View getLikeView()
    {
        return _sparkLike;
    }

    /**
     * Sets whether or not the "delete" button is visible on the spark.
     *
     * @param visible True if the current user owns the spark, false otherwise.
     */
    public void setDeleteButtonVisibility(boolean visible)
    {
        _sparkDelete.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     *
     * @return The view related to the "owner" of the comment.
     */
    public View getOwnerView()
    {
        return _ownerUsername;
    }

    /**
     * Sets the created date of the spark.
     *
     * @param created The created date as string.
     */
    public void setCreated(String created)
    {
        _timestmap.setText(created);
    }

    /**
     * Sets whether or not the spark's name and username should be drawn differently from others,
     * to let the current user know it is a spark owned by them.
     *
     * @param special the special
     */
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

    /**
     *
     * @return The view related to the "delete" button on the spark.
     */
    public View getDeleteSparkView()
    {
        return _sparkDelete;
    }

    /**
     * Sets the amount of likes that the spark has.
     *
     * @param commentsAmount The amount of likes that the spark has.
     */
    public void setCommentsAmount(int commentsAmount)
    {
        _commentsAmount.setText(String.valueOf(commentsAmount));
    }

    /**
     * Sets whether or not the spark's comment icon should be drawn differently from others,
     * to let the current user know it is a spark on which they have commented.
     *
     * @param special True if the current user has commented on the spark, false otherwise.
     */
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