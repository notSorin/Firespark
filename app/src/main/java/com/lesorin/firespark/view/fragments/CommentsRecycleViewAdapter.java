package com.lesorin.firespark.view.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CommentsRecycleViewAdapter extends RecyclerView.Adapter<CommentViewHolder>
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm";
    private SimpleDateFormat _dateFormat;
    private ArrayList<Comment> _commentsList;

    public CommentsRecycleViewAdapter()
    {
        _commentsList = null;
        _dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position)
    {
        Comment comment = _commentsList.get(position);
        int likesAmount = comment.getLikes().size();

        holder.setOwnerName(comment.getOwnerFirstLastName(), comment.getOwnerUsername());
        holder.setBody(comment.getBody());
        holder.setCommentLiked(comment.isLikedByCurrentUser());
        holder.setDeleteButtonVisibility(comment.isOwnedByCurrentUser());
        holder.setLikes(likesAmount);
        holder.setCreated(_dateFormat.format(comment.getCreated().toDate()));
        holder.setSpecialOwnerName(comment.isOwnedByCurrentUser());
        holder.setReplyName(comment.getReplyToFirstLastName(), comment.getReplyToUsername());
    }

    @Override
    public int getItemCount()
    {
        return _commentsList.size();
    }

    public void setComments(ArrayList<Comment> comments)
    {
        _commentsList = comments;

        notifyDataSetChanged();
    }

    public void addComment(Comment comment)
    {
        int index = _commentsList.indexOf(comment);

        if(index == -1) //Only add the comment if it is not already present.
        {
            _commentsList.add(0, comment);
            notifyDataSetChanged();
        }
    }

    public void deleteSpark(Spark spark)
    {
        if(_commentsList.remove(spark))
        {
            notifyDataSetChanged();
        }
    }
}