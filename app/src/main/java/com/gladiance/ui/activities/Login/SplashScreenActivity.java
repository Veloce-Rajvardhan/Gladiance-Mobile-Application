package com.gladiance.ui.activities.Login;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gladiance.R;
import com.gladiance.ui.activities.Home.ProjectSpaceActivity;

public class SplashScreenActivity extends AppCompatActivity {

    Animation a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
//        String GUID = LoginActivity.getUserId(sharedPreferences);
//        Log.e(TAG, "SplashScreen GUID/LoginDeviceId: " + GUID);
//        String loginDeviceId = GUID.trim();
//
//        SharedPreferences sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
//        Log.e(TAG, "SplashScreen loginToken: " + savedLoginDeviceId);
//        String loginToken = savedLoginDeviceId.trim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String GUID = LoginActivity.getUserId(sharedPreferences);
                String loginDeviceId = GUID != null ? GUID.trim() : null;

                SharedPreferences sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
                String loginToken = savedLoginDeviceId != null ? savedLoginDeviceId.trim() : null;

                if (loginDeviceId != null && !loginDeviceId.isEmpty() && loginToken != null && !loginToken.isEmpty()) {
                    // If user is logged in, navigate directly to ProjectSpaceActivity
                    Intent intent = new Intent(SplashScreenActivity.this, ProjectSpaceActivity.class);
                    startActivity(intent);
                } else {
                    // If user is not logged in, navigate to LoginActivity
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 1000);
    }




}