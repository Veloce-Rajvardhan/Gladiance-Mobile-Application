package com.gladiance.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.models.keycontacts.ObjectTag;

import java.util.List;

public class KeyContactAdapter extends RecyclerView.Adapter<KeyContactAdapter.ViewHolder> {

    private static List<ObjectTag> keyContactList;
    private Context context;

    public KeyContactAdapter(Context context, List<ObjectTag> keyContactList) {
        this.context = context;
        this.keyContactList = keyContactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.key_contact_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectTag objectTag = keyContactList.get(position);
        holder.tvTitle.setText(objectTag.getName());
        holder.tvNumber.setText(objectTag.getTelephoneNumber());

        holder.imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KeyContactAdapter", "tvGoogleMap clicked");
                String coordinates = objectTag.getGoogleLocation();
                openMap(coordinates);
            }
        });

        holder.tvGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KeyContactAdapter", "tvGoogleMap clicked");
                String coordinates = objectTag.getGoogleLocation();
                openMap(coordinates);
            }
        });


    }

    @Override
    public int getItemCount() {
        return keyContactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvNumber, tvGoogleMap;
        ImageView imgCall, imgMap, imgThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvtitle2);
            tvNumber = itemView.findViewById(R.id.tvnumber2);
            imgCall = itemView.findViewById(R.id.imgcall2);
            imgMap = itemView.findViewById(R.id.imgMapLocation);
            imgThumb = itemView.findViewById(R.id.imgthim2);
            tvGoogleMap = itemView.findViewById(R.id.tvGoogleMap);


        }
    }

    private void openMap(String coordinates) {
        try {
            Uri gmmIntentUri = Uri.parse("geo:" + coordinates + "?q=" + coordinates);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            } else {
                Log.d("KeyContactAdapter", "No app found to handle map intent, falling back to web browser");
                openMapInBrowser(coordinates);
            }
        } catch (Exception e) {
            Log.e("KeyContactAdapter", "Error opening map intent", e);
        }
    }

    private void openMapInBrowser(String coordinates) {
        try {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + coordinates);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(browserIntent);
        } catch (Exception e) {
            Log.e("KeyContactAdapter", "Error opening map in browser", e);
        }
    }
}
