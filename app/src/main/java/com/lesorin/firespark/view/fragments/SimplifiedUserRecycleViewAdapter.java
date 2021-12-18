package com.lesorin.firespark.view.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lesorin.firespark.R;
import com.lesorin.firespark.presenter.User;
import com.lesorin.firespark.view.activities.MainActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * RecyclerView adapter for @{@link SimplifiedUserViewHolder}.
 */
public class SimplifiedUserRecycleViewAdapter extends RecyclerView.Adapter<SimplifiedUserViewHolder>
{
    private final String DATE_FORMAT = "d MMM yyyy";
    private ArrayList<User> _usersList;
    private SimpleDateFormat _dateFormat;
    private Context _context;

    /**
     * Instantiates a new @{@link SimplifiedUserRecycleViewAdapter}.
     */
    public SimplifiedUserRecycleViewAdapter(Context context)
    {
        _usersList = new ArrayList<>();
        _context = context;
        _dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    }

    @NonNull
    @Override
    public SimplifiedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simplified_user, parent, false);

        return new SimplifiedUserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimplifiedUserViewHolder holder, int position)
    {
        User user = _usersList.get(position);

        holder.setName(user.getFirstLastName());
        holder.setUsername(String.format(_context.getString(R.string.UserHandle), user.getUsername()));
        holder.setJoined(String.format(_context.getString(R.string.UserJoined), _dateFormat.format(user.getJoined())));
        holder.setOriginal(user.isOriginal());
        holder.getLayoutView().setOnClickListener(view -> ((MainActivity)view.getContext()).simplifiedUserClicked(user));
    }

    @Override
    public int getItemCount()
    {
        return _usersList.size();
    }

    /**
     * Sets users to the adapter.
     *
     * @param users Users list to set.
     */
    public void setUsers(ArrayList<User> users)
    {
        _usersList = users;
    }

    public void displayData()
    {
        notifyDataSetChanged();
    }
}