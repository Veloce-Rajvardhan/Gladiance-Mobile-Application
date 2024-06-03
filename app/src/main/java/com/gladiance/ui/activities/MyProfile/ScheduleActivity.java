package com.gladiance.ui.activities.MyProfile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gladiance.R;
import com.gladiance.ui.fragment.MyProfile.CreateScheduleFragment;
import com.gladiance.ui.fragment.MyProfile.InvitedUserFragment;
import com.gladiance.ui.fragment.MyProfile.MyScheduleFragment;

public class ScheduleActivity extends AppCompatActivity {

    private static final String TAG = "InviteUserActivity";
    Button mySchedule;
    Button createSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mySchedule = findViewById(R.id.inviteUser);
        createSchedule = findViewById(R.id.invitedUser);


        // Load the FirstFragment by default
        mySchedule.setBackgroundResource(R.drawable.orange_transperant_bg_left);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FlSchedule, new MyScheduleFragment())
                .commit();

        mySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySchedule.setBackgroundResource(R.drawable.orange_transperant_bg_left);

                createSchedule.setBackgroundResource(R.drawable.transparent_backgraund);
                Fragment fragment = new MyScheduleFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.FlSchedule, fragment).addToBackStack(null)
                        .commit();
            }
        });


        createSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSchedule.setBackgroundResource(R.drawable.orange_transperant_bg_right);

                mySchedule.setBackgroundResource(R.drawable.transparent_backgraund);
                Fragment fragment = new CreateScheduleFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.FlSchedule, fragment).addToBackStack(null)
                        .commit();
            }
        });


//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent intent = new Intent(ScheduleActivity.this, BasicInfoActivity.class);
//                startActivity(intent);
//                finish(); // Optionally finish this activity
//            }
//        };
//        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}