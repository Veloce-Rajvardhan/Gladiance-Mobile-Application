package com.gladiance.ui.activities.MyProfile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gladiance.R;
import com.gladiance.ui.fragment.MyProfile.CreateMoodFragment;
import com.gladiance.ui.fragment.MyProfile.CreateProjectFragment;
import com.gladiance.ui.fragment.MyProfile.MyMoodFragment;
import com.gladiance.ui.fragment.MyProfile.MyProjectFragment;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";

    Button myProject;
    Button createProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        myProject = findViewById(R.id.myProject);
        createProject = findViewById(R.id.createProject);

        // Load the FirstFragment by default
        myProject.setBackgroundResource(R.drawable.orange_transperant_bg_left);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.project_fragment, new MyProjectFragment())
                .commit();


        myProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProject.setBackgroundResource(R.drawable.orange_transperant_bg_left);

                createProject.setBackgroundResource(R.drawable.transparent_backgraund);

                Fragment fragment = new MyProjectFragment();
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.project_fragment, fragment).addToBackStack(null)
                        .commit();


            }
        });

        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createProject.setBackgroundResource(R.drawable.orange_transperant_bg_right);

                myProject.setBackgroundResource(R.drawable.transparent_backgraund);


                Fragment fragment = new CreateProjectFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.project_fragment, fragment)
                        .addToBackStack(null)
                        .commit();


            }
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish(); // Finish the activity when back pressed
                Log.d(TAG, "handleOnBackPressed: ");
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }


}