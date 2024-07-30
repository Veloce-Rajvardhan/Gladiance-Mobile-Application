package com.gladiance.ui.adapters;

import android.util.Log;
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

    private static final String TAG = "PlaceOrderAdapter";
    private List<PlaceOrderItem> orderList;
    private TotalSumListener totalSumListener;

    public interface TotalSumListener {
        void onTotalSumUpdated(double totalSum);
    }

    public PlaceOrderAdapter(List<PlaceOrderItem> orderList, TotalSumListener totalSumListener) {
        this.orderList = orderList;
        this.totalSumListener = totalSumListener;
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
        holder.textViewQuantity.setText(item.getQuantity());
        if (item.isVeg()) {
            holder.imageViewVegNonVeg.setImageResource(R.drawable.vegimg);
        } else {
            holder.imageViewVegNonVeg.setImageResource(R.drawable.nonvegimg);
        }

        // Calculate the unit price from the total price and quantity
        double totalRate = Double.parseDouble(item.getRate());
        int quantity = Integer.parseInt(item.getQuantity());
        double unitRate = totalRate / quantity;

        // Set the click listeners for increment and decrement buttons
        holder.incrementButton.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.textViewQuantity.getText().toString());
            currentQuantity++;
            holder.textViewQuantity.setText(String.valueOf(currentQuantity));

            // Update the total rate based on the new quantity
            double newTotalRate = unitRate * currentQuantity;
            holder.textViewRate.setText(String.valueOf(newTotalRate));

            // Update the item in the list
            item.setQuantity(String.valueOf(currentQuantity));
            item.setRate(String.valueOf(newTotalRate));

            // Log the total sum of all tvPriceFM values
            logTotalSum();
        });

        holder.decrementButton.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.textViewQuantity.getText().toString());
            if (currentQuantity > 0) {  // Ensuring the quantity doesn't go below 1
                currentQuantity--;
                holder.textViewQuantity.setText(String.valueOf(currentQuantity));

                // Update the total rate based on the new quantity
                double newTotalRate = unitRate * currentQuantity;
                holder.textViewRate.setText(String.valueOf(newTotalRate));

                // Update the item in the list
                item.setQuantity(String.valueOf(currentQuantity));
                item.setRate(String.valueOf(newTotalRate));

                // Log the total sum of all tvPriceFM values
                logTotalSum();
            }
        });

        holder.imgDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    private void logTotalSum() {
        double totalSum = 0;
        for (PlaceOrderItem item : orderList) {
            totalSum += Double.parseDouble(item.getRate());
        }
        Log.d(TAG, "Total Sum of all prices: " + totalSum);
        if (totalSumListener != null) {
            totalSumListener.onTotalSumUpdated(totalSum);
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewRate, textViewQuantity;
        ImageView imageViewVegNonVeg, incrementButton, decrementButton,imgDeleteItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvTitleName);
            textViewRate = itemView.findViewById(R.id.tvPriceFM);
            imageViewVegNonVeg = itemView.findViewById(R.id.imgVegNonVegPO);
            textViewQuantity = itemView.findViewById(R.id.quantityText);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButton = itemView.findViewById(R.id.decrementButton);
            imgDeleteItem = itemView.findViewById(R.id.imgDeleteItem);

        }
    }

    public void removeItem(int position) {
        if (position < 0 || position >= orderList.size()) {
            return;
        }
        orderList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderList.size());
    }
}
