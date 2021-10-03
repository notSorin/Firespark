package com.lesorin.firespark.view.fragments;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;

public class CommentViewHolder extends RecyclerView.ViewHolder
{
    private TextView _ownerName, _commentBody, _timestamp;

    public CommentViewHolder(@NonNull View view)
    {
        super(view);

        _ownerName = view.findViewById(R.id.CommentOwner);
        _commentBody = view.findViewById(R.id.CommentBody);
        _timestamp = view.findViewById(R.id.CommentTimestamp);
    }

    public String getOwnerName()
    {
        return _ownerName.getText().toString();
    }

    public void setOwnerName(String name)
    {
        _ownerName.setText(name);
    }

    public String getCommentBody()
    {
        return _commentBody.getText().toString();
    }

    public void setCommentBody(String body)
    {
        _commentBody.setText(body);
    }

    public String getTimestamp()
    {
        return _timestamp.getText().toString();
    }

    public void setTimestamp(String timestamp)
    {
        _timestamp.setText(timestamp);
    }
}