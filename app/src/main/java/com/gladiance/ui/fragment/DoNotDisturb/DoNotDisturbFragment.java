package com.gladiance.ui.fragment.DoNotDisturb;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.ControlBouquet.HouseKeepingActivity;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.models.PrivacyOnOffResponse;
import com.gladiance.ui.models.privacystatus.PrivacyStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoNotDisturbFragment extends Fragment {


    public DoNotDisturbFragment() {
        // Required empty public constructor
    }

    TextView textView;
    Switch aSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_not_disturb, container, false);

        textView = view.findViewById(R.id.privacy);
        aSwitch = view.findViewById(R.id.switchButtonDimmer);

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

        SharedPreferences sharedPreferencesService = requireContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        boolean switchStateService = sharedPreferencesService.getBoolean("switchStateService", false);
        Log.e(TAG, "Privacy SwitchState: " + switchStateService );


        textView.setText(aSwitch.isChecked() ? "DND ACTIVATED" : "DND DEACTIVATED");

        getPrivacyStatus(projectSpaceRef,loginToken,loginDeviceId);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switchStatePrivacy", isChecked);
                editor.apply();

                // Log the switch state
                Log.e(TAG, "Privacy SwitchState changed: " + isChecked);

                if (isChecked) {
                    SharedPreferences sharedPreferencesPrivacy = requireActivity().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                    boolean switchStatePrivacy = sharedPreferencesPrivacy.getBoolean("switchStateService", false);
                    if (switchStatePrivacy) {
                        showCustomDialogBoxEmergency("Service Mode is Activated. Are you sure you want to activate the privacy Mode?");
                    } else {
                        activatePrivacyMode(projectSpaceRef, loginToken, loginDeviceId);
                    }
                } else {
                    deactivatePrivacyMode(projectSpaceRef, loginToken, loginDeviceId);
                }
            }
        });

        return view;
    }

    private void getPrivacyStatus(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<PrivacyStatusResponse> call = apiService.getPrivacyStatus(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<PrivacyStatusResponse>() {
            @Override
            public void onResponse(Call<PrivacyStatusResponse> call, Response<PrivacyStatusResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isActivated = response.body().getObjectTag().getPrivacyModeActivated();
                    aSwitch.setChecked(isActivated);

                }
            }

            @Override
            public void onFailure(Call<PrivacyStatusResponse> call, Throwable t) {

            }
        });
    }



    private void activatePrivacyMode(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<PrivacyOnOffResponse> call = apiService.activatePrivacyMode(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<PrivacyOnOffResponse>() {
            @Override
            public void onResponse(Call<PrivacyOnOffResponse> call, Response<PrivacyOnOffResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PrivacyOnOffResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        textView.setText("DND ACTIVATED");
                        Toast.makeText(requireContext(), "Privacy Mode Activated", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(requireContext(), "Failed to Activate Privacy Mode", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrivacyOnOffResponse> call, Throwable t) {

            }
        });
    }

    private void deactivatePrivacyMode(String gaaProjectSpaceRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<PrivacyOnOffResponse> call = apiService.deactivatePrivacyMode(gaaProjectSpaceRef, loginToken, loginDeviceId);
        call.enqueue(new Callback<PrivacyOnOffResponse>() {
            @Override
            public void onResponse(Call<PrivacyOnOffResponse> call, Response<PrivacyOnOffResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PrivacyOnOffResponse apiResponse = response.body();
                    if (apiResponse.isSuccessful()) {
                        textView.setText("DND DEACTIVATED");
                        Toast.makeText(requireContext(), "Privacy Mode Deactivated", Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(requireContext(), "Failed to Deactivate Privacy Mode", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrivacyOnOffResponse> call, Throwable t) {

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


                activatePrivacyMode(projectSpaceRef, loginToken, loginDeviceId);
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.setChecked(false);
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

                bottomNavigation.show(5, true);
                requireActivity().onBackPressed();
                return true; // Consumes the back button press event
            }
            return false; // Otherwise, let the system handle it
        });
    }
}