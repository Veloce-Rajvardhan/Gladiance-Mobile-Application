package com.gladiance.ui.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;

import java.util.ArrayList;
import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> {

    private List<String> months;
    private boolean[] selectionState;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String month, boolean isChecked);
    }

    public MonthAdapter(List<String> months, OnItemClickListener listener) {
        this.months = months;
        this.listener = listener;
        this.selectionState = new boolean[months.size()];
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
        holder.textViewMonth.setSelected(selectionState[position]);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class MonthViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMonth;

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    boolean isChecked = !selectionState[position];
                    selectionState[position] = isChecked;
                    textViewMonth.setSelected(isChecked);
                    int textColor = isChecked ? ContextCompat.getColor(itemView.getContext(), R.color.link_color) : ContextCompat.getColor(itemView.getContext(), R.color.white);
                    textViewMonth.setTextColor(textColor);

                    listener.onItemClick(months.get(position), isChecked);
                }
            });
        }
    }
    public List<Pair<String, Boolean>> getAllDataWithCheckedStatus() {
        List<Pair<String, Boolean>> dataList = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            dataList.add(new Pair<>(months.get(i), selectionState[i]));
        }
        return dataList;
    }
}
