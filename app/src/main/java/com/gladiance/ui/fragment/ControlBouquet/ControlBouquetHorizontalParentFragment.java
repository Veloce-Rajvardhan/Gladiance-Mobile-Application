package com.gladiance.ui.fragment.ControlBouquet;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.ControlBouquet.AddLaundryRequestActivity;
import com.gladiance.ui.activities.ControlBouquet.BillViewActivity;
import com.gladiance.ui.activities.ControlBouquet.LaundryActivity;
import com.gladiance.ui.activities.ControlBouquet.RoomServiceDetailsActivity;
import com.gladiance.ui.activities.ControlBouquet.RoomServiceListActivity;
import com.gladiance.ui.activities.ControlBouquet.SurveillanceActivity;
import com.gladiance.ui.activities.ControlBouquet.SafetyActivity;
import com.gladiance.ui.activities.ControlBouquet.FeedbackActivity;
import com.gladiance.ui.activities.ControlBouquet.HotelInfoActivity;
import com.gladiance.ui.activities.ControlBouquet.HouseKeepingActivity;
import com.gladiance.ui.activities.ControlBouquet.MessagingActivity;
import com.gladiance.ui.activities.ControlBouquet.PromotionActivity;
import com.gladiance.R;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.models.EmergencyResponse;
import com.gladiance.ui.models.SecurityResponse;
import com.gladiance.ui.models.emergencystatus.EmergencyStatusRes;
import com.gladiance.ui.models.securitystatus.SecurityStatusRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ControlBouquetHorizontalParentFragment extends Fragment  {

    CardView CVHouseKipping,CVRoomService,CVLaundry,CVBillView,CVFeedback,CVHotelInfo,CVPromotion,CVMessaging,CVSurveillance,
                CVEmergency,CVSafety,CVSecurity;

    private boolean isEmergencyActive = false;
    private boolean isSecurityActive = false;

    public ControlBouquetHorizontalParentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_bouquet_horizontal_parent, container, false);

        CVHouseKipping = view.findViewById(R.id.houseKipping);
        CVRoomService = view.findViewById(R.id.roomService);
        CVLaundry = view.findViewById(R.id.laundry);
        CVBillView = view.findViewById(R.id.billView);
        CVFeedback = view.findViewById(R.id.feedback);
        CVHotelInfo = view.findViewById(R.id.hotelInfo);
        CVPromotion = view.findViewById(R.id.promotion);
        CVMessaging = view.findViewById(R.id.messaging);
        CVSurveillance = view.findViewById(R.id.surveillance);
        CVEmergency = view.findViewById(R.id.emergency);
        CVSafety = view.findViewById(R.id.safety);
        CVSecurity = view.findViewById(R.id.security);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences  sharedPreferences5 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
        String projectSpaceRef = saveProjectSpaceRef.trim();

        getEmergencyStatus(projectSpaceRef,loginToken,loginDeviceId);
        getSecurityStatus(projectSpaceRef,loginToken,loginDeviceId);

        CVHouseKipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HouseKeepingActivity.class);
                startActivity(intent);
            }
        });

        CVRoomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomServiceListActivity.class);
                startActivity(intent);
            }
        });

        CVLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LaundryActivity.class);
                startActivity(intent);
            }
        });

        CVBillView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BillViewActivity.class);
                startActivity(intent);
            }
        });

        CVFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        CVHotelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelInfoActivity.class);
                startActivity(intent);
            }
        });

        CVPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PromotionActivity.class);
                startActivity(intent);
            }
        });

        CVMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessagingActivity.class);
                startActivity(intent);
            }
        });

        CVSurveillance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SurveillanceActivity.class);
                startActivity(intent);
            }
        });

        CVEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmergencyActive) {
                    String message = "Are you sure you want to Deactivate Emergency";
                    showCustomDialogBoxEmergency(message);
                    CVEmergency.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                } else {
                    activeEmergency(projectSpaceRef, loginToken, loginDeviceId);
                    CVEmergency.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                }
            }
        });

        CVSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SafetyActivity.class);
                startActivity(intent);
            }
        });

        CVSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSecurityActive){
                    String message = "Are you sure you want to Deactivate Security";
                    showCustomDialogBoxSecurity(message);
                    CVSecurity.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                }else {
                    activeSecurity(projectSpaceRef,loginToken,loginDeviceId);
                    CVSecurity.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                }
            }
        });


        return view;
    }

    private void activeEmergency(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<EmergencyResponse> call = apiService.raiseEmergencyRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<EmergencyResponse>() {
            @Override
            public void onResponse(Call<EmergencyResponse> call, Response<EmergencyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmergencyResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        Toast.makeText(requireContext(), "Emergency Activated", Toast.LENGTH_SHORT).show();
                        isEmergencyActive = true;
                        CVEmergency.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                    } else {
                        Toast.makeText(requireContext(), "Failed to Activate Emergency", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EmergencyResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deactivateEmergency(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<EmergencyResponse> call = apiService.cancelEmergencyRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<EmergencyResponse>() {
            @Override
            public void onResponse(Call<EmergencyResponse> call, Response<EmergencyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmergencyResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        Toast.makeText(requireContext(), "Emergency Deactivated", Toast.LENGTH_SHORT).show();
                        isEmergencyActive = false;
                        CVEmergency.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                    } else {
                        Toast.makeText(requireContext(), "Failed to Deactivate Emergency", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EmergencyResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEmergencyStatus(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<EmergencyStatusRes> call = apiService.getPendingEmergencyRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<EmergencyStatusRes>() {
            @Override
            public void onResponse(Call<EmergencyStatusRes> call, Response<EmergencyStatusRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EmergencyStatusRes emergencyResponse = response.body();
                    boolean isTriggered = emergencyResponse.getObjectTag().getTriggered();
                    if (isTriggered) {
                        isEmergencyActive = true;
                        CVEmergency.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                    } else {
                        isEmergencyActive = false;
                        CVEmergency.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                    }
                }
            }

            @Override
            public void onFailure(Call<EmergencyStatusRes> call, Throwable t) {
                Toast.makeText(requireContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void activeSecurity(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SecurityResponse> call = apiService.raiseSecurityRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<SecurityResponse>() {
            @Override
            public void onResponse(Call<SecurityResponse> call, Response<SecurityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SecurityResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        Toast.makeText(requireContext(), "Security Activated", Toast.LENGTH_SHORT).show();
                        isSecurityActive = true;
                        CVSecurity.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                    } else {
                        Toast.makeText(requireContext(), "Failed to Activate Security", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SecurityResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deactivateSecurity(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SecurityResponse> call = apiService.cancelSecurityRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<SecurityResponse>() {
            @Override
            public void onResponse(Call<SecurityResponse> call, Response<SecurityResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SecurityResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        Toast.makeText(requireContext(), "Security Deactivated", Toast.LENGTH_SHORT).show();
                        isSecurityActive = false;
                        CVSecurity.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                    } else {
                        Toast.makeText(requireContext(), "Failed to Deactivate Security", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SecurityResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSecurityStatus(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<SecurityStatusRes> call = apiService.getPendingSecurityRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<SecurityStatusRes>() {
            @Override
            public void onResponse(Call<SecurityStatusRes> call, Response<SecurityStatusRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SecurityStatusRes emergencyResponse = response.body();
                    boolean isTriggered = emergencyResponse.getObjectTag().getTriggered();
                    if (isTriggered) {
                        isSecurityActive = true;
                        CVSecurity.setBackgroundResource(R.drawable.transparent_orange_emergency_bg);
                    } else {
                        isSecurityActive = false;
                        CVSecurity.setBackgroundResource(R.drawable.transparent_backgraund_emergency);
                    }
                }
            }

            @Override
            public void onFailure(Call<SecurityStatusRes> call, Throwable t) {
                Toast.makeText(requireContext(), "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    TextView tvMessage;
    Button btnYes, btnNo;

    private void showCustomDialogBoxEmergency(String message) {
        final Dialog dialog = new Dialog(requireContext());
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

                SharedPreferences sharedPreferences1 = requireContext().getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences1);
                Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
                String loginDeviceId = GUID.trim();


                SharedPreferences  sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
                String loginToken = savedLoginDeviceId.trim();

                SharedPreferences  sharedPreferences3 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
                String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
                Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
                String projectSpaceRef = saveProjectSpaceRef.trim();


                deactivateEmergency(projectSpaceRef, loginToken, loginDeviceId);
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

    private void showCustomDialogBoxSecurity(String message) {
        final Dialog dialog = new Dialog(requireContext());
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

                SharedPreferences sharedPreferences1 = requireContext().getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences1);
                Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
                String loginDeviceId = GUID.trim();


                SharedPreferences  sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
                String loginToken = savedLoginDeviceId.trim();

                SharedPreferences  sharedPreferences3 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
                String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
                Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
                String projectSpaceRef = saveProjectSpaceRef.trim();


                deactivateSecurity(projectSpaceRef, loginToken, loginDeviceId);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                MeowBottomNavigation bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation);

                bottomNavigation.show(1, true);
                requireActivity().onBackPressed();
                return true; // Consumes the back button press event
            }
            return false; // Otherwise, let the system handle it
        });
    }


}