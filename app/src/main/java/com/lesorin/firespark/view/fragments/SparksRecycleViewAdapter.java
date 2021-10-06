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
import java.util.Locale;

public class SparksRecycleViewAdapter extends RecyclerView.Adapter<SparkViewHolder>
{
    private final String DATE_FORMAT = "d MMM yyyy\nHH:mm";
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

        //todo add @ handle below first and last name
        holder.setOwnerName(spark.getOwnerFirstLastName());
        holder.setSparkBody(spark.getBody());
        holder.setSparkLiked(spark.getLikes().contains(spark.getOwnerId()));
        holder.setDeleteButtonVisibility(spark.isOwnedByCurrentUser());
        holder.setLikes(spark.getLikes().size() + " Likes");
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
        _sparksList.add(0, spark);
        notifyDataSetChanged();
    }
}