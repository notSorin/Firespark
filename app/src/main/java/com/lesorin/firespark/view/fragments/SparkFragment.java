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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.pojo.Comment;
import com.lesorin.firespark.presenter.pojo.Spark;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SparkFragment extends Fragment
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm";
    private View _view;
    private RecyclerView _comments;
    private CommentsRecycleViewAdapter _commentsRVAdapter;
    protected RecyclerView.LayoutManager _rvLayoutManager;
    private SwipeRefreshLayout _swipeRefresh;
    private Spark _spark;
    private TextView _ownerUsername, _sparkBody, _likes, _timestmap, _commentsAmount;
    private ImageView _sparkLike, _sparkDelete, _commentsIcon;
    private ConstraintLayout _layout;
    private SimpleDateFormat _dateFormat;
    private ArrayList<Comment> _commentsList;
    private TextView _backgroundText;

    public SparkFragment()
    {
        _view = null;
        _spark = null;
        _dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        _commentsRVAdapter = new CommentsRecycleViewAdapter();
        _rvLayoutManager = new LinearLayoutManager(getContext());
        _commentsList = null;
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
        }

        return _view;
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
        _timestmap = _view.findViewById(R.id.SparkTimestamp);
        _sparkLike = _view.findViewById(R.id.SparkLike);
        _layout = _view.findViewById(R.id.SparkLayout);
        _sparkDelete = _view.findViewById(R.id.SparkDelete);
        _commentsAmount = _view.findViewById(R.id.SparkCommentsAmount);
        _commentsIcon = _view.findViewById(R.id.SparkCommentsIcon);

        _sparkLike.setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkLikeClicked(_spark);
        });

        _ownerUsername.setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkOwnerClicked(_spark);
        });

        _sparkDelete.setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkDeleteClicked(_spark);
        });
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

        displayComments();
    }

    private void displayComments()
    {
        if(getContext() != null && _commentsList != null)
        {
            if(_commentsRVAdapter != null)
            {
                _commentsRVAdapter.setComments(_commentsList);
                setBackGroundText(_commentsList.isEmpty() ? getString(R.string.NoCommentsText) : "");
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

        displaySpark();
    }

    private void displaySpark()
    {
        if(getContext() != null && _spark != null)
        {
            _ownerUsername.setText(_spark.getOwnerFirstLastName() + " (@" + _spark.getOwnerUsername() + ")");
            _sparkBody.setText(_spark.getBody());
            setSparkLiked(_spark.isLikedByCurrentUser());
            _sparkDelete.setVisibility(_spark.isOwnedByCurrentUser() ? View.VISIBLE : View.GONE);
            setLikesAmount(_spark.getLikes().size());
            _timestmap.setText(_dateFormat.format(_spark.getCreated().toDate()));
            setSpecialOwnerName(_spark.isOwnedByCurrentUser());
            _commentsAmount.setText(String.valueOf(_spark.getComments().size()));
            setSpecialCommentIcon(_spark.containsCommentFromCurrentUser());
        }
    }

    private void setSpecialCommentIcon(boolean special)
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

    private void setLikesAmount(int likesAmount)
    {
        _likes.setText(String.valueOf(likesAmount));
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

    private void setSparkLiked(boolean liked)
    {
        if(liked)
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_fill);
            _sparkLike.setColorFilter(_sparkLike.getContext().getColor(R.color.primaryColor));
        }
        else
        {
            _sparkLike.setImageResource(R.drawable.thumbs_up_outline);
            _sparkLike.clearColorFilter();
        }
    }

    public void sparkLiked(Spark spark)
    {
        if(spark == _spark)
        {
            setSparkLiked(spark.isLikedByCurrentUser());
            setLikesAmount(spark.getLikes().size());
        }
    }
}