package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import android.util.Log;
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
    private boolean[] selectionState;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String day, boolean isChecked);
    }

    public DayAdapter(List<String> days, OnItemClickListener listener) {
        this.days = days;
        this.listener = listener;
        this.selectionState = new boolean[days.size()];
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
        holder.textViewDay.setSelected(selectionState[position]);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    boolean isChecked = !selectionState[position];
                    selectionState[position] = isChecked;
                    textViewDay.setSelected(isChecked);
                    listener.onItemClick(days.get(position), isChecked);
                }
            });
        }
    }
}