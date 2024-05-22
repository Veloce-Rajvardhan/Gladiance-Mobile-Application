package com.gladiance.ui.activities.MyProfile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gladiance.R;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectActivity extends AppCompatActivity {

    private static final String TAG = "CreateProjectActivity";

    private Spinner customSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        Spinner dropdownSpinner = findViewById(R.id.customSpinner);

        // Create a list of items for the dropdown
        List<String> items = new ArrayList<>();
        items.add("Home");
        items.add("Hotel");
        items.add("Office");
        items.add("Hospital");
        items.add("Other");

        // Create a custom adapter with your custom layout for dropdown items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.dropdown_item1,
                R.id.text1,
                items
        );

        // Set the adapter for the dropdown spinner
        dropdownSpinner.setAdapter(adapter);

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