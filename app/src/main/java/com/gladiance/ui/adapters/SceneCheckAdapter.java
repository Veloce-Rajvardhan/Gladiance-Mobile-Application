package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;

import static com.gladiance.AppConstants.MY_CONSTANT_LIST;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.AppConstants;
import com.gladiance.ui.activities.AddDeviceActivity;
import com.gladiance.ui.activities.EspMainActivity;
//import com.gladiance.ui.fragment.MyProfile.EditSceneFragment;
import com.gladiance.ui.fragment.MyProfile.ProfileDeviceCardFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.guestlandingpage.Controls;
import com.gladiance.ui.models.scene.Configuration;
import com.gladiance.R;

import java.util.ArrayList;
import java.util.List;

public class SceneCheckAdapter extends RecyclerView.Adapter<SceneCheckAdapter.ViewHolder> {
    private List<Controls> ConArrayList;
    private List<Configuration> ConfigArrayList;
    private SharedPreferences prefs;


    public SceneCheckAdapter(ArrayList<Controls> ConArrayList, ArrayList<Configuration>ConfigArrayList) {
        this.ConArrayList = ConArrayList;
        this.ConfigArrayList = ConfigArrayList;
    }

    @NonNull
    @Override
    public SceneCheckAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene_control_card, parent, false);
        return new SceneCheckAdapter.ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull SceneCheckAdapter.ViewHolder holder, int position) {

        Controls control = ConArrayList.get(position);
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());
        /*if(control.isChecked() == true){
            holder.deviceNameCheckBox.setChecked(true);
        }else{
            holder.deviceNameCheckBox.setChecked(false);
        }*/
        holder.deviceNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                control.setChecked(isChecked);
            }
        });

        for (int i =0; i< ConfigArrayList.size();i++){
            Log.e("123","-- "+ConfigArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceRef()+" -- "+control.getgAAProjectSpaceTypePlannedDeviceRef());
            if(ConfigArrayList.get(i).getgAAProjectSpaceTypePlannedDeviceRef() == control.getgAAProjectSpaceTypePlannedDeviceRef()){
                control.setChecked(true);
                holder.deviceNameCheckBox.setChecked(true);
            }else{
                control.setChecked(false);
                holder.deviceNameCheckBox.setChecked(false);
            }
        }
       // int positions = 0;
        /*for (int i=0;i< ConfigArrayList.size(); i++) {

            Configuration configuration = ConfigArrayList.get(i);
            //holder.deviceNameCheckBox.setChecked(control.isChecked());

         //   holder.deviceNameCheckBox.setOnCheckedChangeListener(null);
            if((configuration.getgAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef()))){
                Log.e(TAG, "onBindViewHolder: "+configuration.getgAAProjectSpaceTypePlannedDeviceRef() + " " + control.getgAAProjectSpaceTypePlannedDeviceRef());
           //     control.isChecked(!control.isPowerState());

              //      control.setCheckBox(!control.isCheckBox());

                //holder.deviceNameCheckBox.setChecked(true);
                control.setChecked(true);
                return;
                // control.setChecked(true);
                // Start from here
//                if(prefs == null){
//
//                }

            }
        }*/

        holder.deviceNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                control.setChecked(isChecked);
                /*if(isChecked){
                    control.setChecked(isChecked);
                    System.out.println("Checked");
                    ConArrayList.get(position).setChecked(isChecked);
                    Log.e(TAG, "onCheckedChanged2: "+position);

                    try {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isChecked_2" + position, isChecked);
                        editor.apply();
                    }
                    catch (Exception e){
                        Log.e(TAG, "Exception: "+e);
                    }
                } else {
                    System.out.println("Un-Checked2");
                    ConArrayList.get(position).setChecked(isChecked);
                }*/
            }
        });


        holder.CdScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = holder.deviceNameCheckBox.isChecked();



                if (isChecked) {
                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

                    Long GaaProjectSpaceTypePlannedDeviceRef = control.getgAAProjectSpaceTypePlannedDeviceRef();

                    Log.e(TAG, "www: ");


                    // GAAPROJECTSPACETYPEPLANNEDDEVICEREF
                    SharedPreferences sharedPreference_dyn2 = inflater.getContext().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_REF_Dyn", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_dyn = sharedPreference_dyn2.edit();
                    editor_dyn.putLong("GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_REF", GaaProjectSpaceTypePlannedDeviceRef);
                    editor_dyn.apply();
                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceRef: " + GaaProjectSpaceTypePlannedDeviceRef);


                    // GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF / NodeConfigDeviceName
                    String projectSpaceTypePlannedDeviceName = control.getgAAProjectSpaceTypePlannedDeviceName();
                    SharedPreferences sharedPreference_dyn3 = inflater.getContext().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF_Dyn", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_dyn3 = sharedPreference_dyn3.edit();
                    editor_dyn3.putString("GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF", projectSpaceTypePlannedDeviceName);
                    editor_dyn3.apply();
                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceRef SP: " + projectSpaceTypePlannedDeviceName);


                    ArrayList<String> newList = new ArrayList<>();
                    newList.add(String.valueOf(GaaProjectSpaceTypePlannedDeviceRef)); // Convert Long to String if necessary
                    newList.add(projectSpaceTypePlannedDeviceName);


                    // Printing contents of newList to Android log
                    for (int i = 0; i < newList.size(); i++) {
                        Log.e("NewListData", "Data at index " + i + ": " + newList.get(i));
                    }


                    SharedPreferences sharedPreference_dyn = inflater.getContext().getSharedPreferences("my_shared_pref_dyn", Context.MODE_PRIVATE);

                    // Create an editor for modifying the SharedPreferences
                    SharedPreferences.Editor editor5 = sharedPreference_dyn.edit();

                    // Put the GaaProjectSpaceTypePlannedDeviceRef value into SharedPreferences with the desired key
                    editor5.putLong("dynamic_key_" + String.valueOf(GaaProjectSpaceTypePlannedDeviceRef), GaaProjectSpaceTypePlannedDeviceRef);

                    // Apply the changes
                    editor5.apply();


                    AppConstants.GaaProjectSpaceTypePlannedDeviceRef = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);
                    AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);

                    //AppConstants.projectSpaceTypePlannedDeviceName = projectSpaceTypePlannedDeviceName;
                    for (String item : MY_CONSTANT_LIST) {
                        System.out.println("NewList2: " + item);
                    }

                    Bundle bundle = new Bundle();
                    // Put data from constant ArrayList into the bundle
                    bundle.putStringArrayList("constantData", AppConstants.MY_CONSTANT_LIST);


                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = inflater.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    String Label = control.getLabel();
                    SharedPreferences sharedPreferences1 = inflater.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    Log.e(TAG, "Label: " + Label);
                    editor1.putString("KEY_USERNAMEs", Label);
                    editor1.apply();

                    Fragment fragment = new ProfileDeviceCardFragment();

                    // Create the destination fragment instance
                    bundle.putLong("LONG_VALUE_KEY", GaaProjectSpaceTypePlannedDeviceRef);
                    Fragment destinationFragment = new DeviceCardFragment();
                    destinationFragment.setArguments(bundle);
                    //destinationFragment.

                    FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.set_mood, fragment, String.valueOf(destinationFragment)).addToBackStack(null)
                            .commit();


                } else {
                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                    Toast.makeText(inflater.getContext(), "Checkbox is not checked!", Toast.LENGTH_SHORT).show();

                }
            }

        });

    }



    @Override
    public int getItemCount() {
        return ConArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        CheckBox deviceNameCheckBox;
        CardView CdScene;

        public ViewHolder(@NonNull View itemView ) {
            super(itemView);
            CdScene = itemView.findViewById(R.id.CdScene);
            deviceNameTextView = itemView.findViewById(R.id.Device_name_text_view);
            deviceNameCheckBox = itemView.findViewById(R.id.DN_checkbox);
            deviceNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }
    }
}