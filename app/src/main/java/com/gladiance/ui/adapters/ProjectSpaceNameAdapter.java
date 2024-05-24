package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.Intent;
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

import com.gladiance.ui.activities.Home.NavBarActivity;
import com.gladiance.ui.activities.Home.ProjectSpaceLandingActivity;
import com.gladiance.ui.models.SpaceLanding;
import com.gladiance.ui.fragment.RoomControl.AreaLandingFragment;
import com.gladiance.R;

import java.util.List;

import javax.xml.namespace.NamespaceContext;

public class ProjectSpaceNameAdapter extends RecyclerView.Adapter<ProjectSpaceNameAdapter.ViewHolder> {


    private static List<SpaceLanding> arraylist;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public ProjectSpaceNameAdapter(List<SpaceLanding> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_space_name_recycleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpaceLanding space = arraylist.get(position);
        holder.spaceNameTextView.setText(space.getGAAProjectSpaceName());
        // You can also bind other data related to the space group here if needed


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView spaceNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spaceNameTextView = itemView.findViewById(R.id.spaceName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Context context = view.getContext();
                    if (position != RecyclerView.NO_POSITION) {
                        SpaceLanding clickedCard = arraylist.get(position);
                        String name = clickedCard.getGAAProjectSpaceRef();
                        String typeRef = clickedCard.getGAAProjectSpaceTypeRef();
                        String spaceName = clickedCard.getGAAProjectSpaceName();

                        // Storing data in SharedPreferences
                        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("MyPrefsPSR", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Project_Space_Ref", name);
                        editor.apply();

                        SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("MyPrefsPSTR", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("Project_Space_Type_Ref", typeRef);
                        editor1.apply();

                        SharedPreferences sharedPreferences2 = view.getContext().getSharedPreferences("MyPrefsPSN", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                        editor2.putString("Project_Space_Name", spaceName);
                        editor2.apply();


                        Intent intent = new Intent(context, NavBarActivity.class);
                        context.startActivity(intent);


                    }
                }
            });
        }
    }

}