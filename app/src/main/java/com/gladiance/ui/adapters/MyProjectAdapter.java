package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.Project;

import java.util.ArrayList;
import java.util.List;

public class MyProjectAdapter extends RecyclerView.Adapter<MyProjectAdapter.MyViewHolder> {

    private static List<Project> arrayList;

    public MyProjectAdapter(ArrayList<Project> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyProjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myproject_recyleview, parent, false);
        return new MyProjectAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyProjectAdapter.MyViewHolder holder, int position) {

        Project project = arrayList.get(position);
        holder.tvProjectName.setText(project.getGAAProjectName());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvProjectName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tvProjectName);



        }
    }
}
