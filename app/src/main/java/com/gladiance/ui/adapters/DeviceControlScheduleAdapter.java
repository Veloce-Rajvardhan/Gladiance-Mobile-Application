package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import static com.gladiance.AppConstants.MY_CONSTANT_LIST;
import static com.gladiance.AppConstants.MY_CONSTANT_LIST_SCHEDULE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.AppConstants;
import com.gladiance.R;
import com.gladiance.ui.fragment.MyProfile.ProfileDeviceCardFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.guestlandingpage.Controls;

import java.util.List;

public class DeviceControlScheduleAdapter extends RecyclerView.Adapter<DeviceControlScheduleAdapter.ViewHolder> {
    private List<Controls> controls;
    private Context context;
    private SharedPreferences prefs;

    public DeviceControlScheduleAdapter(List<Controls> controls, Context context) {
        this.controls = controls;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_control_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Controls control = controls.get(position);
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());

        SharedPreferences prefs = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE);



//        try {
//            // Set checkbox state based on isChecked flag in Controls object
//          //  holder.deviceNameCheckBox.setChecked(control.isChecked());
//
//// Retrieve saved state from SharedPreferences and apply
//            boolean isChecked = prefs.getBoolean("isChecked_2" + position, false); // default to false if key doesn't exist
//            holder.deviceNameCheckBox.setChecked(isChecked);
//            Log.e(TAG, "onBindViewHolder23: "+isChecked);
//        }
//        catch (Exception e){
//
//        }


       // holder.deviceNameCheckBox.setOnCheckedChangeListener(null);

        // Set checkbox state based on isChecked flag in Controls object
        //holder.deviceNameCheckBox.setChecked(control.isChecked());

        String projectSpaceTypePlannedDeviceName = control.getgAAProjectSpaceTypePlannedDeviceName();


        // Set OnCheckedChangeListener for checkbox
//        holder.deviceNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // Update isChecked state in Controls object
//                control.setChecked(isChecked);
//
//                // Handle saving state or other actions if needed
//                try {
//                    if (prefs == null) {
//                        SharedPreferences.Editor editor = prefs.edit();
//                        editor.putBoolean("isChecked_" + position, isChecked);
//                        editor.apply();
//                    }
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception: " + e);
//                }
//            }
//        });


        holder.scheduleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isChecked = holder.deviceNameCheckBox.isChecked();
                if (isChecked) {
                 //   LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

                    Long GaaProjectSpaceTypePlannedDeviceRef = Long.valueOf(control.getgAAProjectSpaceTypePlannedDeviceRef());
            //        String projectSpaceTypePlannedDeviceName = control.getgAAProjectSpaceTypePlannedDeviceName();

                    SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedPreferences.edit();
                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + GaaProjectSpaceTypePlannedDeviceRef);
                    editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceRef);
                    editor3.apply();


                    AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);
                    //  AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = projectSpaceTypePlannedDeviceName;
                    Log.e(TAG, "onClick: " + AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule + " ProjectSpaceTypePlannedDeviceName " + AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule + " ");
                    for (String item : MY_CONSTANT_LIST_SCHEDULE) {
                        System.out.println("NewList2: " + item);
                    }

                    Bundle bundle = new Bundle();
                    // Put data from constant ArrayList into the bundle
                    bundle.putStringArrayList("constantData", AppConstants.MY_CONSTANT_LIST);


                    String Label = control.getLabel();
                    SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    Log.e(TAG, "Label: " + Label);
                    editor1.putString("KEY_USERNAMEs", Label);
                    editor1.apply();

                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = view.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    // holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), DeviceCardActivity.class));

                    bundle.putLong("LONG_VALUE_KEY", GaaProjectSpaceTypePlannedDeviceRef);


                    FragmentManager fragmentManager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    ProfileDeviceCardFragment newFragment = new ProfileDeviceCardFragment();
                    newFragment.setArguments(bundle);
                    transaction.replace(R.id.FlSchedule, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                else {
                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                    Toast.makeText(inflater.getContext(), "Checkbox is not checked!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return controls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        CardView scheduleCardView;
        CheckBox deviceNameCheckBox;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            scheduleCardView = itemView.findViewById(R.id.Cd_Schedule);
            deviceNameTextView = itemView.findViewById(R.id.Device_name_text_view1);
            deviceNameCheckBox = itemView.findViewById(R.id.DN_checkbox1);

            deviceNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}
