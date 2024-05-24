package com.gladiance.ui.activities.MyProfile;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.gladiance.R;
import com.gladiance.ui.adapters.MyProjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyProjectActivity extends AppCompatActivity {

    private static final String TAG = "MyProjectActivity";

    private RecyclerView recyclerView;
    private MyProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish(); // Finish the activity when back pressed
                Log.d(TAG, "handleOnBackPressed: ");
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        recyclerView = findViewById(R.id.recycleViewMyProject);

        // Initialize your data lists
        List<String> tvProject = new ArrayList<>();
        List<String> tvProjectName = new ArrayList<>();
        List<String> tvSpace = new ArrayList<>();
        List<String> tvSpaceName = new ArrayList<>();
        List<Integer> imgView = new ArrayList<>();
        List<Integer> imgDelete = new ArrayList<>();

        // Add sample data to your lists (replace this with your actual data)
        tvProject.add("Project :");
        tvProject.add("Project :");
        tvProject.add("Project :");
        tvProject.add("Project :");

        tvProjectName.add("ABC");
        tvProjectName.add("XYZ");
        tvProjectName.add("ABC");
        tvProjectName.add("XYZ");

        tvSpace.add("Space :");
        tvSpace.add("Space :");
        tvSpace.add("Space :");
        tvSpace.add("Space :");

        tvSpaceName.add("Home");
        tvSpaceName.add("Home");
        tvSpaceName.add("Home");
        tvSpaceName.add("Home");

        imgView.add(R.drawable.project);
        imgView.add(R.drawable.project);
        imgView.add(R.drawable.project);
        imgView.add(R.drawable.project);

        imgDelete.add(R.drawable.delete);
        imgDelete.add(R.drawable.delete);
        imgDelete.add(R.drawable.delete);
        imgDelete.add(R.drawable.delete);

        // Create and set up your adapter
//        adapter = new MyProjectAdapter(this, tvProject, tvProjectName, tvSpace, tvSpaceName, imgView, imgDelete);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setAdapter(adapter);
    }
}