package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.fragment.RoomControl.AreaLandingFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceLandingFragment;
import com.gladiance.ui.models.SpaceLanding;
import com.gladiance.ui.models.arealandingmodel.Area;

import java.util.List;

public class AreaLandingAdapter extends RecyclerView.Adapter<AreaLandingAdapter.ViewHolder> {

    private static List<Area> arraylist;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private Context context;

    public AreaLandingAdapter(List<Area> arraylist,Context context) {
        this.arraylist = arraylist;
        this.context = context;

    }

    @NonNull
    @Override
    public AreaLandingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_card, parent, false);
        return new AreaLandingAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AreaLandingAdapter.ViewHolder holder, int position) {
        Area area = arraylist.get(position);
        holder.AreaNameTextView.setText(area.getGAAProjectSpaceTypeAreaName());

        if (arraylist.size() == 1) {
            applySelectedState(holder.AreaNameTextView);
            selectedPosition = position;
        } else {
            if (selectedPosition == RecyclerView.NO_POSITION) {
                if (position == 0) {
                    applySelectedState(holder.AreaNameTextView);
                    selectedPosition = position;
                } else {
                    applyDefaultState(holder.AreaNameTextView);
                }
            } else {
                if (selectedPosition == position) {
                    applySelectedState(holder.AreaNameTextView);
                } else {
                    applyDefaultState(holder.AreaNameTextView);
                }
            }
        }
    }

    // Helper method to apply the selected state to the TextView
    private void applySelectedState(TextView textView) {
        boolean isNightModeEnabled = isNightModeEnabled(context);
        textView.setBackgroundResource(isNightModeEnabled ? R.drawable.white_button_bg_round : R.drawable.black_button_bg_round);
        textView.setTextColor(ContextCompat.getColor(context, isNightModeEnabled ? R.color.color_black : R.color.white));
    }

    // Helper method to apply the default state to the TextView
    private void applyDefaultState(TextView textView) {
        boolean isNightModeEnabled = isNightModeEnabled(context);
        textView.setBackgroundResource(isNightModeEnabled ? R.drawable.black_button_bg_round : R.drawable.white_button_bg_round);
        textView.setTextColor(ContextCompat.getColor(context, isNightModeEnabled ? R.color.white : R.color.color_black));
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView AreaNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AreaNameTextView = itemView.findViewById(R.id.btnAreaName);

            AreaNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Area clickedCard = arraylist.get(position);
                        Long typeRef = Long.valueOf(clickedCard.getGAAProjectSpaceTypeAreaRef());

                        // Save the reference to SharedPreferences
                        SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("MyPrefsPSAR", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putLong("Project_Space_Area_Ref", typeRef);
                        editor1.apply();


                        // Notify the adapter to update the selected state
                        notifyItemChanged(selectedPosition);
                        selectedPosition = position;
                        notifyItemChanged(selectedPosition);

                        // Navigate to the DeviceLandingFragment
                        Fragment newFragment = new DeviceLandingFragment();
                        FragmentTransaction fragmentTransaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            });
        }
    }

    // Function to check if night mode is enabled
    private boolean isNightModeEnabled(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    // Function to get drawable for current theme
    private Drawable getDrawableForTheme(Context context, @DrawableRes int drawableResId) {
        if (isNightModeEnabled(context)) {
            // Load night mode drawable
            return ContextCompat.getDrawable(context, R.drawable.transparent_button_background);
        } else {
            // Load day mode drawable
            return ContextCompat.getDrawable(context, drawableResId);
        }
    }

}

