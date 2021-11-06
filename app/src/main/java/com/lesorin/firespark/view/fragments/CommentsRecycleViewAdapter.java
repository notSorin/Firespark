package com.lesorin.firespark.view.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Comment;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CommentsRecycleViewAdapter extends RecyclerView.Adapter<CommentViewHolder>
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm:ss";
    private SimpleDateFormat _dateFormat;
    private ArrayList<Comment> _commentsList;
    private SparkFragment _sparkFragment;

    public CommentsRecycleViewAdapter(SparkFragment sparkFragment)
    {
        _commentsList = new ArrayList<>();
        _dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        _sparkFragment = sparkFragment;
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

        holder.setOwnerName(comment.getUserFirstLastName(), comment.getUserUsername());
        holder.setBody(comment.getBody());
        holder.setCommentLiked(comment.isLikedByCurrentUser());
        holder.setDeleteButtonVisibility(comment.isOwnedByCurrentUser());
        holder.setLikes(likesAmount);
        holder.setCreated(_dateFormat.format(comment.getCreated()));
        holder.setSpecialOwnerName(comment.isOwnedByCurrentUser());
        holder.setReply(comment.getReplyToId(), comment.getReplyToFirstLastName(), comment.getReplyToUsername());
        holder.getReplyButton().setOnClickListener(view -> _sparkFragment.setReplyComment(_commentsList.get(position)));
        holder.getDeleteView().setOnClickListener(view -> ((MainActivity)view.getContext()).commentDeleteClicked(_commentsList.get(position)));
        holder.getLikeView().setOnClickListener(view -> ((MainActivity)view.getContext()).commentLikeClicked(_commentsList.get(position)));
        holder.getOwnerView().setOnClickListener(view -> ((MainActivity)view.getContext()).commentOwnerClicked(_commentsList.get(position)));
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

    public void deleteComment(Comment comment)
    {
        if(_commentsList.remove(comment))
        {
            notifyDataSetChanged();
        }
    }
}