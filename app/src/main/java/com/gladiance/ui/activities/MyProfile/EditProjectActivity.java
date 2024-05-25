package com.gladiance.ui.activities.MyProfile;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.gladiance.R;
import com.gladiance.ui.activities.Login.LoginActivity;

public class EditProjectActivity extends AppCompatActivity {

    EditText editTextProjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        editTextProjectName = findViewById(R.id.edProjectName);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsProjectName", Context.MODE_PRIVATE);
        String saveProjectName = sharedPreferences.getString("ProjectName", "");
        Log.e(TAG, "EditProfile Project Name: "+saveProjectName );
        editTextProjectName.setText(saveProjectName);


    }
}