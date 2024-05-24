package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.activities.MyProfile.EditInvitedUserActivity;

import java.util.List;

public class InvitedUserAdapter extends RecyclerView.Adapter<InvitedUserAdapter.ViewHolder> {

    private List<String> controlTypes; // Your dataset
    private Context context;

    // Constructor to initialize the dataset and context
    public InvitedUserAdapter(List<String> controlTypes, Context context) {
        this.controlTypes = controlTypes;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invited_user, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String controlType = controlTypes.get(position);
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MyActivity when imgView is clicked
                Intent intent = new Intent(context, EditInvitedUserActivity.class);
                context.startActivity(intent);
            }
        });

     //   holder.controlTypeTextView.setText(controlType);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return controlTypes.size();
    }

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView controlTypeTextView;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgView);
          //  controlTypeTextView = itemView.findViewById(R.id.controlTypeTextView); // Replace with your TextView ID
        }
    }
}