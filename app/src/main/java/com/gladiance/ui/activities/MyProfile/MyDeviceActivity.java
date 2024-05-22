package com.gladiance.ui.activities.MyProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gladiance.R;
import com.gladiance.ui.adapters.MyDeviceAdapter;

import java.util.Arrays;
import java.util.List;

public class MyDeviceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyDeviceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_device);

        Button addDeviceButton = findViewById(R.id.AddDevice);
        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDeviceActivity.this, DeviceActivity.class);
                startActivity(intent);
            }
        });

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycleViewMyDevice);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Populate data
        List<String> mDLabel = Arrays.asList("Label :", "Label :", "Label :", "Label :");
        List<String> mDLabelName = Arrays.asList("Dev1 AC", "Dev2 Fan", "Dev2 Light", "Dev2 Fan");
        List<String> mDProject = Arrays.asList("Project :", "Project :", "Project :", "Project :");
        List<String> mDProjectName = Arrays.asList("ABC", "XYZ", "ABC", "XYZ");
        List<String> mDSpace = Arrays.asList("Space :", "Space :", "Space :", "Space :");
        List<String> mDSpaceName = Arrays.asList("Veloce", "Veloce", "Veloce", "Veloce");
        List<String> mDArea = Arrays.asList("Area :", "Area :", "Area :", "Area :");
        List<String> mDAreaName = Arrays.asList("Developer Room", "Developer Room", "Developer Room", "Developer Room");
        List<Integer> mDDelete = Arrays.asList(R.drawable.delete, R.drawable.delete, R.drawable.delete, R.drawable.delete);
        List<Integer> mDRefresh = Arrays.asList(R.drawable.reload, R.drawable.reload, R.drawable.reload, R.drawable.reload);

        // Set up adapter
        adapter = new MyDeviceAdapter(this, mDLabel, mDLabelName, mDProject, mDProjectName, mDSpace, mDSpaceName, mDArea, mDAreaName, mDDelete, mDRefresh);
        recyclerView.setAdapter(adapter);
    }
}