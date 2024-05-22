package com.gladiance.ui.activities.MyProfile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.gladiance.R;

public class JoinUserActivity extends AppCompatActivity {

    private static final String TAG = "JoinUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_user);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(JoinUserActivity.this, BasicInfoActivity.class);
                startActivity(intent);
                finish(); // Optionally finish this activity
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}