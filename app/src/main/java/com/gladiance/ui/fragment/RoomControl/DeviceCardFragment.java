package com.gladiance.ui.fragment.RoomControl;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.gladiance.ui.activities.EspMainActivity;
import com.gladiance.ui.adapters.CardAdapter;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.Devices;
import com.gladiance.ui.models.saveScene.SceneConfig;
import com.gladiance.ui.models.scene.ObjectScenes;
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
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.fan")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), FanActivity.class);
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.curtain")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), CurtainActivity.class);
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.light")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), DimmerActivity.class);
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("esp.device.bellcontrol")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), BellActivity.class);
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.ther")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), AirContiningActivity.class);
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.bell")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), BellActivity.class);
                            intent.putExtra("extra_name", name);
                            startActivity(intent);
                        }else if(arrayList.get(0).getType().equals("e.d.curt")){
                            String name = arrayList.get(0).getName();
                            Intent intent = new Intent(requireContext(), CurtainActivity.class);
                            intent.putExtra("extra_name", name);
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
        Log.e(TAG, "EspMain Activity PowerState:"+shPowerState );

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        try {
            AppConstants.powerState = power;
            AppConstants.power = String.valueOf(powerState);
            Log.d("TAG", "PowerState: " + AppConstants.powerState);
            Log.d("TAG", "Power: " + AppConstants.power);

            Log.e("APPCONSTS",""+AppConstants.Ref_dyn);
            Log.e("APPCONSTS",""+ AppConstants.Name_dyn);
            Log.e("APPCONSTS",""+AppConstants.SceneRef);
            Log.e("APPCONSTS",""+AppConstants.Space_dyn);
            Log.e("APPCONSTS",""+AppConstants.projectSpaceTypePlannedDeviceName);
            Log.e("APPCONSTS",""+AppConstants.GaaProjectSpaceTypePlannedDeviceRef);
            Log.e("APPCONSTS",""+AppConstants.powerState);
            Log.e("APPCONSTS",""+AppConstants.power);


            ObjectScenes objectScenes = new ObjectScenes(AppConstants.Ref_dyn,AppConstants.Name_dyn,AppConstants.SceneRef,AppConstants.Space_dyn,AppConstants.projectSpaceTypePlannedDeviceName,AppConstants.GaaProjectSpaceTypePlannedDeviceRef,AppConstants.powerState,AppConstants.power);

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

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);



    }

}