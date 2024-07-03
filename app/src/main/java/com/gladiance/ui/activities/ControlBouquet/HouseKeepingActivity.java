package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switchStateService", isChecked);
                editor.apply();

                Log.e(TAG, "Service SwitchState saved: " + isChecked);

                if (isChecked) {
                    SharedPreferences sharedPreferencesPrivacy = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                    boolean switchStatePrivacy = sharedPreferencesPrivacy.getBoolean("switchStatePrivacy", false);
                    if (switchStatePrivacy) {
                        showCustomDialogBoxEmergency("Privacy Mode Is Activated. Are you sure you want to activate the Service Mode?");
                    } else {
                        activateServiceMode(projectSpaceRef, loginToken, loginDeviceId);
                    }
                } else {
                    deactivateServiceMode(projectSpaceRef, loginToken, loginDeviceId);
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

    TextView tvMessage;
    Button btnYes, btnNo;

    private void showCustomDialogBoxEmergency(String message) {
        final Dialog dialog = new Dialog(HouseKeepingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.favorite_custom_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvMessage = dialog.findViewById(R.id.tvMessage);
        btnYes = dialog.findViewById(R.id.btn_Yes);
        btnNo = dialog.findViewById(R.id.btn_No);

        tvMessage.setText(message);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences1);
                Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
                String loginDeviceId = GUID.trim();


                SharedPreferences  sharedPreferences2 = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
                String loginToken = savedLoginDeviceId.trim();

                SharedPreferences  sharedPreferences3 = getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
                String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
                Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
                String projectSpaceRef = saveProjectSpaceRef.trim();


                activateServiceMode(projectSpaceRef, loginToken, loginDeviceId);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchHouseKeeping.setChecked(false);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}