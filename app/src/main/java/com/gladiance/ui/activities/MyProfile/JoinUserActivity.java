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
import com.gladiance.ui.fragment.MyProfile.InviteUserFragment;
import com.gladiance.ui.fragment.MyProfile.InvitedUserFragment;
import com.gladiance.ui.fragment.MyProfile.JoinedSpacesFragment;
import com.gladiance.ui.fragment.MyProfile.RequestedSpacesFragment;

public class JoinUserActivity extends AppCompatActivity {

    private static final String TAG = "JoinUserActivity";

    Button inviteUser;
    Button invitedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_user);

        inviteUser = findViewById(R.id.inviteUser);
        invitedUser = findViewById(R.id.invitedUser);


        inviteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new RequestedSpacesFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.set_mood, fragment).addToBackStack(null)
                        .commit();
            }
        });

        invitedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new JoinedSpacesFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.set_mood, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


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