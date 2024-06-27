package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.EmergencyAdapter;
import com.gladiance.ui.models.SafetyResponse;
import com.gladiance.ui.models.SecurityResponse;
import com.gladiance.ui.models.emergencystatus.EmergencyStatusRes;
import com.gladiance.ui.models.safetystatus.SafetyStatusRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SafetyActivity extends AppCompatActivity {

    TextView textViewPolice;
    LinearLayout llPolice,llFie,llHospital,llOther;
    private boolean isSafetyActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sefety);

        textViewPolice = findViewById(R.id.TextPolice);
        llPolice = findViewById(R.id.llPolice);
        llFie = findViewById(R.id.llFire);
        llHospital = findViewById(R.id.llHospital);
        llOther = findViewById(R.id.llOther);



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

        getSafetyStatus(projectSpaceRef,loginToken,loginDeviceId);


        llPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSafetyActive) {
                    String message = "Are you sure you want to Deactivate Safety";
                    showCustomDialogBoxEmergency(message);
                    llPolice.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                } else {
                    activeSafety(projectSpaceRef,"100", loginToken, loginDeviceId);
                    llPolice.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                }

            }
        });

        llFie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSafetyActive) {
                    String message = "Are you sure you want to Deactivate Safety";
                    showCustomDialogBoxEmergency(message);
                    llFie.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                } else {
                    activeSafety(projectSpaceRef,"200", loginToken, loginDeviceId);
                    llFie.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                }

            }
        });

        llHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSafetyActive) {
                    String message = "Are you sure you want to Deactivate Safety";
                    showCustomDialogBoxEmergency(message);
                    llHospital.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                } else {
                    activeSafety(projectSpaceRef,"300", loginToken, loginDeviceId);
                    llHospital.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                }

            }
        });

        llOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSafetyActive) {
                    String message = "Are you sure you want to Deactivate Safety";
                    showCustomDialogBoxEmergency(message);
                    llOther.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                } else {
                    activeSafety(projectSpaceRef,"0", loginToken, loginDeviceId);
                    llOther.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                }

            }
        });
    }


    private void activeSafety(String gaaProjectSpaceRef,String safetyRequestType,String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SafetyResponse> call = apiService.raiseSafetyRequest(gaaProjectSpaceRef,safetyRequestType,loginToken,loginDeviceId);
        call.enqueue(new Callback<SafetyResponse>() {
            @Override
            public void onResponse(Call<SafetyResponse> call, Response<SafetyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SafetyResponse apiResponse = response.body();
                    if (apiResponse.getSuccessful()) {
                        Toast.makeText(SafetyActivity.this, "Safety Activated", Toast.LENGTH_SHORT).show();
                        isSafetyActive = true;
                    } else {
                        Toast.makeText(SafetyActivity.this, "Failed to Activate Safety", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SafetyResponse> call, Throwable t) {
                Toast.makeText(SafetyActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deactivateSafety(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SafetyResponse> call = apiService.cancelSafetyRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<SafetyResponse>() {
            @Override
            public void onResponse(Call<SafetyResponse> call, Response<SafetyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SafetyResponse apiResponse = response.body();
                    if (apiResponse.getSuccessful()) {
                        Toast.makeText(SafetyActivity.this, "Safety Deactivated", Toast.LENGTH_SHORT).show();
                        isSafetyActive = false;

                    } else {
                        Toast.makeText(SafetyActivity.this, "Failed to Deactivate Safety", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SafetyResponse> call, Throwable t) {
                Toast.makeText(SafetyActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSafetyStatus(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SafetyStatusRes> call = apiService.getPendingSafetyRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<SafetyStatusRes>() {
            @Override
            public void onResponse(Call<SafetyStatusRes> call, Response<SafetyStatusRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SafetyStatusRes safetyStatusRes = response.body();
                    boolean isTriggered = safetyStatusRes.getObjectTag().getTriggered();
                    String safetyRequestType = String.valueOf(safetyStatusRes.getObjectTag().getSafetyRequestType());

                    if (isTriggered) {
                        isSafetyActive = true;
                        switch (safetyRequestType) {
                            case "100":
                                llPolice.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                                break;
                            case "200":
                                llFie.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                                break;
                            case "300":
                                llHospital.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                                break;
                            case "0":
                                llOther.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                                break;
                        }
                    } else {
                        isSafetyActive = false;
                        resetAllBackgrounds();
                    }
                }
            }

            @Override
            public void onFailure(Call<SafetyStatusRes> call, Throwable t) {
                Toast.makeText(SafetyActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetAllBackgrounds() {
        llPolice.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
        llFie.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
        llHospital.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
        llOther.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
    }
    TextView tvMessage;
    Button btnYes, btnNo;

    private void showCustomDialogBoxEmergency(String message) {
        final Dialog dialog = new Dialog(SafetyActivity.this);
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


                deactivateSafety(projectSpaceRef, loginToken, loginDeviceId);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}