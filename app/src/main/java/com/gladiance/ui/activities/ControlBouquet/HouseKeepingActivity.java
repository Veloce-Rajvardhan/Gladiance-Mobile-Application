package com.gladiance.ui.activities.ControlBouquet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.gladiance.R;

public class HouseKeepingActivity extends AppCompatActivity {

    TextView textView;
    Switch switchHouseKeeping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_keeping);

        switchHouseKeeping = findViewById(R.id.switchButtonHouseKeeping);




    }
}