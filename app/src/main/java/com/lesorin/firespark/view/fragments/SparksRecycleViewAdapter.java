package com.lesorin.firespark.view.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.MainActivityContract;
import com.lesorin.firespark.view.activities.MainActivity;
import java.util.ArrayList;

public class SparksRecycleViewAdapter extends RecyclerView.Adapter<SparkViewHolder>
{
    private ArrayList<MainActivityContract.Spark> _sparksList;

    public SparksRecycleViewAdapter(ArrayList<MainActivityContract.Spark> sparks)
    {
        _sparksList = sparks;
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
        /*MainActivityContract.Spark spark = _sparksList.get(position);

        holder.setOwnerName(spark._ownerName);
        holder.setSparkBody(spark._text);
        holder.setSparkLiked(spark._likes.contains(spark._ownerId));*/

        holder.getLayout().setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkClicked(_sparksList.get(position));
        });

        holder.getLikeView().setOnClickListener(view ->
        {
            ((MainActivity)view.getContext()).sparkLikeClicked(_sparksList.get(position));
        });
    }

    @Override
    public int getItemCount()
    {
        return _sparksList.size();
    }
}