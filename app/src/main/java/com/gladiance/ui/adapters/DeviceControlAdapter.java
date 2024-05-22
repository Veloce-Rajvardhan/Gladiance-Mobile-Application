package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspMainActivity;
import com.gladiance.ui.activities.Home.ProjectSpaceGroupActivity;
import com.gladiance.ui.activities.RoomControl.DeviceCardActivity;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.guestlandingpage.Controls;

import com.gladiance.R;

import java.util.List;

import retrofit2.Call;

public class DeviceControlAdapter extends RecyclerView.Adapter<DeviceControlAdapter.ViewHolder> {
    private List<Controls> controls;
    private Context context;




    public DeviceControlAdapter(List<Controls> controls, Context context) {
        this.controls = controls;
        this.context = context;
    }


    @NonNull
    @Override
    public DeviceControlAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_control, parent, false);
        return new DeviceControlAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull DeviceControlAdapter.ViewHolder holder, int position) {
        Controls control = controls.get(position);
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

                Long GaaProjectSpaceTypePlannedDeviceRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceRef());
                SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor3 = sharedPreferences.edit();
                Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + GaaProjectSpaceTypePlannedDeviceRef);
                editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceRef);
                editor3.apply();

                String Label = control.getLabel();
                SharedPreferences sharedPreferences1 = inflater.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                Log.e(TAG, "Node Id: " + Label);
                editor1.putString("KEY_USERNAMEs", Label);
                editor1.apply();

                String nodeId = control.getNodeId();
                SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                Log.e(TAG, "Node Id: " + nodeId);
                editor.putString("KEY_USERNAMEs", nodeId);
                editor.apply();

                // holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), DeviceCardActivity.class));




                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        DeviceCardFragment newFragment = new DeviceCardFragment();
                        transaction.replace(R.id.DeviceCard, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });
    }



    @Override
    public int getItemCount() {
        return controls.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        LinearLayout llGuestControl;
        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.btnTitle);
            llGuestControl = itemView.findViewById(R.id.llGuestControl);
            deviceNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


}
