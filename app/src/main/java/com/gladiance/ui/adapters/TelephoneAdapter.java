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
import com.gladiance.ui.models.telephonenumber.ObjectTag;

import java.util.List;

public class TelephoneAdapter extends RecyclerView.Adapter<TelephoneAdapter.MyViewHolder> {

    private static List<ObjectTag> telephoneList;

    public TelephoneAdapter(List<ObjectTag> telephoneList) {
            this.telephoneList = telephoneList;

    }

    @NonNull
    @Override
    public TelephoneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.telephone_recycler, parent, false);
        return new TelephoneAdapter.MyViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull TelephoneAdapter.MyViewHolder holder, int position) {

        ObjectTag objectTag = telephoneList.get(position);
        holder.TextView1.setText(objectTag.getName());
        holder.TextView2.setText(objectTag.getTelephoneNumber());

    }


    @Override
    public int getItemCount() {
        return telephoneList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView Image1, Image2;
        TextView TextView1, TextView2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TextView1 = itemView.findViewById(R.id.tvtitle);
            Image1 = itemView.findViewById(R.id.imgcall);
            TextView2 = itemView.findViewById(R.id.tvnumber);
            Image2 = itemView.findViewById(R.id.imgthim);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}