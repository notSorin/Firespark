package com.lesorin.firespark.view.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;import com.lesorin.firespark.presenter.pojo.Spark;

import java.util.ArrayList;
import java.util.Locale;

public class SparksRecycleViewAdapter extends RecyclerView.Adapter<SparkViewHolder>
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm:ss";
    private ArrayList<Spark> _sparksList;
    private SimpleDateFormat _dateFormat;

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

        holder.setOwnerUsername(spark.getOwnerFirstLastName(), spark.getOwnerUsername());
        holder.setSparkBody(spark.getBody());
        holder.setSparkLiked(spark.isLikedByCurrentUser());
        holder.setDeleteButtonVisibility(spark.isOwnedByCurrentUser());
        holder.setLikes(likesAmount);
        holder.setCreated(_dateFormat.format(spark.getCreated().toDate()));
        holder.setSpecialOwnerName(spark.isOwnedByCurrentUser());

        holder.getLayoutView().setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkClicked(_sparksList.get(position));
        });

        holder.getLikeView().setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkLikeClicked(_sparksList.get(position));
        });

        holder.getOwnerView().setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkOwnerClicked(_sparksList.get(position));
        });

        holder.getDeleteSparkView().setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkDeleteClicked(_sparksList.get(position));
        });
    }

    @Override
    public int getItemCount()
    {
        return _sparksList.size();
    }

    public void setSparks(ArrayList<Spark> sparks)
    {
        _sparksList = sparks;

        notifyDataSetChanged();
    }

    public void emptyElements()
    {
        _sparksList.clear();
        notifyDataSetChanged();
    }

    public void addSpark(Spark spark)
    {
        int index = _sparksList.indexOf(spark);

        if(index == -1) //Only add the spark if it is not already present.
        {
            _sparksList.add(0, spark);
            notifyDataSetChanged();
        }
    }

    public void deleteSpark(Spark spark)
    {
        if(_sparksList.remove(spark))
        {
            notifyDataSetChanged();
        }
    }

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

    public void sparkLikeRemoved(Spark spark)
    {
        sparkChanged(spark);
    }
}