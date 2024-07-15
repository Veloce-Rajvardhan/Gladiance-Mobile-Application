package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.PlaceOrderItem;

import java.util.List;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.ViewHolder> {

    private List<PlaceOrderItem> orderList;

    public PlaceOrderAdapter(List<PlaceOrderItem> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceOrderItem item = orderList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewRate.setText(item.getRate());
        if (item.isVeg()) {
            holder.imageViewVegNonVeg.setImageResource(R.drawable.vegimg);
        } else {
            holder.imageViewVegNonVeg.setImageResource(R.drawable.nonvegimg);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewRate;
        ImageView imageViewVegNonVeg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvTitleName);
            textViewRate = itemView.findViewById(R.id.tvPriceFM);
            imageViewVegNonVeg = itemView.findViewById(R.id.imgVegNonVegPO);
        }
    }
}