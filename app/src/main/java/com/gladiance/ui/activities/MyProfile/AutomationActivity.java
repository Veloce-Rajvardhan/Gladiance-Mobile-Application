package com.gladiance.ui.activities.MyProfile;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gladiance.R;
import com.gladiance.ui.activities.Home.NavBarActivity;

public class AutomationActivity extends AppCompatActivity {

    LinearLayout setYourMood;
    LinearLayout scheduling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation);

        setYourMood = findViewById(R.id.cd_setYourMood);
        scheduling = findViewById(R.id.cd_scheduling);

        setYourMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity here
                Intent intent = new Intent(AutomationActivity.this, SetYourMoodActivity.class);
                Log.e(TAG, "onClick: ");
                startActivity(intent);
            }
        });

        // You can also set click listener for scheduling button if needed
        scheduling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutomationActivity.this, ScheduleActivity.class);
                Log.e(TAG, "onClick: " );
                startActivity(intent);
                // Handle click for scheduling button if needed
            }
        });

    }

}