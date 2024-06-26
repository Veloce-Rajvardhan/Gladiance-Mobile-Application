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
import com.gladiance.ui.activities.DeviceControls.CurtainActivity;
import com.gladiance.ui.activities.DeviceControls.DimmerActivity;
import com.gladiance.ui.activities.DeviceControls.FanActivity;
import com.gladiance.ui.activities.DeviceControls.RGBLightActivity;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.adapters.ProfileCardAdapter;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.Devices;
import com.gladiance.ui.models.SceneStoreData.ConfigurationSceneEditData;
import com.gladiance.ui.models.SceneViewModel;
import com.gladiance.ui.models.ScheduleViewModel;
import com.gladiance.ui.models.saveSchedule.ObjectScheduleEdit;
import com.gladiance.ui.models.scene.ObjectSceneCreate;
import com.gladiance.ui.models.scene.ObjectScenes;
import com.gladiance.ui.models.scenelist.ObjectSchedule;
import com.gladiance.ui.viewModels.SceneCreateViewModel;
import com.gladiance.ui.viewModels.ScheduleEditViewModel;
import com.google.gson.Gson;

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

    private static ProfileDeviceCardFragment instance;
    private List<ConfigurationSceneEditData> configurationSceneEditData;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_device_card, container, false);

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
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), RGBLightActivity.class);
                           // intent.putExtra("extra_name", name);
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
        try {
            AppConstants.projectSpaceTypePlannedDeviceName = name;
            AppConstants.powerState = power;
            AppConstants.power = String.valueOf(powerState);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS1",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS2",""+AppConstants.Name_dyn);
            Log.e("APPCONSTS3",""+AppConstants.SceneRef);
            Log.e("APPCONSTS4",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS5",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS6",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS7",""+AppConstants.powerState);
            Log.e("APPCONSTS8",""+AppConstants.power);


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);
            SceneViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(SceneViewModel.class);
            // sharedViewModel.setObjecatSchedule(objectScenes);
            sharedViewModelEdit.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        // Create Scene
        try {
            AppConstants.Create_projectSpaceTypePlannedDeviceName = name;
            Log.e(TAG, "old: "+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            AppConstants.Create_powerState = power;
            AppConstants.Create_power = String.valueOf(powerState);
            Log.d("TAG", "PowerState2: " + AppConstants.Create_powerState);
            Log.d("TAG", "Power2: " + AppConstants.Create_power);

            Log.e("APPCONSTS9 Create Scene Ref_dyn_Schedule2",""+AppConstants.Create_Ref_dyn);
            Log.e("APPCONSTS10 Create Name_dyn_Schedule2",""+AppConstants.Create_Name_dyn);
            Log.e("APPCONSTS11 Space_dyn_Schedule2",""+AppConstants.Create_Space_dyn);
            Log.e("APPCONSTS12 SceneRef_Schedule2",""+AppConstants.Create_SceneRef);
            Log.e("APPCONSTS13 GaaProjectSpaceTypePlannedDeviceRef_Schedule",""+AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS14 projectSpaceTypePlannedDeviceName_Schedule2",""+AppConstants.Create_projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS15 powerState_Schedule",""+AppConstants.Create_powerState);
            Log.e("APPCONSTS16 power_Schedule",""+AppConstants.Create_power);


            ObjectSceneCreate objectSceneCreate = new ObjectSceneCreate(AppConstants.Create_Ref_dyn,AppConstants.Create_Name_dyn,AppConstants.Create_SceneRef,AppConstants.Create_Space_dyn,AppConstants.Create_projectSpaceTypePlannedDeviceName,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef,AppConstants.Create_powerState,AppConstants.Create_power);
            SceneCreateViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SceneCreateViewModel.class);
            sharedViewModel.addObjectScenes(objectSceneCreate);

        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        //// Create Schedule
        try {
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

            ObjectSchedule objectSchedule = new ObjectSchedule(AppConstants.Create_Ref_dyn_Schedule,AppConstants.Create_Name_dyn_Schedule,AppConstants.Create_ScheduleRef_Schedule,AppConstants.Create_Space_dyn_Schedule,AppConstants.Create_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Create_powerState_Schedule,AppConstants.Create_power_Schedule);
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

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }


        // Edit Schedule
        try {

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


            ObjectScheduleEdit objectScheduleEdit = new ObjectScheduleEdit(AppConstants.Edit_Ref_dyn_Schedule,AppConstants.Edit_Name_dyn_Schedule,AppConstants.Edit_ScheduleRef_Schedule,AppConstants.Edit_Space_dyn_Schedule,AppConstants.Edit_projectSpaceTypePlannedDeviceName_Schedule,AppConstants.Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule,AppConstants.Edit_powerState_Schedule,AppConstants.Edit_power_Schedule);
            ScheduleEditViewModel sharedViewModelEdit = new ViewModelProvider(requireActivity()).get(ScheduleEditViewModel.class);
            sharedViewModelEdit.addObjectScenes(objectScheduleEdit);

            // sharedViewModel.setObjectSchedule(objectScenes);
          //  sharedViewModel.addObjectScenes(objectScenes);

            Log.e(TAG, "sendSwitchState: "+objectScenes.getRef_dyn());
            //   objScenes.setRef_dyn(AppConstants.Ref_dyn);

//            List<SceneConfig> list = new ArrayList<>();
//            list.add(new SceneConfig(Long.parseLong(AppConstants.SceneRef),Long.parseLong(AppConstants.GaaProjectSpaceTypePlannedDeviceRef),AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.powerState,AppConstants.power));
//            list.size();
//            Log.e(TAG, "List Size: "+list.size());

            ////////////


        }
        catch (Exception e){
            Log.e(TAG, "sendSwitchState: "+e);
        }

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);



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