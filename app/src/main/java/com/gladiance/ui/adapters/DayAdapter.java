package com.gladiance.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<String> days;

    public DayAdapter(List<String> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String day = days.get(position);
        holder.textViewDay.setText(day);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
        }
    }
}