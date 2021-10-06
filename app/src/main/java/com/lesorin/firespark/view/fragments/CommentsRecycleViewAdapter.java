package com.lesorin.firespark.view.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Comment;
import java.util.ArrayList;

public class CommentsRecycleViewAdapter extends RecyclerView.Adapter<CommentViewHolder>
{
    private ArrayList<Comment> _commentsList;

    public CommentsRecycleViewAdapter(ArrayList<Comment> comments)
    {
        _commentsList = comments;
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

    }

    @Override
    public int getItemCount()
    {
        return _commentsList.size();
    }
}