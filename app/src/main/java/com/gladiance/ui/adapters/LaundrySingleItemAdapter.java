package com.gladiance.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.laundrysinglelist.LineItem;


import java.util.List;

public class LaundrySingleItemAdapter extends RecyclerView.Adapter<LaundrySingleItemAdapter.ViewHolder> {

    private List<LineItem> lineItems;

    public LaundrySingleItemAdapter(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_laundry_card_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LineItem item = lineItems.get(position);
        holder.tvItem.setText(item.getCustomerLaundryItemName());
        holder.tvName.setText(item.getLaundryServiceTypeName());
        holder.tvAmount.setText(String.valueOf(item.getRate()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return lineItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvName, tvAmount,tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvQuantity = itemView.findViewById(R.id.tv_Quantity);

        }
    }
}
