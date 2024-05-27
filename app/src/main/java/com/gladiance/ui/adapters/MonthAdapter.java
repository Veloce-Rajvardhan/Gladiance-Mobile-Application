package com.gladiance.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> {

    private List<String> months;

    public MonthAdapter(List<String> months) {
        this.months = months;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month, parent, false);
        return new MonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, int position) {
        String month = months.get(position);
        holder.textViewMonth.setText(month);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public static class MonthViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMonth;

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
        }
    }
}
