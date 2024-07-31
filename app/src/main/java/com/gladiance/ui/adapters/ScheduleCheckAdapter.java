package com.gladiance.ui.adapters;

import static android.content.ContentValues.TAG;
import static com.gladiance.AppConstants.MY_CONSTANT_LIST;

import android.content.ContentValues;
import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.gladiance.AppConstants;
import com.gladiance.R;
import com.gladiance.ui.fragment.MyProfile.ProfileDeviceCardFragment;
import com.gladiance.ui.fragment.RoomControl.DeviceCardFragment;
import com.gladiance.ui.models.guestlandingpage.Controls;
import com.gladiance.ui.models.schedule.Configuration;
import com.gladiance.ui.viewModels.ScheduleEditDataViewModel;


import java.util.ArrayList;
import java.util.List;

public class ScheduleCheckAdapter extends RecyclerView.Adapter<ScheduleCheckAdapter.ViewHolder> {
    private List<Controls> ConArrayList;
    private List<Configuration> ConfigArrayList;
    private SharedPreferences prefs;
    private ScheduleEditDataViewModel scheduleEditDataViewModel;
    private Context context;



    public ScheduleCheckAdapter(ArrayList<Controls> ConArrayList, ArrayList<Configuration>ConfigArrayList, Context context, ScheduleEditDataViewModel scheduleEditDataViewModel) {
        this.ConArrayList = ConArrayList;
        this.ConfigArrayList = ConfigArrayList;
        this.context = context;
        this.scheduleEditDataViewModel = scheduleEditDataViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_control_card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Controls control = ConArrayList.get(position);
        holder.deviceNameTextView.setText(control.getgAAProjectSpaceTypePlannedDeviceName());

        // Clear any previous OnCheckedChangeListener to prevent unwanted behavior
        holder.deviceNameCheckBox.setOnCheckedChangeListener(null);

        // Set checkbox state based on isChecked flag in Controls object
        holder.deviceNameCheckBox.setChecked(control.isChecked());
        // int positions = 0;
        for (Configuration configuration : ConfigArrayList){
            if(configuration.getGAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef())){
                Log.e(TAG, "onBindViewHolder: " + configuration.getGAAProjectSpaceTypePlannedDeviceRef() + " " + control.getgAAProjectSpaceTypePlannedDeviceRef());
                holder.deviceNameCheckBox.setChecked(true);
                break; // Exit loop once a match is found
            }
        }
//        for (int i=0;i< ConfigArrayList.size(); i++) {
//
//            Configuration configuration = ConfigArrayList.get(i);
//
//
//            if((configuration.getGAAProjectSpaceTypePlannedDeviceRef().equals(control.getgAAProjectSpaceTypePlannedDeviceRef()))){
//                Log.e(TAG, "onBindViewHolder: "+configuration.getGAAProjectSpaceTypePlannedDeviceRef() + " " + control.getgAAProjectSpaceTypePlannedDeviceRef() );
//                holder.deviceNameCheckBox.setChecked(true);
//                return;
//            }
//        }

        holder.deviceNameCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                control.setChecked(isChecked);

                long abc = control.getgAAProjectSpaceTypePlannedDeviceRef();
                Log.e(TAG, "GAAProjectSpaceTypePlannedDeviceRef: "+abc);
                if(control.isChecked() == false) {
                    // editFragment.abc(abc);


//                    ScheduleEditDataViewModel sceneViewModel2 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
//                    LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData2 = sceneViewModel2.getObjectScheduleList();
//                    objectScenesListLiveData2.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
//                        @Override
//                        public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//
//                            if (objectScenesList != null) {
//                                for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
//
//                                    Log.e(ContentValues.TAG, "Edit Schedule Ref Final: "+objectScenes.getRef());
//
//
//                                    objectScenesListLiveData2.removeObserver(this);
//                                }
//
//                            }
//                        }
//                    });



//                    scheduleEditDataViewModel.getObjectScheduleList().observe((LifecycleOwner) context, (Observer<? super List<com.gladiance.ui.models.scheduleEdit.Configuration>>) new Observer<List<com.gladiance.ui.models.sceneEdit.Configuration>>() {
//                        @Override
//                        public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                            if (objectScenesList != null) {
//                                AppConstants.DataEdit = true;
//
//
//                                List<com.gladiance.ui.models.scheduleEdit.Configuration> listCopy = new ArrayList<>(objectScenesList);
//
//                                for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : listCopy) {
//                                    Log.e(TAG, abc + " == " + objectScenes.getGAAProjectSpaceTypePlannedDeviceRef());
//
//                                    if (abc == (objectScenes.getGAAProjectSpaceTypePlannedDeviceRef())) {
//                                        Log.e(TAG, abc + " == " + objectScenes.getGAAProjectSpaceTypePlannedDeviceRef());
//
//                                        // Remove the object from the original list
//                                        objectScenesList.remove(objectScenes);
//                                    } else {
//                                        // Handle other cases if needed
//                                    }
//
//                                    Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//                                }
//                                notifyDataSetChanged(); // Notify the adapter of data changes
//                            }
//                        }
//                    });
                }


                try {
                    if (prefs != null) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isChecked_" + position, isChecked);
                        editor.apply();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e);
                }
//                if(isChecked){
//                    System.out.println("Checked");
//                    ConArrayList.get(position).setChecked(isChecked);
//                } else {
//                    System.out.println("Un-Checked");
//                    ConArrayList.get(position).setChecked(isChecked);
//                }
            }
        });


        holder.scheduleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                boolean provisionStatus = control.isProvisioned();
//                SharedPreferences sharedPreferences13 = view.getContext().getSharedPreferences("my_shared_prefty", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor2 = sharedPreferences13.edit();
//                Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + sharedPreferences13);
//                editor2.putBoolean("KEY_USERNAMEw", provisionStatus);
//                editor2.apply();

//                if (!control.isProvisioned()) {
//                    LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
//                    Long GaaProjectSpaceTypePlannedDeviceRef = control.getGaaProjectSpaceTypePlannedDeviceRef();
//                    SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + GaaProjectSpaceTypePlannedDeviceRef);
//                    editor.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceRef);
//                    editor.apply();


//                    Toast.makeText(inflater.getContext(), "This is a toast message" + control.getGaaProjectSpaceTypePlannedDeviceName(), Toast.LENGTH_SHORT).show();
//                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), AddDeviceActivity.class));
//                } else {

                boolean isChecked = holder.deviceNameCheckBox.isChecked();
                if (isChecked) {

                  //  LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

                    Long GaaProjectSpaceTypePlannedDeviceRef = control.getgAAProjectSpaceTypePlannedDeviceRef();

                    Log.e(TAG, "www: ");


                    // GAAPROJECTSPACETYPEPLANNEDDEVICEREF
                    SharedPreferences sharedPreference_dyn2 = view.getContext().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_REF_Dyn", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor_dyn = sharedPreference_dyn2.edit();
                    editor_dyn.putLong("GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_REF", GaaProjectSpaceTypePlannedDeviceRef);
                    editor_dyn.apply();
                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceRef: " + GaaProjectSpaceTypePlannedDeviceRef);

                    AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);
//                    AppConstants.Edit_Ref_Schedule = Long.valueOf(control.getRef());



                    // GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF / NodeConfigDeviceName
                    String projectSpaceTypePlannedDeviceName = control.getgAAProjectSpaceTypePlannedDeviceName();
                    SharedPreferences sharedPreference_dyn3 = view.getContext().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF_Dyn", Context.MODE_PRIVATE);
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


//                  SharedPreferences sharedPreferences = inflater.getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
//                  SharedPreferences.Editor editor3 = sharedPreferences.edit();
//                  Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceName11: " + GaaProjectSpaceTypePlannedDeviceRef);
//                  editor3.putLong("KEY_USERNAME", GaaProjectSpaceTypePlannedDeviceRef);
//                  editor3.apply();

                    //for dynamic GaaProjectSpaceTypePlannedDeviceRef
                    // Get the SharedPreferences object
                    SharedPreferences sharedPreference_dyn = view.getContext().getSharedPreferences("my_shared_pref_dyn", Context.MODE_PRIVATE);

                    // Create an editor for modifying the SharedPreferences
                    SharedPreferences.Editor editor5 = sharedPreference_dyn.edit();

                    // Put the GaaProjectSpaceTypePlannedDeviceRef value into SharedPreferences with the desired key
                    editor5.putLong("dynamic_key_" + String.valueOf(GaaProjectSpaceTypePlannedDeviceRef), GaaProjectSpaceTypePlannedDeviceRef);

                    // Apply the changes
                    editor5.apply();

                    // this changes done on 4th may start work from here on monday
//                    Configuration configuration = ConfigArrayList.get(position);
//
//                    Long GAAProjectSceneRef = configuration.getgAAProjectSceneRef();
//
//                  //    take a look inside it  //////// (Final dynamic SP)
//                    SharedPreferences sharedPreference_GaaProjectSpaceTypePlannedDeviceRef = inflater.getContext().getSharedPreferences("my_shared_pref_dyn", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor_dyn1 = sharedPreference_GaaProjectSpaceTypePlannedDeviceRef.edit();
//                    editor_dyn1.putLong("GaaProjectSpaceTypePlannedDeviceRef", GaaProjectSpaceTypePlannedDeviceRef);
//                    editor_dyn1.apply();
//                    Log.e(TAG, "GaaProjectSpaceTypePlannedDeviceRef Dynamic: "+GaaProjectSpaceTypePlannedDeviceRef );
//
//                    SharedPreferences sharedPreference_GAAProjectSceneRef = inflater.getContext().getSharedPreferences("my_shared_pref_dyn_projectScene", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor_projectScene = sharedPreference_GAAProjectSceneRef.edit();
//                    editor_projectScene.putLong("GAAProjectSceneRef", GAAProjectSceneRef);
//                    editor_projectScene.apply();
//                    Log.e(TAG, "GAAProjectSceneRef: "+GAAProjectSceneRef );


//                    SharedPreferences sharedPreference_dyn2 = inflater.getContext().getSharedPreferences("my_shared_pref_dyn_projectScene", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor_projectScene = sharedPreference_dyn2.edit();
//                    editor_projectScene.putLong("dynamic_key_projectScene"+String.valueOf(GAAProjectSceneRef), GAAProjectSceneRef);
//                    editor_projectScene.apply();


                    //  String NodeConfigDeviceName = configuration.getNodeConfigParamName();

                    //take a look inside it
//                    SharedPreferences sharedPreference_dyn3 = inflater.getContext().getSharedPreferences("my_shared_pref_dyn_nodeConfigDeviceName", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor_nodeConfigDeviceName = sharedPreference_dyn3.edit();
//                    editor_nodeConfigDeviceName.putString("dynamic_key_nodeConfigDeviceName"+String.valueOf(NodeConfigDeviceName), NodeConfigDeviceName);
//                    editor_nodeConfigDeviceName.apply();

//                    // Pass the sceneRef to the next fragment using fragment arguments
//                    Bundle bundle = new Bundle();
//                    bundle.putStringArrayList("myArrayList", newList);
//
//                    // Create the destination fragment instance
//                    Fragment destinationFragment = new EditSceneFragment();
//                    destinationFragment.setArguments(bundle);


//                    mData.add(String.valueOf(GaaProjectSpaceTypePlannedDeviceRef));
//                    mData.add(String.valueOf(projectSpaceTypePlannedDeviceName));

                    //AppConstants.addToConstantList(String.valueOf(GaaProjectSpaceTypePlannedDeviceRef));
                    //AppConstants.addToConstantList(projectSpaceTypePlannedDeviceName);

                    AppConstants.GaaProjectSpaceTypePlannedDeviceRef = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);
                    AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef = String.valueOf(GaaProjectSpaceTypePlannedDeviceRef);

                    //AppConstants.projectSpaceTypePlannedDeviceName = projectSpaceTypePlannedDeviceName;
                    for (String item : MY_CONSTANT_LIST) {
                        System.out.println("NewList2: " + item);
                    }

                    Bundle bundle = new Bundle();
                    // Put data from constant ArrayList into the bundle
                    bundle.putStringArrayList("constantData", AppConstants.MY_CONSTANT_LIST);


                   /* keyValuePairs = new KeyValuePair[8];
                    // Initializing array elements with key-value pairs
                    keyValuePairs[4] = new KeyValuePair("key1", String.valueOf(GaaProjectSpaceTypePlannedDeviceRef));
                    keyValuePairs[5] = new KeyValuePair("key1", projectSpaceTypePlannedDeviceName);*/

                    // ObjScenes objScenes = new ObjScenes(null,null,null,null,String.valueOf(GaaProjectSpaceTypePlannedDeviceRef), projectSpaceTypePlannedDeviceName);
                    /*for (KeyValuePair pair : keyValuePairs) {
                        if (pair != null) {
                            System.out.println("Keyyyy: " + pair.getKey() + ", Value: " + pair.getValue());
                        }
                    }*/


//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("myArrayList", mData);
//
//                    for (String item : mData) {
//                        System.out.println("BundleArrayData"+item);
//                    }


                    // Retrieve the ArrayList from the Bundle
                    //     ArrayList<String> newList = getArguments().getStringArrayList("myArrayList");

//// Create an Intent to start the next Activity
//                    Intent intent = new Intent(getActivity(), NextActivity.class);
//
//// Put the ArrayList into the Intent as an extra
//                    intent.putStringArrayListExtra("myArrayList", newList);
//
//// Start the next Activity
//                    startActivity(intent);

                    String nodeId = control.getNodeId();
                    SharedPreferences sharedPreferences2 = view.getContext().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    Log.e(TAG, "Node Id: " + nodeId);
                    editor.putString("KEY_USERNAMEs", nodeId);
                    editor.apply();

                    String Label = control.getLabel();
                    SharedPreferences sharedPreferences1 = view.getContext().getSharedPreferences("my_shared_prefe_label", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    Log.e(TAG, "Label: " + Label);
                    editor1.putString("KEY_USERNAMEs", Label);
                    editor1.apply();
                    //  intent.putExtra("LONG_VALUE_KEY", GaaProjectSpaceTypePlannedDeviceRef);

                    //holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), EspMainActivity.class));


                    //edit from here
                    //  Fragment fragment = new DeviceCardFragment();
                    Fragment fragment = new ProfileDeviceCardFragment();

                    //   Bundle bundle = new Bundle();
                    // Pass the sceneRef to the next fragment using fragment arguments
                    //   Bundle bundle = new Bundle();
//                    bundle.putString("SCENE_REF", sceneRefString);
//                    fragment.setArguments(bundle);


                    //   bundle.putStringArrayList("myArrayList", newList);

                    // Create the destination fragment instance
                    bundle.putLong("LONG_VALUE_KEY", GaaProjectSpaceTypePlannedDeviceRef);
                    Fragment destinationFragment = new DeviceCardFragment();
                    destinationFragment.setArguments(bundle);
                    //destinationFragment.

                    FragmentTransaction transaction = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.FlSchedule, fragment, String.valueOf(destinationFragment)).addToBackStack(null)
                            .commit();


                    //to here

                    /////---------///////////////------

//                    Intent intent = new Intent(holder.itemView.getContext(), EspMainActivity.class);
//
//                    // Put the long value as an extra in the Intent
//                    intent.putExtra("LONG_VALUE_KEY", GaaProjectSpaceTypePlannedDeviceRef);
//                    //  Bundle bundle = new Bundle();
//                    //    bundle.putSerializable("myArrayList", mDa);
//
//// Put the Bundle into the Intent
//                    intent.putExtras(bundle);

                    //////// -------///////////////////------

                    //intent.putExtra("objScene",objScenes);
                    // Start EspMainActivity with the Intent

                    //-----------////////
                    ///   holder.itemView.getContext().startActivity(intent);
                    /////////-----
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
        CardView scheduleCardView;

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