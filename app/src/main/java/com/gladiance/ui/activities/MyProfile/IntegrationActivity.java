package com.gladiance.ui.activities.MyProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gladiance.R;

public class IntegrationActivity extends AppCompatActivity {

    LinearLayout llVoiceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration);

        llVoiceService = findViewById(R.id.voiceService);

        llVoiceService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntegrationActivity.this, VoiceServicesActivity.class);
                startActivity(intent);
            }
        });
    }
}