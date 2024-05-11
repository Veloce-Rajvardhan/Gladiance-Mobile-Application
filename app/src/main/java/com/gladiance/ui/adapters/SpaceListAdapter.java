package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.ui.models.ProjectSpaceRequestModel;
import com.gladiance.ui.models.Space;
import com.gladiance.R;

import java.util.ArrayList;
import java.util.List;

public class SpaceListAdapter extends RecyclerView.Adapter<SpaceListAdapter.ViewHolder> {

    private static List<Space> arraylist;

    public SpaceListAdapter(ArrayList<Space> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.space_name_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Space space = arraylist.get(position);
        holder.spaceNameTextView.setText(space.getGAAProjectSpaceName());
        // You can also bind other data related to the space here if needed
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView spaceNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spaceNameTextView = itemView.findViewById(R.id.spaceName);
        }
    }
}
