package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.favoritelist.ObjectTag;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder> {


    private List<ObjectTag> arraylistFav;
    private Context context;

    public FavoriteListAdapter(ArrayList<ObjectTag> arraylistFav, Context context) {
        this.arraylistFav = arraylistFav;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteListAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_control, parent, false);
        return new FavoriteListAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavoriteViewHolder holder, int position) {
        ObjectTag objectTag = arraylistFav.get(position);

        holder.FavTextView.setText(objectTag.getLabel());

    }

    @Override
    public int getItemCount() {
        return arraylistFav.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder{

        TextView FavTextView;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            FavTextView = itemView.findViewById(R.id.btnTitle);
        }
    }
}
