package com.lesorin.firespark.view.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.Spark;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * RecyclerView adapter for @{@link SparkViewHolder}.
 */
public class SparksRecycleViewAdapter extends RecyclerView.Adapter<SparkViewHolder>
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm:ss";
    private ArrayList<Spark> _sparksList;
    private SimpleDateFormat _dateFormat;

    /**
     * Instantiates a new @{@link SparksRecycleViewAdapter}.
     */
    public SparksRecycleViewAdapter()
    {
        _sparksList = new ArrayList<>();
        _dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    }

    @NonNull
    @Override
    public SparkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spark, parent, false);

        return new SparkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SparkViewHolder holder, int position)
    {
        Spark spark = _sparksList.get(position);
        int likesAmount = spark.getLikes().size();
        Date created = spark.getCreated(); //TODO Figure out how to convert server UTC date to local date.

        holder.setOwnerUsername(spark.getUserFirstLastName(), spark.getUserUsername());
        holder.setSparkBody(spark.getBody());
        holder.setSparkLiked(spark.isLikedByCurrentUser());
        holder.setDeleteButtonVisibility(spark.isOwnedByCurrentUser());
        holder.setLikes(likesAmount);
        holder.setCreated(created != null ? _dateFormat.format(created) : "");
        holder.setSpecialOwnerName(spark.isOwnedByCurrentUser());
        holder.setCommentsAmount(spark.getComments().size());
        holder.setSpecialCommentIcon(spark.containsCommentFromCurrentUser());
        holder.getLayoutView().setOnClickListener(view -> ((MainActivity)view.getContext()).sparkClicked(_sparksList.get(position)));
        holder.getLikeView().setOnClickListener(view -> ((MainActivity)view.getContext()).sparkLikeClicked(_sparksList.get(position)));
        holder.getOwnerView().setOnClickListener(view -> ((MainActivity)view.getContext()).sparkOwnerClicked(_sparksList.get(position)));
        holder.getDeleteSparkView().setOnClickListener(view -> ((MainActivity)view.getContext()).sparkDeleteClicked(_sparksList.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return _sparksList.size();
    }

    /**
     * Sets sparks to the adapter.
     *
     * @param sparks Sparks list to set.
     */
    public void setSparks(ArrayList<Spark> sparks)
    {
        _sparksList = sparks;

        notifyDataSetChanged();
    }

    /**
     * Adds a spark to the sparks list.
     *
     * @param spark Spark to add.
     */
    public void addSpark(Spark spark)
    {
        int index = _sparksList.indexOf(spark);

        if(index == -1) //Only add the spark if it is not already present.
        {
            _sparksList.add(0, spark);
            notifyDataSetChanged();
        }
    }

    /**
     * Deletes a spark from the sparks list.
     *
     * @param spark Spark to delete.
     */
    public void deleteSpark(Spark spark)
    {
        if(_sparksList.remove(spark))
        {
            notifyDataSetChanged();
        }
    }

    /**
     * Notifies the adapter that a spark has been liked.
     *
     * @param spark Spark which was liked.
     */
    public void sparkLiked(Spark spark)
    {
        sparkChanged(spark);
    }

    private void sparkChanged(Spark spark)
    {
        int sparkIndex = _sparksList.indexOf(spark);

        if(sparkIndex != -1)
        {
            notifyItemChanged(sparkIndex);
        }
    }

    /**
     * Notifies the adapter that a spark has been unliked.
     *
     * @param spark Spark which was unliked.
     */
    public void sparkUnliked(Spark spark)
    {
        sparkChanged(spark);
    }
}