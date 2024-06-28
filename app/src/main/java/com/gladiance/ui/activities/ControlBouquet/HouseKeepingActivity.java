package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.models.PrivacyOnOffResponse;
import com.gladiance.ui.models.ServiceOnOffResponse;
import com.gladiance.ui.models.privacystatus.PrivacyStatusResponse;
import com.gladiance.ui.models.servicestatus.ServiceStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseKeepingActivity extends AppCompatActivity {

    TextView textView;
    Switch switchHouseKeeping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_keeping);

        switchHouseKeeping = findViewById(R.id.switchButtonHouseKeeping);
        textView = findViewById(R.id.service);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences  sharedPreferences5 = getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        textView.setText(switchHouseKeeping.isChecked() ? "Service ACTIVATED" : "Service DEACTIVATED");

        getServiceStatus(projectSpaceRef,loginToken,loginDeviceId);

        switchHouseKeeping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textView.setText("Service ACTIVATED");
                    activateServiceMode(projectSpaceRef,loginToken,loginDeviceId);
                } else {
                    textView.setText("Service DEACTIVATED");
                    deactivateServiceMode(projectSpaceRef,loginToken,loginDeviceId);
                }
            }
        });


    }



    private void getServiceStatus(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ServiceStatusResponse> call = apiService.getServiceStatus(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<ServiceStatusResponse>() {
            @Override
            public void onResponse(Call<ServiceStatusResponse> call, Response<ServiceStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isActivated = response.body().getObjectTag().getServiceModeActivated();
                    switchHouseKeeping.setChecked(isActivated);
                }
            }

            @Override
            public void onFailure(Call<ServiceStatusResponse> call, Throwable t) {

            }
        });
    }

    private void activateServiceMode(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ServiceOnOffResponse> call = apiService.activateServiceMode(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<ServiceOnOffResponse>() {
            @Override
            public void onResponse(Call<ServiceOnOffResponse> call, Response<ServiceOnOffResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ServiceOnOffResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        textView.setText("Service ACTIVATED");
                        Toast.makeText(HouseKeepingActivity.this, "Service Mode Activated", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(requireContext(), "Failed to Activate Privacy Mode", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceOnOffResponse> call, Throwable t) {

            }
        });
    }

    private void deactivateServiceMode(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ServiceOnOffResponse> call = apiService.deactivateServiceMode(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<ServiceOnOffResponse>() {
            @Override
            public void onResponse(Call<ServiceOnOffResponse> call, Response<ServiceOnOffResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ServiceOnOffResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        textView.setText("Service DEACTIVATED");
                        Toast.makeText(HouseKeepingActivity.this, "Service Mode Deactivated", Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(requireContext(), "Failed to Deactivate Privacy Mode", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceOnOffResponse> call, Throwable t) {

            }
        });
    }


}