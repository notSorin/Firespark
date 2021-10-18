package com.lesorin.firespark.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SparkFragment extends FiresparkFragmentAdapter
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm:ss";
    private View _view;
    private RecyclerView _comments;
    private CommentsRecycleViewAdapter _commentsRVAdapter;
    protected RecyclerView.LayoutManager _rvLayoutManager;
    private SwipeRefreshLayout _swipeRefresh;
    private Spark _spark;
    private TextView _ownerUsername, _sparkBody, _likes, _timestamp, _commentsAmount;
    private ImageView _sparkLike, _sparkDelete, _commentsIcon, _sendComment, _cancelCommentReply;
    private SimpleDateFormat _dateFormat;
    private ArrayList<Comment> _commentsList;
    private TextView _backgroundText, _commentReplyOwner;
    private TextInputEditText _commentInput;
    private View _commentReplyLayout;
    private Comment _replyComment;

    public SparkFragment(MainActivity activity)
    {
        super(activity);

        _view = null;
        _spark = null;
        _dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        _commentsRVAdapter = new CommentsRecycleViewAdapter(this);
        _rvLayoutManager = new LinearLayoutManager(getContext());
        _commentsList = null;
        _replyComment = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_spark, container, false);

            initializeSparkViews();
            initializeSwipeRefresh();
            initializeCommentsRecycleView();
            initializeBackgroundText();
            initializeNewCommentViews();
        }

        return _view;
    }

    private void initializeNewCommentViews()
    {
        _commentReplyLayout = _view.findViewById(R.id.CommentReplyLayout);
        _cancelCommentReply = _view.findViewById(R.id.CancelCommentReply);
        _commentInput = _view.findViewById(R.id.NewCommentInput);
        _sendComment = _view.findViewById(R.id.SendComment);
        _commentReplyOwner = _view.findViewById(R.id.CommentReplyOwner);

        _sendComment.setOnClickListener(view -> _activity.sendComment(_spark, _commentInput.getText().toString(), _replyComment));

        _cancelCommentReply.setOnClickListener(view ->
        {
            _replyComment = null;

            updateCommentReply();
        });
    }

    private void initializeBackgroundText()
    {
        _backgroundText = _view.findViewById(R.id.BackgroundText);
    }

    private void initializeCommentsRecycleView()
    {
        _comments = _view.findViewById(R.id.CommentsRV);

        _comments.setLayoutManager(_rvLayoutManager);
        _comments.setItemAnimator(new DefaultItemAnimator());
        _comments.setAdapter(_commentsRVAdapter);
    }

    private void initializeSparkViews()
    {
        _ownerUsername = _view.findViewById(R.id.SparkUsername);
        _sparkBody = _view.findViewById(R.id.SparkBody);
        _likes = _view.findViewById(R.id.SparkLikes);
        _timestamp = _view.findViewById(R.id.SparkTimestamp);
        _sparkLike = _view.findViewById(R.id.SparkLike);
        _sparkDelete = _view.findViewById(R.id.SparkDelete);
        _commentsAmount = _view.findViewById(R.id.SparkCommentsAmount);
        _commentsIcon = _view.findViewById(R.id.SparkCommentsIcon);

        _sparkLike.setOnClickListener(view -> _activity.sparkLikeClicked(_spark));
        _ownerUsername.setOnClickListener(view -> _activity.sparkOwnerClicked(_spark));
        _sparkDelete.setOnClickListener(view -> _activity.sparkDeleteClicked(_spark));
    }

    private void initializeSwipeRefresh()
    {
        _swipeRefresh = _view.findViewById(R.id.SwipeRefresh);

        _swipeRefresh.setOnRefreshListener(() ->
        {
            //todo
            _swipeRefresh.setRefreshing(false);
        });
    }

    public void setComments(ArrayList<Comment> comments)
    {
        _commentsList = comments;
    }

    private void displayComments()
    {
        if(_commentsList != null)
        {
            if(_commentsRVAdapter != null)
            {
                _commentsRVAdapter.setComments(_commentsList);
                setBackGroundText(_commentsList.isEmpty() ? _activity.getString(R.string.NoCommentsText) : "");
            }

            if(_swipeRefresh != null)
            {
                _swipeRefresh.setRefreshing(false);
            }
        }
    }

    public void setBackGroundText(String text)
    {
        if(_backgroundText != null)
        {
            _backgroundText.setText(text);
        }
    }

    public void setSpark(Spark spark)
    {
        _spark = spark;
    }

    @Override
    public void displayElements()
    {
        if(_spark != null)
        {
            updateOwnerUsername();
            updateBody();
            updateLikeIcon();
            updateDeleteIcon();
            updateLikesAmount();
            updateTimestamp();
            updateCommentsAmount();
            updateCommentInput();
            updateCommentReply();
        }

        displayComments();
    }

    private void updateCommentReply()
    {
        if(_replyComment != null)
        {
            _commentReplyLayout.setVisibility(View.VISIBLE);
            _commentReplyOwner.setText(_replyComment.getOwnerFirstLastName());
        }
        else
        {
            _commentReplyLayout.setVisibility(View.GONE);
            _replyComment = null;
        }
    }

    private void updateCommentInput()
    {
        _commentInput.setText("");
    }

    private void updateCommentsAmount()
    {
        _commentsAmount.setText(String.valueOf(_spark.getComments().size()));

        if(_spark.containsCommentFromCurrentUser())
        {
            _commentsIcon.setColorFilter(_activity.getColor(R.color.primaryColor));
        }
        else
        {
            _commentsIcon.clearColorFilter();
        }
    }

    private void updateTimestamp()
    {
        Timestamp sparkTimestamp = _spark.getCreated();
        Date created = sparkTimestamp != null ? sparkTimestamp.toDate() : null;

        _timestamp.setText(created != null ? _dateFormat.format(created) : "");
    }

    private void updateDeleteIcon()
    {
        _sparkDelete.setVisibility(_spark.isOwnedByCurrentUser() ? View.VISIBLE : View.GONE);
    }

    private void updateBody()
    {
        _sparkBody.setText(_spark.getBody());
    }

    private void updateOwnerUsername()
    {
        _ownerUsername.setText(String.format(_activity.getString(R.string.SparkOwnerUsername), _spark.getOwnerFirstLastName(), _spark.getOwnerUsername()));

        if(_spark.isOwnedByCurrentUser())
        {
            _ownerUsername.setBackgroundResource(R.drawable.spark_owner_background);
        }
        else
        {
            _ownerUsername.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void updateLikesAmount()
    {
        _likes.setText(String.valueOf(_spark.getLikesAmount()));
    }

    private void updateLikeIcon()
    {
        if(_spark.isLikedByCurrentUser())
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_fill);
            _sparkLike.setColorFilter(_activity.getColor(R.color.primaryColor));
        }
        else
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_outline);
            _sparkLike.clearColorFilter();
        }
    }

    @Override
    public void sparkLiked(Spark spark)
    {
        updateLikeIcon();
        updateLikesAmount();
    }

    @Override
    public void sparkLikeRemoved(Spark spark)
    {
        updateLikeIcon();
        updateLikesAmount();
    }

    public void setReplyComment(Comment comment)
    {
        _replyComment = comment;

        updateCommentReply();
    }
}