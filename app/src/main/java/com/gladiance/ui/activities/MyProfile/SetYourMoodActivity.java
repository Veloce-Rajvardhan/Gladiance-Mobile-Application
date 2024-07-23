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
import com.gladiance.ui.fragment.MyProfile.CreateMoodFragment;
import com.gladiance.ui.fragment.MyProfile.InviteUserFragment;
import com.gladiance.ui.fragment.MyProfile.InvitedUserFragment;
import com.gladiance.ui.fragment.MyProfile.MyMoodFragment;

public class SetYourMoodActivity extends AppCompatActivity {

    private static final String TAG = "SetYourMoodActivity";
    Button inviteUser;
    Button invitedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_your_mood);


        inviteUser = findViewById(R.id.inviteUser2);
        invitedUser = findViewById(R.id.invitedUser2);

        // Load the FirstFragment by default
        inviteUser.setBackgroundResource(R.drawable.orange_transperant_bg_left);
        invitedUser.setBackgroundResource(R.drawable.transparent_backgraund_right);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.set_mood, new MyMoodFragment())
                .commit();


        inviteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteUser.setBackgroundResource(R.drawable.orange_transperant_bg_left);

                invitedUser.setBackgroundResource(R.drawable.transparent_backgraund_right);

                Fragment fragment = new MyMoodFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.set_mood, fragment).addToBackStack(null)
                        .commit();
            }
        });

//        invitedUser.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//
//                Fragment fragment = new MyMoodFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.set_mood, fragment).addToBackStack(null).commit();
//            }
//        });
        invitedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invitedUser.setBackgroundResource(R.drawable.orange_transperant_bg_right);

                inviteUser.setBackgroundResource(R.drawable.transparent_backgraund_left);

                Fragment fragment = new CreateMoodFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.set_mood, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent intent = new Intent(SetYourMoodActivity.this, AutomationActivity.class);
//                startActivity(intent);
//                finish(); // Optionally finish this activity
//            }
//        };
//        getOnBackPressedDispatcher().addCallback(this, callback);

    }
}