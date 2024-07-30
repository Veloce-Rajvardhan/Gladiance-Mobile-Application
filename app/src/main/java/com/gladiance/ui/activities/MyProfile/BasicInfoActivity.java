package com.gladiance.ui.activities.MyProfile;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.gladiance.ui.models.LogoutRequestModel;
import com.gladiance.ui.models.LogoutResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicInfoActivity extends AppCompatActivity {

    LinearLayout llChangePassword;
    LinearLayout llInviteUser;
    LinearLayout llJoinedUser;
    LinearLayout llJoinUser;
    LinearLayout llLogout;
    LinearLayout editProfile;

    TextView tvMessage,tv_user_name;

    Button btnYes, btnNo;

    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        tv_user_name = findViewById(R.id.tv_user_name);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "BaLoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();

        SharedPreferences sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: " + savedLoginDeviceId);
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences3 = getSharedPreferences("MyPreferencesDN", Context.MODE_PRIVATE);
        String savedUserDeviceName = sharedPreferences3.getString("UserDisplayName", "");
        tv_user_name.setText(savedUserDeviceName);
        Log.e(ContentValues.TAG, "My Profile User Name: "+savedUserDeviceName );

        llChangePassword = findViewById(R.id.llChangePassword);
        llChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicInfoActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        llInviteUser = findViewById(R.id.llInviteUser);
        llInviteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicInfoActivity.this, InviteUserActivity.class);
                startActivity(intent);
            }
        });

        llJoinedUser = findViewById(R.id.llJoinedUser);
        llJoinedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicInfoActivity.this, JoinedUserActivity.class);
                startActivity(intent);
            }
        });


        llJoinUser = findViewById(R.id.llJoinUser);
        llJoinUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicInfoActivity.this, JoinUserActivity.class);
                startActivity(intent);
            }
        });

        llLogout = findViewById(R.id.llLogout);
        editProfile = findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicInfoActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Are you sure you want to log out from gladiance ?";
                showCustomDialogBox(message);
            }

            private void showCustomDialogBox(String message) {
                final Dialog dialog = new Dialog(BasicInfoActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.layout_custom_dailog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tvMessage = dialog.findViewById(R.id.tvMessage);
                btnYes = dialog.findViewById(R.id.btn_Yes);
                btnNo = dialog.findViewById(R.id.btn_No);

                tvMessage.setText(message);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logoutUser(loginToken, loginDeviceId);
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
        });
    }

    private void logoutUser(String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        LogoutRequestModel request = new LogoutRequestModel(loginToken, loginDeviceId);

        Call<LogoutResponseModel> call = apiService.logoutUser(request);
        call.enqueue(new Callback<LogoutResponseModel>() {
            @Override
            public void onResponse(Call<LogoutResponseModel> call, Response<LogoutResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LogoutResponseModel logoutResponse = response.body();
                    if (logoutResponse.isSuccessful()) {
                        Toast.makeText(BasicInfoActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor1 = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                        editor1.clear();
                        editor1.apply();

                        SharedPreferences.Editor editor2 = getSharedPreferences("MyPreferences", MODE_PRIVATE).edit();
                        editor2.clear();
                        editor2.apply();

                        finishAffinity();
                    } else {
                        Toast.makeText(BasicInfoActivity.this, "Logout failed: " + logoutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BasicInfoActivity.this, "Failed to logout", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponseModel> call, Throwable t) {
                Toast.makeText(BasicInfoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}