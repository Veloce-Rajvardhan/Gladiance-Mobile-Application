package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.ui.activities.Home.ProjectSpaceLandingActivity;
import com.gladiance.ui.models.SpaceGroup;
import com.gladiance.ui.activities.Home.NavBarActivity;
import com.gladiance.R;

import java.util.List;

public class ProjectSpaceGroupListAdapter extends RecyclerView.Adapter<ProjectSpaceGroupListAdapter.ViewHolder> {

    private static List<SpaceGroup> arraylist;

    public ProjectSpaceGroupListAdapter(List<SpaceGroup> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_space_group_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpaceGroup spaceGroup = arraylist.get(position);
        holder.spaceGroupNameTextView.setText(spaceGroup.getGAAProjectSpaceGroupName());
        // You can also bind other data related to the space group here if needed
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView spaceGroupNameTextView;
        LinearLayout llSpaceGroupName;
        ImageView imgSN;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spaceGroupNameTextView = itemView.findViewById(R.id.projectSpaceGroupList);
            llSpaceGroupName = itemView.findViewById(R.id.llSpaceGroupName);
            imgSN = itemView.findViewById(R.id.imgSN);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Context context = view.getContext();

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        SpaceGroup clickedCard = arraylist.get(position);
                        String name = clickedCard.getGAAProjectSpaceGroupRef();
                        String spaceName = clickedCard.getGAAProjectSpaceGroupName();

                        // Storing data in SharedPreferences
                        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefsPSGR", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("SPACE_GROUP_REF", name);
                        editor.apply();

                        SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("MyPrefsPSGN", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("SPACE_GROUP_NAME", spaceName);
                        editor1.apply();

                        llSpaceGroupName.setBackground(getDrawableForTheme(context, R.drawable.new_border_button_background));


                        int textColor = context.getResources().getColor(R.color.TextOrangeColor);
                        if (isNightModeEnabled(context)) {
                            textColor = context.getResources().getColor(R.color.TextOrangeColor);
                        }
                        spaceGroupNameTextView.setTextColor(textColor);

                        // Change image here
                        if (isNightModeEnabled(context)) {
                            // Change to night mode image
                            imgSN.setImageResource(R.drawable.homeorange);
                        } else {
                            // Change to day mode image
                            imgSN.setImageResource(R.drawable.homeorange);
                        }

                        // Start the activity
                        Intent intent = new Intent(context, ProjectSpaceLandingActivity.class);
                        context.startActivity(intent);

                    }
                }
            });
        }
    }

    private boolean isNightModeEnabled(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    private Drawable getDrawableForTheme(Context context, @DrawableRes int drawableResId) {
        if (isNightModeEnabled(context)) {
            // Load night mode drawable
            return ContextCompat.getDrawable(context, R.drawable.new_border_button_background_night);
        } else {
            // Load day mode drawable
            return ContextCompat.getDrawable(context, drawableResId);
        }
    }
}
