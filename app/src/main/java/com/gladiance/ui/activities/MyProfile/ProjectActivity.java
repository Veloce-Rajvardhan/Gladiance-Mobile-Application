package com.gladiance.ui.activities.MyProfile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gladiance.R;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";

    LinearLayout MyProject;
    LinearLayout CreateProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        MyProject = findViewById(R.id.MyProject);
        CreateProject = findViewById(R.id.CreateProject);

        MyProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, MyProjectActivity.class);
                startActivity(intent);
            }
        });

        CreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this, CreateProjectActivity.class);
                startActivity(intent);
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

//    private void navigateToFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.Project_frameLayout, fragment).addToBackStack(null).commit();
//    }
}