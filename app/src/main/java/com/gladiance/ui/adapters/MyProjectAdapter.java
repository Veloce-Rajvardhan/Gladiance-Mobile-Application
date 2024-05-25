package com.gladiance.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.activities.DeviceControls.FanActivity;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.BasicInfoActivity;
import com.gladiance.ui.activities.MyProfile.EditProjectActivity;
import com.gladiance.ui.models.Devices;
import com.gladiance.ui.models.Project;

import java.util.ArrayList;
import java.util.List;

public class MyProjectAdapter extends RecyclerView.Adapter<MyProjectAdapter.MyViewHolder> {

    private static List<Project> arrayList;
    private Context context;


    public MyProjectAdapter(ArrayList<Project> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context=context;
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

        ImageView imageViewEdit,imageViewDelete;
        TextView tvProjectName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tvProjectName);
            imageViewEdit = itemView.findViewById(R.id.imgEdit);
            imageViewDelete = itemView.findViewById(R.id.imgDelete);

            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Project clickedCard = arrayList.get(position);
                        String name = clickedCard.getGAAProjectName();

                        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefsProjectName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ProjectName", name);
                        editor.apply();

                        Context context = v.getContext();
                        Intent intent = new Intent(context, EditProjectActivity.class);
                        context.startActivity(intent);
                    }
                }
            });

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "Are you sure you want to delete?";
                    showCustomDialogBox(v.getContext(), message);
                }

            });
        }
    }
    private void showCustomDialogBox(Context context, String message) {
        if (context == null) {
            throw new IllegalStateException("Context is null. Ensure context is passed correctly to the adapter.");
        }

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_custom_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        Button btnYes = dialog.findViewById(R.id.btn_Yes);
        Button btnNo = dialog.findViewById(R.id.btn_No);

        tvMessage.setText(message);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}



