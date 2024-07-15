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
import com.gladiance.ui.activities.ControlBouquet.RoomServiceDetailsActivity;
import com.gladiance.ui.models.roomservicelist.ObjectTag;

import java.util.List;

public class RoomServiceAdapter extends RecyclerView.Adapter<RoomServiceAdapter.ViewHolder> {

    private List<ObjectTag> diningRequestList;

    public RoomServiceAdapter(List<ObjectTag> diningRequestList) {
        this.diningRequestList = diningRequestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roomservice_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectTag request = diningRequestList.get(position);
        holder.tvRequestNo.setText(request.getDocNo());
        holder.tvRequestedOnDate.setText(request.getRequestedAtDateTime());
        holder.tvStatus.setText(request.getRequestStatusName());

        holder.tvRequestNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                String DocNo = request.getDocNo();
                SharedPreferences sharedPreferencesDocNo = inflater.getContext().getSharedPreferences("my_shared_prefe_Doc_No", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferencesDocNo.edit();
                Log.e(TAG, "Doc No: " + DocNo);
                editor.putString("KEY_DOC_No", DocNo);
                editor.apply();

                String DateTime = request.getRequestedAtDateTime();
                SharedPreferences sharedPreferencesDate = inflater.getContext().getSharedPreferences("my_shared_prefe_Date", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferencesDate.edit();
                Log.e(TAG, "Date : " + DateTime);
                editor1.putString("KEY_DATE", DateTime);
                editor1.apply();

                Long Ref = request.getRef();
                SharedPreferences sharedPreferencesRef = inflater.getContext().getSharedPreferences("my_shared_prefe_Ref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferencesRef.edit();
                Log.e(TAG, "Date : " + Ref);
                editor2.putLong("KEY_REF", Ref);
                editor2.apply();

                Intent intent = new Intent(context, RoomServiceDetailsActivity.class);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return diningRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestNo, tvRequestedOnDate, tvRequestedOnTime, tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestNo = itemView.findViewById(R.id.tv_request_no);
            tvRequestedOnDate = itemView.findViewById(R.id.tv_requested_on_datetime);
            tvStatus = itemView.findViewById(R.id.tv_status_roomService);
        }
    }
}
