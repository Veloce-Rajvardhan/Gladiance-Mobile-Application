package com.gladiance.ui.activities.RoomControl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.gladiance.NetworkApiManager;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.adapters.CardAdapter;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.Devices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceCardActivity extends AppCompatActivity {
    private static final String TAG = "DeviceCardActivity";

    private ArrayList<Devices> arrayList;
    RecyclerView recyclerView;
    String nodeId2;
    NetworkApiManager networkApiManager;
    private EspApplication espApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_card);

        recyclerView = findViewById(R.id.recycleViewCard);

        espApp = new EspApplication(this);
        networkApiManager = new NetworkApiManager(getApplicationContext(), espApp);

        arrayList = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        nodeId2 = preferences.getString("KEY_USERNAMEs", "");

        getDevice(nodeId2);
    }


    private void getDevice(String nodeId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<DeviceInfo> call = apiService.getAllData(nodeId);
        Log.e(TAG, "getDevice: " + nodeId);

        call.enqueue(new Callback<DeviceInfo>() {
            @Override
            public void onResponse(Call<DeviceInfo> call, Response<DeviceInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Gson gson = new Gson();
                    String n = gson.toJson(response.body());

                    SharedPreferences sharedPreferences = getSharedPreferences("my_shared_prefet", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("KEY_USERNAMEst", n);
                    editor.apply();

                    List<DeviceInfo.Device> devices = response.body().getDevices();
                    for (DeviceInfo.Device device : devices) {
                        List<DeviceInfo.Param> params = device.getParams();
                        for (DeviceInfo.Param param : params) {
                            String name = param.getUi_type();
                            String uiType = param.getUi_type();
                        }
                        arrayList.add(new Devices(device.getName(), device.getType(), device.getPrimary()));
                    }
                    CardAdapter cardAdapter = new CardAdapter(arrayList);
                    recyclerView.setAdapter(cardAdapter);
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(DeviceCardActivity.this, 2, GridLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager1);
                } else {
                    // Handle unsuccessful response
                }

            }

            @Override
            public void onFailure(Call<DeviceInfo> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void sendSwitchState(boolean powerState, String name, String power) {
        String commandBody = "{\"" + name + "\": {\"" + power + "\": " + powerState + "}}";
        String message = powerState ? "on" : "off";
        Toast.makeText(this, "Switch is " + message, Toast.LENGTH_SHORT).show();
        boolean shPowerState = powerState;
        SharedPreferences sharedPreferencesPowerState = getSharedPreferences("MyPreferencesPS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesPowerState.edit();
        editor.putBoolean("PowerState", shPowerState);
        editor.apply();
        Log.e(TAG, "EspMain Activity PowerState:" + shPowerState);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }
}
