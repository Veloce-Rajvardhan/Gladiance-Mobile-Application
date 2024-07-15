package com.gladiance.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.roomservicesingleitemlist.LineItem;

import java.util.List;

public class RoomServiceSingleItemAdapter extends RecyclerView.Adapter<RoomServiceSingleItemAdapter.ViewHolder> {

    private List<LineItem> lineItemList;

    public RoomServiceSingleItemAdapter(List<LineItem> lineItemList) {
        this.lineItemList = lineItemList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_room_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LineItem lineItem = lineItemList.get(position);
        holder.tvItem.setText(lineItem.getrBItemName());
        holder.tvRequestNo.setText(String.valueOf(lineItem.getRate()));
        holder.tvQuantity.setText(String.valueOf(lineItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return lineItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvRequestNo,tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
            tvRequestNo = itemView.findViewById(R.id.tv_amount_item);
            tvQuantity = itemView.findViewById(R.id.tv_Quantity_item);
        }
    }
}
