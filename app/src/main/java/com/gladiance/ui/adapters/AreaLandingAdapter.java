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
                        String typeRef = String.valueOf(clickedCard.getGAAProjectSpaceTypeAreaRef());


                        SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("MyPrefsPSAR", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("Project_Space_Area_Ref", typeRef);
                        editor1.apply();


                        AreaNameTextView.setBackground(getDrawableForTheme(view.getContext(), R.drawable.transparent_button_background));
                        boolean isNightModeEnabled = true;
                        AreaNameTextView.setBackgroundResource(isNightModeEnabled ? R.drawable.white_button_bg_round : R.drawable.black_button_bg_round);
                        AreaNameTextView.setTextColor(ContextCompat.getColor(context, isNightModeEnabled ? R.color.color_black : R.color.white));

                        // Change text color of clicked text view based on theme
                        int textColor = view.getContext().getResources().getColor(R.color.white);
                        if (isNightModeEnabled(view.getContext())) {
                            textColor = view.getContext().getResources().getColor(R.color.color_black);
                        }
                        AreaNameTextView.setTextColor(textColor);


                        Fragment newFragment = new DeviceLandingFragment(); // Replace YourNewFragment with the fragment you want to navigate to
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

