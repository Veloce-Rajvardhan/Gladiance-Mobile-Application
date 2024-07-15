package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.laundryItemHotel.ObjectTag;


import java.util.List;

public class LaundryHotelItemAdapter extends RecyclerView.Adapter<LaundryHotelItemAdapter.LaundryHotelItemViewHolder> {
    private List<ObjectTag> laundryItems;


    public LaundryHotelItemAdapter(List<ObjectTag> laundryItems) {
        this.laundryItems = laundryItems;
    }

    @NonNull
    @Override
    public LaundryHotelItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_laundry_card, parent, false);
        return new LaundryHotelItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaundryHotelItemViewHolder holder, int position) {
        ObjectTag item = laundryItems.get(position);
        holder.tvName.setText(item.getName());
        holder.tvPressCharges.setText(String.valueOf(item.getPressCharges()));
        holder.tvDryWashCharges.setText(String.valueOf(item.getDryWashCharges()));
    }

    @Override
    public int getItemCount() {
        return laundryItems.size();
    }

    static class LaundryHotelItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPressCharges, tvDryWashCharges;

        LaundryHotelItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPressCharges = itemView.findViewById(R.id.tv_amount_press);
            tvDryWashCharges = itemView.findViewById(R.id.tv_amount_drywash);
        }
    }
}

