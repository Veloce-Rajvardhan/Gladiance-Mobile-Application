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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        // Correct pattern based on the example "2024-07-18-18-19-19-865"
        String dateTimeString = laundryRes.getRequestedAtDateTime();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        String formattedDate = "";
        try {
            Date date = originalFormat.parse(dateTimeString);
            formattedDate = dateFormat.format(date);
            Log.d(TAG, "Parsed date-time: " + date.toString());
            Log.d(TAG, "Formatted date: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "Date parsing error: " + e.getMessage());
        }

        holder.tvRequestedOnDateTime.setText(formattedDate);

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

                String dateTime = laundryRes.getRequestedAtDateTime();
                SharedPreferences sharedPreferencesDate = inflater.getContext().getSharedPreferences("my_shared_prefe_Date", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesDate.edit();

                try {
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.getDefault());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = originalFormat.parse(dateTime);
                    String formattedDate = dateFormat.format(date);
                    Log.e(TAG, "Formatted Date: " + formattedDate);
                    editor1.putString("KEY_DATE", formattedDate);
                    editor1.apply();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Failed to parse date: " + dateTime);
                }

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
