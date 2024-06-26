package com.gladiance.ui.activities.ControlBouquet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gladiance.R;
import com.gladiance.ui.adapters.EmergencyAdapter;

import java.util.ArrayList;
import java.util.List;

public class SafetyActivity extends AppCompatActivity {

    List<String> tvName = new ArrayList<>();
    List<Integer> imageCall = new ArrayList<>();
    List<String> tvNumber = new ArrayList<>();
    List<Integer> imageMap = new ArrayList<>();
    List<Integer> imageLogo = new ArrayList<>();
    private EmergencyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sefety);

        RecyclerView recyclerViewEmergency = findViewById(R.id.rv_emergency);




        // Add data to lists

        tvName = new ArrayList<>();
        imageCall = new ArrayList<>();
        tvNumber = new ArrayList<>();
        imageMap = new ArrayList<>();
        imageLogo = new ArrayList<>();

        adapter = new EmergencyAdapter(this,tvName,imageCall,tvNumber,imageMap,imageLogo);

        tvName.add("Police");
        tvName.add("Fire Extinguisher");
        tvName.add("Police");
        tvName.add("Police");
        tvName.add("Police");
        tvName.add("Police");

        imageCall.add(R.drawable.telephone);
        imageCall.add(R.drawable.telephone);
        imageCall.add(R.drawable.telephone);
        imageCall.add(R.drawable.telephone);
        imageCall.add(R.drawable.telephone);
        imageCall.add(R.drawable.telephone);


        tvNumber.add("101");
        tvNumber.add("112");
        tvNumber.add("101");
        tvNumber.add("101");
        tvNumber.add("101");
        tvNumber.add("101");

        imageMap.add(R.drawable.map);
        imageMap.add(R.drawable.map);
        imageMap.add(R.drawable.map);
        imageMap.add(R.drawable.map);
        imageMap.add(R.drawable.map);
        imageMap.add(R.drawable.map);


        imageLogo.add(R.drawable.emergancy1);
        imageLogo.add(R.drawable.emergancy1);
        imageLogo.add(R.drawable.emergancy1);
        imageLogo.add(R.drawable.emergancy1);
        imageLogo.add(R.drawable.emergancy1);
        imageLogo.add(R.drawable.emergancy1);


        EmergencyAdapter adapter = new EmergencyAdapter(this, tvName, imageCall, tvNumber, imageMap, imageLogo);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewEmergency.setLayoutManager(gridLayoutManager);
        recyclerViewEmergency.setHasFixedSize(true);
        recyclerViewEmergency.setAdapter(adapter);

    }
}