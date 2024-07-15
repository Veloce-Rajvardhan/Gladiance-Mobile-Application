package com.gladiance.ui.adapters;

import static com.gladiance.cloudapi.AlexaApiManager.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.R;
import com.gladiance.ui.activities.ControlBouquet.LaundryDetailsActivity;
import com.gladiance.ui.models.laundrylist.ObjectTag;

import java.util.List;

public class LaundryItemListAdapter extends RecyclerView.Adapter<LaundryItemListAdapter.ViewHolder> {

    private List<ObjectTag> arraylist;

    public LaundryItemListAdapter(List<ObjectTag> arraylist) {
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laundry_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectTag laundryRes = arraylist.get(position);
        holder.tvRequestNo.setText(laundryRes.getDocNo());
        holder.tvRequestedOnDateTime.setText(laundryRes.getRequestedAtDateTime());
        holder.tvStatus.setText(laundryRes.getRequestStatusName());

        holder.tvRequestNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                String DocNo = laundryRes.getDocNo();
                SharedPreferences sharedPreferencesDocNo = inflater.getContext().getSharedPreferences("my_shared_prefe_Doc_No", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferencesDocNo.edit();
                Log.e(TAG, "Doc No: " + DocNo);
                editor.putString("KEY_DOC_No", DocNo);
                editor.apply();

                String DateTime = laundryRes.getRequestedAtDateTime();
                SharedPreferences sharedPreferencesDate = inflater.getContext().getSharedPreferences("my_shared_prefe_Date", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesDate.edit();
                Log.e(TAG, "Date : " + DateTime);
                editor1.putString("KEY_DATE", DateTime);
                editor1.apply();

                Long Ref = laundryRes.getRef();
                SharedPreferences sharedPreferencesRef = inflater.getContext().getSharedPreferences("my_shared_prefe_Ref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferencesRef.edit();
                Log.e(TAG, "Date : " + Ref);
                editor2.putLong("KEY_REF", Ref);
                editor2.apply();

                Intent intent = new Intent(context, LaundryDetailsActivity.class);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRequestNo;
        public TextView tvRequestedOnDateTime;
        public TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRequestNo = itemView.findViewById(R.id.tv_request_no);
            tvRequestedOnDateTime = itemView.findViewById(R.id.tv_requested_on_date_time);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
