package com.gladiance.ui.fragment.MyProfile;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.DeviceControls.AirContiningActivity;
import com.gladiance.ui.activities.DeviceControls.BellActivity;
import com.gladiance.ui.activities.DeviceControls.CurtainActivity;
import com.gladiance.ui.activities.DeviceControls.DimmerActivity;
import com.gladiance.ui.activities.DeviceControls.FanActivity;
import com.gladiance.ui.activities.DeviceControls.RGBLightActivity;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.adapters.ProfileCardAdapter;
import com.gladiance.ui.fragment.DeviceControls.AirConditionerFragment;
import com.gladiance.ui.fragment.DeviceControls.CurtainFragment;
import com.gladiance.ui.fragment.DeviceControls.DimmerFragment;
import com.gladiance.ui.fragment.DeviceControls.FanFragment;
import com.gladiance.ui.fragment.DeviceControls.RGBLightFragment;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.Devices;
import com.gladiance.ui.models.RefObject;
import com.gladiance.ui.models.SceneStoreData.ConfigurationSceneEditData;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.saveSchedule.ObjectScheduleEdit;
import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scenelist.ObjectSchedule;
import com.gladiance.ui.models.scheduleEdit.Configuration;
import com.gladiance.ui.viewModels.SceneCreateViewModel;
import com.gladiance.ui.viewModels.SceneEditDataViewModel;
import com.gladiance.ui.viewModels.ScheduleEditDataViewModel;
import com.gladiance.ui.viewModels.ScheduleEditViewModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileDeviceCardFragment extends Fragment {
    public ProfileDeviceCardFragment() {
        // Required empty public constructor
    }
    private ArrayList<Devices> arrayList;
    RecyclerView recyclerView;
    String nodeId2;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;
    private ObjectScenes objectScenes;
    ProgressBar progressBar;
    private static ProfileDeviceCardFragment instance;
    private List<ConfigurationSceneEditData> configurationSceneEditData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_device_card, container, false);

        recyclerView = view.findViewById(R.id.recycleViewDeviceCard);
        instance = this;
        progressBar = view.findViewById(R.id.progressBar); // Initialize ProgressBar

        espApp = new EspApplication(requireContext());
        networkApiManager = new NetworkApiManager(requireContext().getApplicationContext(), espApp);

        arrayList = new ArrayList<>();

        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "node id4: " +nodeId);

        SharedPreferences preferences = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        nodeId2 = preferences.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "node id: " +nodeId2);

        getDevice(nodeId);


        return view;
    }

    public static ProfileDeviceCardFragment getInstance() {
        return instance;
    }

    private void getDevice(String nodeId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<DeviceInfo> call = apiService.getAllData(nodeId);
        Log.e(TAG, "Profile Device Card Fragment getDevice: "+ nodeId );

        call.enqueue(new Callback<DeviceInfo>() {
            @Override
            public void onResponse(Call<DeviceInfo> call, Response<DeviceInfo> response) {
                if (response.isSuccessful() && response.body() != null) {


                    Log.d(TAG, "onResponse: "+response.body());
                    Gson gson = new Gson();
                    String n = gson.toJson(response.body());

                    SharedPreferences sharedPreferences10 = requireContext().getSharedPreferences("my_shared_prefet", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences10.edit();
                    editor.putString("KEY_USERNAMEst", n);
                    editor.apply();

                    List<DeviceInfo.Device> devices = response.body().getDevices();
                    for (DeviceInfo.Device device : devices) {
                        List<DeviceInfo.Param> params = device.getParams();
                        Log.e(TAG, "Device Type: "+device.getName());
                        Log.e(TAG, "Device Type: "+device.getType());
                        for (DeviceInfo.Param param : params) {
                            String name = param.getUi_type();
                            Log.e(TAG, "onResponse: "+param.getName());
                            String uiType = param.getUi_type();
                            Log.e(TAG, "onResponse22222: "+param.getUi_type());
                            Log.e(TAG, "onResponse22222: "+uiType);
                        }
                        arrayList.add(new Devices(device.getName(),device.getType(),device.getPrimary()));
                    }
                    if(arrayList.size() == 1){
                        if(arrayList.get(0).getType().equals("esp.device.lightbulb")){
                            RGBLightFragment rgbLightFragment = new RGBLightFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            rgbLightFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, rgbLightFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
//                            String name = arrayList.get(0).getName();
//                            Intent intent = new Intent(requireContext(), RGBLightActivity.class);
//                           // intent.putExtra("extra_name", name);
//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
//                            editor2.putString("Name", name);
//                            editor2.apply();
//                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.fan")){
                            // Create a new instance of the FanFragment
                            FanFragment fanFragment = new FanFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            fanFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, fanFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
//                            String name = arrayList.get(0).getName();
//                            Intent intent = new Intent(requireContext(), FanActivity.class);
//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
//                            editor2.putString("Name", name);
//                            editor2.apply();
//                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.curtain")){
                            CurtainFragment curtainFragment = new CurtainFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            curtainFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, curtainFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
//                            String name = arrayList.get(0).getName();
//                            Intent intent = new Intent(requireContext(), CurtainActivity.class);
//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
//                            editor2.putString("Name", name);
//                            editor2.apply();
//                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.light")){
                            DimmerFragment dimmerFragment = new DimmerFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            dimmerFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, dimmerFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
//                            String name = arrayList.get(0).getName();
//                            Intent intent = new Intent(requireContext(), DimmerActivity.class);
//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
//                            editor2.putString("Name", name);
//                            editor2.apply();
//                            Log.e(TAG, "Device Card Fragment : "+ name);
//                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.bellcontrol")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), BellActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.ther")){
                            AirConditionerFragment airConditionerFragment = new AirConditionerFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            airConditionerFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, airConditionerFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
//                            String name = arrayList.get(0).getName();
//                            Intent intent = new Intent(requireContext(), AirContiningActivity.class);
//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
//                            editor2.putString("Name", name);
//                            editor2.apply();
//                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.thermostat")) {
                            AirConditionerFragment airConditionerFragment = new AirConditionerFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            airConditionerFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, airConditionerFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
                        } else if(arrayList.get(0).getType().equals("e.d.bell")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), BellActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.curt")){
                            CurtainFragment curtainFragment = new CurtainFragment(getContext());
                            String name = arrayList.get(0).getName();
                            // Get SharedPreferences instance
                            SharedPreferences preferences = requireActivity().getSharedPreferences("my_shared_prefe_labelname", Context.MODE_PRIVATE);

// Obtain an editor to modify SharedPreferences
                            SharedPreferences.Editor editor3 = preferences.edit();

// Example: Storing a String with key "KEY_USERNAMEs"
                            String newValue = "New Value"; // Replace with the value you want to store
                            editor3.putString("LABEL_NAME", name);

// Apply changes
                            editor3.apply();
                            // Pass data to the fragment using arguments
                            Bundle args = new Bundle();
                            args.putString("Name", name);
                            curtainFragment.setArguments(args);

                            // Perform fragment transaction to replace current fragment or add it
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                            // Replace or add fragment to the container (assuming you have a container in your layout with id container_id)
                            transaction.replace(R.id.FlSchedule, curtainFragment);
                            // or transaction.add(R.id.container_id, fanFragment);

                            // Commit the transaction
                            transaction.commit();
//                            String name = arrayList.get(0).getName();
//                            Intent intent = new Intent(requireContext(), CurtainActivity.class);
//                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
//                            editor2.putString("Name", name);
//                            editor2.apply();
//                            startActivity(intent);
                        }else {
                            ProfileCardAdapter profileCardAdapter = new ProfileCardAdapter(arrayList);
                            recyclerView.setAdapter(profileCardAdapter);
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(gridLayoutManager1);
                        }

                    }
                    else {
                        ProfileCardAdapter profileCardAdapter = new ProfileCardAdapter(arrayList);
                        recyclerView.setAdapter(profileCardAdapter);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(gridLayoutManager1);
                    }
                } else {

                }

            }

            @Override
            public void onFailure(Call<DeviceInfo> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void sendSwitchState(boolean powerState,String name,String power) {
        progressBar.setVisibility(View.VISIBLE);

        String commandBody = "{\""+name+"\": {\""+power+"\": " + powerState + "}}";
        String message = powerState ? "on" : "off";
        Toast.makeText(requireContext(), "Switch is "+message+".Please wait for second for saving data for schedule", Toast.LENGTH_SHORT).show();
        boolean shPowerState = powerState;
        SharedPreferences sharedPreferencesPowerState = requireContext().getSharedPreferences("MyPreferencesPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesPowerState.edit();
        editor.putBoolean("PowerState", shPowerState);
        editor.apply();
        Log.e(TAG, "Device Fragment PowerState:"+shPowerState );


        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";
        Log.e(TAG, "Device Fragment Node Id:"+nodeId2 );

//        SharedPreferences sharedPreference_dyn3 = requireActivity().getSharedPreferences("PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF_Dyn", Context.MODE_PRIVATE);
//        String savedValue = sharedPreference_dyn3.getString("GAA_PROJECT_SPACE_TYPE_PLANNED_DEVICE_NAME_REF", "default_value_if_not_found");


//        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
//        String nodeId = preferences9.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "node id4: " +nodeId);

       // AppConstants.projectSpaceTypePlannedDeviceName = savedValue;

        //Edit Scene Object Data
//        for (ConfigurationSceneEditData config : configurationSceneEditData) {
//            // Print the GAAProjectSpaceTypePlannedDeviceRef for each configuration
//            System.out.println("dataaa22: "+config.getGAAProjectSpaceTypePlannedDeviceRef());
//        }



        // Edit Scene
//        try {
//            AppConstants.projectSpaceTypePlannedDeviceName = name;
//            AppConstants.powerState = power;
//            AppConstants.power = String.valueOf(powerState);
//            Log.d("TAG", "PowerState: " + AppConstants.powerState);
//            Log.d("TAG", "Power: " + AppConstants.power);
//
//            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
//            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
//            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
//            Log.e("APPCONSTS4",""+AppConstants.Space_dyn);
//            Log.e("APPCONSTS5",""+AppConstants.projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS6",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS7",""+AppConstants.powerState);
//            Log.e("APPCONSTS8",""+AppConstants.power);
//
//
//            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//            // sharedViewModel.setObjecatSchedule(objectScenes);
//            sharedViewModelEdit.addObjectScenes(objectScenes);
//
//            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
//            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
////            List<SceneConfig> list = new ArrayList<>();
////            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
////            list.size();
////            Log.e(TAG, "List Size: "+list.size());
//
//            ////////////
//
//
//        }
//        catch (Exception e){
//            Log.e(TAG, "sendSwitchState: "+e);
//        }
//
//        // Create Scene
//        try {
//            getRefObjectValue();
//
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
//            Log.e(TAG, "old: "+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            AppConstants.Create_powerState = power;
//            AppConstants.Create_power = String.valueOf(powerState);
//            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
//            Log.d("TAG", "Power2: " + AppConstants.Create_power);
//
//            Log.e("APPCONSTS9 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
//            Log.e("APPCONSTS10 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
//            Log.e("APPCONSTS11 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
//            Log.e("APPCONSTS12 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
//            Log.e("APPCONSTS13 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS14 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS15 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS16 power_Schedule",""+AppConstants.Create_power);
//
//
//            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power, AppConstants.Create_Ref_Scene);
//            SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
//
//                }
//            }, 1000);
//        }
//        catch (Exception e){
//            Log.e(TAG, "sendSwitchState: "+e);
//        }


        //// Create Schedule
        try {
            getRefObjectValue();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
            AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Create_powerState_Schedule = power;
            AppConstants.Create_power_Schedule = String.valueOf(powerState);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

//            Log.e("APPCONSTS2 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
//            Log.e("APPCONSTS2 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
//            Log.e("APPCONSTS2 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
//            Log.e("APPCONSTS2 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

            Log.e("APPCONSTS17 Create Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS18 Create Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS19 Create Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS20 Create SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS21 Create GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS22 Create projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS23 Create powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS24 Create power_Schedule",""+AppConstants.Create_power_Schedule);


            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule, AppConstants.Create_Ref_Schedule);
            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
            sharedViewModel.addObjectSchedule(objectSchedule);
           // ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
//            ScheduleViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
//            sharedViewModel.addObjectScenes(objectSceneCreate);
            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());
                    progressBar.setVisibility(View.GONE);

            ////////////
                }
            }, 1000);

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {
            if(AppConstants.Edit_Ref_dyn_Schedule != null){
                //      getRefObjectValue();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule = name;
            AppConstants.Edit_powerState_Schedule = power;
            AppConstants.Edit_power_Schedule = String.valueOf(powerState);
            Log.d("TAG", "PowerState: " + AppConstants.Edit_powerState_Schedule);
            Log.d("TAG", "Power: " + AppConstants.Edit_power_Schedule);

            Log.e("APPCONSTS25"," Edit schedule "+AppConstants.Edit_Ref_dyn_Schedule);
            Log.e("APPCONSTS26"," Edit schedule "+AppConstants.Edit_Name_dyn_Schedule);
            Log.e("APPCONSTS27", " Edit schedule "+AppConstants.Edit_ScheduleRef_Schedule);
            Log.e("APPCONSTS28"," Edit schedule "+AppConstants.Edit_Space_dyn_Schedule);
            Log.e("APPCONSTS29"," Edit schedule "+AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS30"," Edit schedule "+AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS31"," Edit schedule "+AppConstants.Edit_powerState_Schedule);
            Log.e("APPCONSTS32"," Edit schedule "+AppConstants.Edit_power_Schedule);


//            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_Ref_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
//            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(ScheduleEditViewModel.class);
//            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
          //  sharedViewModel.addObjectScenes(objectScenes);

         //   Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            //////////// start here ///////////
            ScheduleEditDataViewModel scheduleViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
            LiveData<List<com.gladiance.ui.models.scheduleEdit.Configuration>> objectScenesListLiveData1 = scheduleViewModel1.getObjectScheduleList();
            objectScenesListLiveData1.observe(getViewLifecycleOwner(), new Observer<List<com.gladiance.ui.models.scheduleEdit.Configuration>>() {
                @Override
                public void onChanged(List<com.gladiance.ui.models.scheduleEdit.Configuration> objectScenesList) {
//                       for(int i = 0; i <ConArrayList.size(); i++) {
//                           if (ConArrayList.get(i).isChecked() == true) {
//                               Log.e("ConArrayList", "Selected -- " + ConArrayList.get(i).getGaaProjectSpaceTypePlannedDeviceName());
                    if (objectScenesList != null) {
                        AppConstants.DataEditSchedule = true;
                        for (com.gladiance.ui.models.scheduleEdit.Configuration objectScenes : objectScenesList) {
                            if(Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef) == objectScenes.getGAAProjectSpaceTypePlannedDeviceRef()){
                                objectScenes.getRef();
                                Log.e(TAG, "Before Edit NodeConfigParamName1: "+ objectScenes.getNodeConfigParamName());
                                Log.e(TAG, "Before Edit power1: "+ objectScenes.getValue());
                                        // objectScenes.modify();
//                                        this.ref = ref;
//                                        this.gAAProjectSceneRef = gAAProjectSceneRef;
//                                        this.gAAProjectSpaceTypePlannedDeviceRef = gAAProjectSpaceTypePlannedDeviceRef;
//                                        this.nodeConfigDeviceName = nodeConfigDeviceName;
//                                        this.nodeConfigParamName = nodeConfigParamName;
//                                        this.value = value;
//
                                        // objectScenes.setRef();
//                                        objectScenes.setgAAProjectSceneRef();
//                                        objectScenes.setgAAProjectSpaceTypePlannedDeviceRef();
//                                        objectScenes.setNodeConfigDeviceName();
                                        // objectScenes.setNodeConfigParamName(AppConstants.powerState);
                                        objectScenes.setValue(AppConstants.Edit_power_Schedule);
                                        Log.e(TAG, "After edit NodeConfigParamName: "+ objectScenes.getNodeConfigParamName());
                                        Log.e(TAG, "After Edit power: "+ objectScenes.getValue());
                                        AppConstants.DataEditSchedule = false;

//                                        this.setgAAProjectSpaceTypePlannedDeviceRef(Long.parseLong(AppConstants.projectSpaceTypePlannedDeviceName));
                                    }
                                    else{

                                    }


//
                                    Log.d("ObjectScenes22", String.valueOf(objectScenes.getRef()));
//
                                }
                                if(AppConstants.DataEditSchedule == true){
                                    getRefObjectValueConfigRef();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e(TAG, "Create new Ref Because the device is not present into Configuration");
                                            com.gladiance.ui.models.scheduleEdit.Configuration objectScenes1 = new Configuration(AppConstants.Edit_Ref_Schedule,Long.parseLong(AppConstants.Edit_ScheduleRef_Schedule),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
                                            ScheduleEditDataViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(ScheduleEditDataViewModel.class);
                                            // sharedViewModel.setObjectSchedule(objectScenes);
                                            sharedViewModel1.addObjectEditSchedule(objectScenes1);
                                        }
                                    }, 1000);
                                }

                                progressBar.setVisibility(View.GONE);


                                // Remember to remove the observer if necessary
                                objectScenesListLiveData1.removeObserver(this);
                            }
                            //   }
                            //}
                        }


                    });

//-----------------------------------------------------------------------------------------------------------------------------------

//                    ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power, AppConstants.Create_Ref_Scene);
//                    SceneViewModel sharedViewModel1 = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
//                    // sharedViewModel.setObjectSchedule(objectScenes);
//                    sharedViewModel1.addObjectScenes(objectScenes);

//---------------------------------------------------------------------------------------------------------------------------------------

//                    Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
                    //   objScenes.setRef_dyn(AppConstants.Ref_dyn);
//
//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

                    ////////////
                }
            }, 1000);
        }

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);



    }
    private void getRefObjectValueConfigRef() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        SharedPreferences preferences9 = getContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId3 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(EventBus.TAG, "node id3: " + nodeId3);
        // Make API call
        Call<AllocateSingleIdResponse> call = apiService.allocateSingleId();
        call.enqueue(new Callback<AllocateSingleIdResponse>() {
            @Override
            public void onResponse(Call<AllocateSingleIdResponse> call, Response<AllocateSingleIdResponse> response) {
                if (response.isSuccessful()) {
                    AllocateSingleIdResponse responseModel = response.body();
                    if (responseModel != null) {
                        boolean success = responseModel.getSuccessful();
                        String message = responseModel.getMessage();
                        AppConstants.Edit_Ref_Schedule = Long.valueOf(responseModel.getTag());
                        Log.d(EventBus.TAG, "Success2: " + success + ", Message2: " + message+ " Tag2: "+AppConstants.Edit_Ref_Schedule);

                    }
                } else {
                    Log.e(EventBus.TAG, "API call failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                Log.e(EventBus.TAG, "API call failed: " + t.getMessage());
            }
        });
    }
    private void getRefObjectValue() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        SharedPreferences preferences9 = getContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId3 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(EventBus.TAG, "node id3: " + nodeId3);
        // Make API call
        Call<AllocateSingleIdResponse> call = apiService.allocateSingleId();
        call.enqueue(new Callback<AllocateSingleIdResponse>() {
            @Override
            public void onResponse(Call<AllocateSingleIdResponse> call, Response<AllocateSingleIdResponse> response) {
                if (response.isSuccessful()) {
                    AllocateSingleIdResponse responseModel = response.body();
                    if (responseModel != null) {
                        boolean success = responseModel.getSuccessful();
                        String message = responseModel.getMessage();
                        String Ref = responseModel.getTag();

                        RefObject refObject = new RefObject(Ref);
                        AppConstants.Create_Ref_Schedule = Ref;
                        AppConstants.Create_Ref_Scene = Ref;

                        Log.e(TAG, "abcc2: "+AppConstants.Create_Ref_Schedule);



                        //saveRefToSharedPreferences(Ref);

// Later in your code, fetch the RefValue from SharedPreferences
//                        String savedRef = getRefFromSharedPreferences();
//                        if (savedRef != null) {
//                            Log.d(EventBus.TAG, "Retrieved RefValue from SharedPreferences: " + savedRef);
//                            // Use savedRef as needed
//                        } else {
//                            Log.e(EventBus.TAG, "RefValue not found in SharedPreferences");
//                            // Handle case where RefValue is not found in SharedPreferences
//                        }
                    //    AppConstants.Create_Ref_Schedule = Long.valueOf(responseModel.getTag());
                        Log.e(EventBus.TAG, "Create Reffff: "+AppConstants.Create_Ref_Schedule);

                        Log.d(EventBus.TAG, "Success2: " + success + ", Message2: " + message+ " Tag2: "+AppConstants.Create_Ref_Schedule);

                    }
                } else {
                    Log.e(EventBus.TAG, "API call failed with code: " + response.code());
                }
            }

            private String getRefFromSharedPreferences() {
                SharedPreferences preferences = getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                return preferences.getString("RefValue", null);
            }

            private void saveRefToSharedPreferences(String refValue) {
                SharedPreferences preferences = getContext().getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("RefValue", refValue);
                editor.apply(); // Apply changes asynchronously
                Log.d(EventBus.TAG, "Saved RefValue to SharedPreferences: " + refValue);
            }

            @Override
            public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                Log.e(EventBus.TAG, "API call failed: " + t.getMessage());
            }
        });
    }


    private final OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            // Replace the current fragment with the EditSceneFragment
            transaction.replace(R.id.set_mood, new EditSceneFragment());

            // Add the transaction to the back stack
            transaction.addToBackStack("EditSceneFragment");

            // Commit the transaction
            transaction.commit();
        }
    };




}