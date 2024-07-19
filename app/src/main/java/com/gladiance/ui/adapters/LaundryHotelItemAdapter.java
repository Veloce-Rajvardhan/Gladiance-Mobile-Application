package com.gladiance.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.laundryItemHotel.ObjectTag;

import java.util.List;

public class LaundryHotelItemAdapter extends RecyclerView.Adapter<LaundryHotelItemAdapter.LaundryHotelItemViewHolder> {
    private List<ObjectTag> laundryItems;
    private OnTotalCostChangedListener totalCostChangedListener;

    public LaundryHotelItemAdapter(List<ObjectTag> laundryItems, OnTotalCostChangedListener listener) {
        this.laundryItems = laundryItems;
        this.totalCostChangedListener = listener;
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

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                item.setSelected(true);
                item.setSelectedPress(false);
            } else {
                item.setSelected(false);
            }
            notifyItemChanged(position);
            holder.decrementButton.setEnabled(isChecked);
            holder.incrementButton.setEnabled(isChecked);
            holder.scoreText.setEnabled(isChecked);
            if (!isChecked) {
                item.setDryWashQuantity(0);
                holder.scoreText.setText(String.valueOf(0));
            }
            printTotalCost();
        });

        holder.checkBoxPress.setOnCheckedChangeListener(null);
        holder.checkBoxPress.setChecked(item.isSelectedPress());
        holder.checkBoxPress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                item.setSelectedPress(true);
                item.setSelected(false);
            } else {
                item.setSelectedPress(false);
            }
            notifyItemChanged(position);
            holder.decrementButtonPress.setEnabled(isChecked);
            holder.incrementButtonPress.setEnabled(isChecked);
            holder.scoreTextPress.setEnabled(isChecked);
            if (!isChecked) {
                item.setPressQuantity(0);
                holder.scoreTextPress.setText(String.valueOf(0));
            }
            printTotalCost();
        });

        holder.tvName.setText(item.getName());
        holder.tvPressCharges.setText(String.valueOf(item.getPressCharges()));
        holder.tvDryWashCharges.setText(String.valueOf(item.getDryWashCharges()));

        holder.scoreText.setText(String.valueOf(item.getDryWashQuantity()));
        holder.scoreTextPress.setText(String.valueOf(item.getPressQuantity()));

        holder.decrementButton.setEnabled(item.isSelected());
        holder.incrementButton.setEnabled(item.isSelected());
        holder.decrementButtonPress.setEnabled(item.isSelectedPress());
        holder.incrementButtonPress.setEnabled(item.isSelectedPress());

        holder.decrementButton.setOnClickListener(v -> {
            int currentScore = item.getDryWashQuantity();
            if (currentScore > 0) {
                item.setDryWashQuantity(currentScore - 1);
                holder.scoreText.setText(String.valueOf(currentScore - 1));
                printTotalCost();
            }
        });

        holder.incrementButton.setOnClickListener(v -> {
            int currentScore = item.getDryWashQuantity();
            item.setDryWashQuantity(currentScore + 1);
            holder.scoreText.setText(String.valueOf(currentScore + 1));
            printTotalCost();
        });

        holder.decrementButtonPress.setOnClickListener(v -> {
            int currentScore = item.getPressQuantity();
            if (currentScore > 0) {
                item.setPressQuantity(currentScore - 1);
                holder.scoreTextPress.setText(String.valueOf(currentScore - 1));
                printTotalCost();
            }
        });

        holder.incrementButtonPress.setOnClickListener(v -> {
            int currentScore = item.getPressQuantity();
            item.setPressQuantity(currentScore + 1);
            holder.scoreTextPress.setText(String.valueOf(currentScore + 1));
            printTotalCost();
        });

        holder.removeTextView.setOnClickListener(v -> removeItem(position));
    }

    @Override
    public int getItemCount() {
        return laundryItems.size();
    }

    private void printTotalCost() {
        int totalDryWashCost = 0;
        int totalPressCost = 0;

        for (ObjectTag item : laundryItems) {
            if (item.isSelected()) {
                totalDryWashCost += item.getDryWashCharges() * item.getDryWashQuantity();
            }
            if (item.isSelectedPress()) {
                totalPressCost += item.getPressCharges() * item.getPressQuantity();
            }
        }

        int totalCost = totalDryWashCost + totalPressCost;
        totalCostChangedListener.onTotalCostChanged(totalCost);
    }

    static class LaundryHotelItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPressCharges, tvDryWashCharges, removeTextView;
        ImageView decrementButton, incrementButton, decrementButtonPress, incrementButtonPress;
        TextView scoreText, scoreTextPress;
        RadioButton checkBox, checkBoxPress;

        LaundryHotelItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPressCharges = itemView.findViewById(R.id.tv_amount_press);
            tvDryWashCharges = itemView.findViewById(R.id.tv_amount_drywash);

            decrementButton = itemView.findViewById(R.id.decrementButton);
            incrementButton = itemView.findViewById(R.id.incrementButton);
            decrementButtonPress = itemView.findViewById(R.id.decrementButtonPress);
            incrementButtonPress = itemView.findViewById(R.id.incrementButtonPress);

            scoreText = itemView.findViewById(R.id.scoreText);
            scoreTextPress = itemView.findViewById(R.id.scoreTextPress);

            checkBox = itemView.findViewById(R.id.checkBox);
            checkBoxPress = itemView.findViewById(R.id.checkBoxPress);

            removeTextView = itemView.findViewById(R.id.tv_item_remove);
        }
    }

    public interface OnTotalCostChangedListener {
        void onTotalCostChanged(int totalCost);
    }

    public void removeItem(int position) {
        if (position < 0 || position >= laundryItems.size()) {
            return;
        }
        laundryItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, laundryItems.size());
        printTotalCost();
    }
}
