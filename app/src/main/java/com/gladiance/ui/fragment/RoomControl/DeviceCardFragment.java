package com.gladiance.ui.fragment.RoomControl;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.DeviceControls.AirContiningActivity;
import com.gladiance.ui.activities.DeviceControls.BellActivity;
import com.gladiance.ui.activities.DeviceControls.CircularSeekBar;
import com.gladiance.ui.activities.DeviceControls.CurtainActivity;
import com.gladiance.ui.activities.DeviceControls.DimmerActivity;
import com.gladiance.ui.activities.DeviceControls.FanActivity;
import com.gladiance.ui.activities.DeviceControls.RGBLightActivity;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.adapters.CardAdapter;
import com.gladiance.ui.fragment.MyProfile.EditSceneFragment;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.Devices;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ac.Gendb;
import com.gladiance.ui.models.ac.Gendbarr;
import com.gladiance.ui.models.ac.SetRange;
import com.gladiance.ui.models.ac.Thermostat;
import com.gladiance.ui.models.ac.ThermostatResponse;
import com.gladiance.ui.models.saveScene.SceneConfig;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scenelist.ObjectSchedule;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeviceCardFragment extends Fragment {
    public DeviceCardFragment() {
        // Required empty public constructor
    }
    private ArrayList<Devices> arrayList;
    RecyclerView recyclerView;
    String nodeId2;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;
    private ObjectScenes objectScenes;

    private static DeviceCardFragment instance;
    // Inside your fragment where you initialize objectSchedule
 //   ScheduleViewModel scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_card, container, false);
        recyclerView = view.findViewById(R.id.recycleViewDeviceCard);
        instance = this;

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



    public static DeviceCardFragment getInstance() {
        return instance;
    }



    private void getDevice(String nodeId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<DeviceInfo> call = apiService.getAllData(nodeId);
        Log.e(TAG, "getDevice: "+ nodeId );

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
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), RGBLightActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.fan")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), FanActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.curtain")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), CurtainActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.light")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), DimmerActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            Log.e(TAG, "Device Card Fragment : "+ name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.bellcontrol")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), BellActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.ther")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), AirContiningActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                            fetchThermostatData(nodeId2);
                            Log.e(TAG, "onResponse DeviceCardFrag NodeId: " + nodeId2 );
                        }else if(arrayList.get(0).getType().equals("e.d.bell")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), BellActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.curt")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), CurtainActivity.class);
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                            editor2.putString("Name", name);
                            editor2.apply();
                            startActivity(intent);
                        }else {
                            CardAdapter cardAdapter = new CardAdapter(arrayList);
                            recyclerView.setAdapter(cardAdapter);
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(gridLayoutManager1);
                        }

                    }
                    else {
                        CardAdapter cardAdapter = new CardAdapter(arrayList);
                        recyclerView.setAdapter(cardAdapter);
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

        String commandBody = "{\""+name+"\": {\""+power+"\": " + powerState + "}}";
        String message = powerState ? "on" : "off";
        Toast.makeText(requireContext(), "Switch is "+message, Toast.LENGTH_SHORT).show();
        boolean shPowerState = powerState;
        SharedPreferences sharedPreferencesPowerState = requireContext().getSharedPreferences("MyPreferencesPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesPowerState.edit();
        editor.putBoolean("PowerState", shPowerState);
        editor.apply();
        Log.e(TAG, "Device Fragment PowerState:"+shPowerState );


        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";
        Log.e(TAG, "Device Fragment Node Id:"+nodeId2 );

        // Edit Scene
        try {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = power;
            AppConstants.power = String.valueOf(powerState);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
           // sharedViewModel.setObjectSchedule(objectScenes);
            sharedViewModel.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

            List<SceneConfig> list = new ArrayList<>();
            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
            list.size();
            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            AppConstants.Create_powerState = power;
            AppConstants.Create_power = String.valueOf(powerState);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

//            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Ref_dyn_Schedule);
//            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Name_dyn_Schedule);
//            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.ScheduleRef_Schedule);
//            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Space_dyn_Schedule);
//            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.projectSpaceTypePlannedDeviceName_Schedule);
//            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef_Schedule);
//            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState);
//            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);


            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);





            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

            List<SceneConfig> list = new ArrayList<>();
            list.add(new SceneConfig(Long.parseLong(AppConstants.Create_SceneRef),Long.parseLong(AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef),AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_powerState,AppConstants.Create_power));
            list.size();
            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


            //// Create Schedule
        try {
            AppConstants.Create_powerState_Schedule = power;
            AppConstants.Create_power_Schedule = String.valueOf(powerState);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState_Schedule);
            Log.d("TAG", "Power2: " + AppConstants.Create_power_Schedule);

            Log.e("APPCONSTS2 Ref_dyn_Schedule",""+AppConstants.Create_Ref_dyn_Schedule);
            Log.e("APPCONSTS2 Name_dyn_Schedule",""+AppConstants.Create_Name_dyn_Schedule);
            Log.e("APPCONSTS2 SceneRef_Schedule",""+AppConstants.Create_ScheduleRef_Schedule);
            Log.e("APPCONSTS2 Space_dyn_Schedule",""+AppConstants.Create_Space_dyn_Schedule);
            Log.e("APPCONSTS2 projectSpaceTypePlannedDeviceName_Schedule",""+AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule);
            Log.e("APPCONSTS2 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule);
            Log.e("APPCONSTS2 powerState_Schedule",""+AppConstants.Create_powerState_Schedule);
            Log.e("APPCONSTS2 power_Schedule",""+AppConstants.Create_power_Schedule);


            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);

            Log.e(TAG, "sendSwitchState: "+objectSchedule.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

            List<SceneConfig> list = new ArrayList<>();
            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
            list.size();
            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);



    }
//    // Handle onBackPressed
//    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//        @Override
//        public void handleOnBackPressed() {
//            finish(); // Finish the activity when back button is pressed
//        }
//    };
//    getOnBackPressedDispatcher().addCallback(this, callback);

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
//            ScheduleViewModel scheduleViewModel = new ViewModelProviders.of(requireActivity()).get(ScheduleViewModel.class);
//
//            ObjectSchedule objectSchedule = scheduleViewModel.getObjectSchedule().getValue();

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


//    public void fetchThermostatData(String nodeId) {
//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Call<ThermostatResponse> call = apiService.getNodeStatus(nodeId);
//
//        call.enqueue(new Callback<ThermostatResponse>() {
//            @Override
//            public void onResponse(Call<ThermostatResponse> call, Response<ThermostatResponse> response) {
//                if (response.isSuccessful()) {
//                    Thermostat thermostat = response.body().getThermostat();
//                    if (thermostat != null) {
//                        String mode = thermostat.getMode();
//                        String unit = thermostat.getUnit();
//
//                        SharedPreferences sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("Mode", mode);
//                        Log.e(TAG, "onResponseMode: " + mode );
//                        editor.putString("Unit", unit);
//
//                        editor.apply();
//
//                        Intent intent = new Intent(requireContext(), AirContiningActivity.class);
//                        startActivity(intent);
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ThermostatResponse> call, Throwable t) {
//                Log.e(TAG, "Error: " + t.getMessage());
//            }
//        });
//    }

    public void fetchThermostatData(String nodeId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ThermostatResponse> call = apiService.getNodeStatus(nodeId);

        call.enqueue(new Callback<ThermostatResponse>() {
            @Override
            public void onResponse(Call<ThermostatResponse> call, Response<ThermostatResponse> response) {
                if (response.isSuccessful()) {
                    ThermostatResponse thermostatResponse = response.body();
                    if (thermostatResponse != null) {
                        Thermostat thermostat = thermostatResponse.getThermostat();
                        if (thermostat != null) {
                            String mode = thermostat.getMode();
                            String unit = thermostat.getUnit();

                            Gendb gendb = thermostat.getGendb();
                            if (gendb != null) {
                                Gendbarr gendbarr = gendb.getGendbarr();
                                if (gendbarr != null) {
                                    SetRange setRange = gendbarr.getSetRange();
                                    if (setRange != null) {
                                        int min = setRange.getMin();
                                        int max = setRange.getMax();

                                        SharedPreferences sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("Mode", mode);
                                        editor.putString("Unit", unit);
                                        editor.apply();

                                        SharedPreferences sharedPrefSetRange = requireContext().getSharedPreferences("MyPrefsRange", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor1 = sharedPrefSetRange.edit();
                                        editor1.putInt("SetRangeMin", min);
                                        editor1.putInt("SetRangeMax", max);
                                        editor1.apply();

                                        Log.e(TAG, "onResponseSetRangeMin: " + min);
                                        Log.e(TAG, "onResponseSetRangeMax: " + max);

                                        editor.apply();

                                        Intent intent = new Intent(requireContext(), AirContiningActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ThermostatResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }





}