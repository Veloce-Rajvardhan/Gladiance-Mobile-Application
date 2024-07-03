package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.amenities.ObjectTag;

import java.util.List;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    private List<ObjectTag> arraylistAmenities;

    public AmenitiesAdapter(List<ObjectTag> arraylistAmenities) {
        this.arraylistAmenities = arraylistAmenities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenities_recycler, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ObjectTag objectTag = arraylistAmenities.get(position);
        holder.TextView1.setText(objectTag.getName());
        holder.TextView5.setText(objectTag.getFromTime());
        holder.TextView6.setText(objectTag.getToTime());
        holder.TextView3.setText(objectTag.getVideoUrl());
        holder.TextView4.setText(objectTag.getDescription());

        // Ensure that description is initially collapsed
        holder.TextView4.setMaxLines(3);
        holder.SeeMore.setText("See More...");

        holder.SeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSeeMore(holder.TextView4, holder.SeeMore);
            }
        });

        holder.TextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(v.getContext(), objectTag.getVideoUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arraylistAmenities.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TextView1, TextView3, TextView4, TextView5, TextView6;
        Button SeeMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView1 = itemView.findViewById(R.id.tvTitle);
            TextView3 = itemView.findViewById(R.id.tvVideoUrl);
            TextView4 = itemView.findViewById(R.id.tvDescription);
            TextView5 = itemView.findViewById(R.id.tvTimingFrom);
            TextView6 = itemView.findViewById(R.id.tvTimingTo);
            SeeMore = itemView.findViewById(R.id.seeMoreAmi);
        }
    }

    private static void toggleSeeMore(TextView textView, Button button) {
        if (textView.getMaxLines() == 3) {
            // Expand the text
            textView.setMaxLines(Integer.MAX_VALUE);
            button.setText("See Less");
        } else {
            // Collapse the text
            textView.setMaxLines(3);
            button.setText("See More...");
        }
    }

    private void openUrl(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}
