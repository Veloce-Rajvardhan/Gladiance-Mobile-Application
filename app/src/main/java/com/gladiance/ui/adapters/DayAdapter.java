package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.util.Log;
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

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private List<String> days;
    private boolean[] selectionState;
    private OnItemClickListener listener;
    RecyclerView recyclerViewDay;

    SharedPreferences sharedPreferences;
    public interface OnItemClickListener {
        void onItemClick(String day, boolean isChecked);
    }

    public DayAdapter(List<String> days, OnItemClickListener listener) {
        this.days = days;
        this.listener = listener;
        this.selectionState = new boolean[days.size()];
    }

    public void updateCheckedStatus(List<Boolean> checkedStatus, List<String> days) {
        for (int i = 0; i < checkedStatus.size(); i++) {
            selectionState[i] = checkedStatus.get(i);
            String day = days.get(i);
          //  days.set(i, String.valueOf(checkedStatus.get(i)));

            if (day.equals("Sunday")) {
                // If it's Sunday, set its value in checkedStatus
                checkedStatus.set(i, true);
            }
            if (day.equals("Monday")) {
                checkedStatus.set(i, true);
            }
            if (day.equals("Tuesday")) {
                checkedStatus.set(i, true);
            }
            if (day.equals("Wednesday")) {
                checkedStatus.set(i, true);
            }
            if (day.equals("Thursday")) {
                checkedStatus.set(i, true);
            }
            if (day.equals("Friday")) {
                checkedStatus.set(i, true);
            }
            if (day.equals("Saturday")) {
                checkedStatus.set(i, true);
            }


//            String day = days.get(i);
//            boolean isChecked = checkedStatus.get(i);
//
//            // Associate the day with its checked status
//            dayCheckedStatusMap.put(day, isChecked);

        }
        notifyDataSetChanged();
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

        // Set text color based on selection state
        int textColor = selectionState[position] ? ContextCompat.getColor(holder.itemView.getContext(), R.color.link_color) : ContextCompat.getColor(holder.itemView.getContext(), R.color.white);
        holder.textViewDay.setTextColor(textColor);

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
                    int textColor = isChecked ? ContextCompat.getColor(itemView.getContext(), R.color.link_color) : ContextCompat.getColor(itemView.getContext(), R.color.white);
                    textViewDay.setTextColor(textColor);


                    // Save selection state and color to SharedPreferences
                    try {
                        saveSelectionStateToSharedPreferences(position, isChecked, textColor);
                    }catch (Exception e){

                    }
                    listener.onItemClick(days.get(position), isChecked);
                }

                private void saveSelectionStateToSharedPreferences(int position, boolean isChecked, int textColor) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("selection_" + position, isChecked);
                    editor.putInt("text_color_" + position, textColor);
                    editor.apply();
                }
            });
        }
    }
    public List<Pair<String, Boolean>> getAllDataWithCheckedStatus() {
        List<Pair<String, Boolean>> dataList = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            dataList.add(new Pair<>(days.get(i), selectionState[i]));
        }
        return dataList;
    }
}