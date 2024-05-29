package com.gladiance.ui.activities.MyProfile;

import static org.greenrobot.eventbus.EventBus.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gladiance.R;
import com.gladiance.ui.activities.Home.ProjectSpaceActivity;
import com.gladiance.ui.adapters.DayAdapter;
import com.gladiance.ui.adapters.MonthAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDay,recyclerViewMonth;
    private DayAdapter dayAdapter;
    private MonthAdapter monthAdapter;
    private Spinner spinnerProjectType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        recyclerViewDay = findViewById(R.id.recyclerViewDay);
        recyclerViewMonth = findViewById(R.id.recyclerViewMonth);
        spinnerProjectType = findViewById(R.id.scheduleProjectType);

        // Create a list of items for the dropdown
        List<String> items = new ArrayList<>();
        items.add("Home");
        items.add("Hotel");
        items.add("Office");

        // Create a custom adapter with your custom layout for dropdown items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                EditScheduleActivity.this,
                R.layout.dropdown_item1,
                R.id.text1,
                items
        );

        // Set the adapter for the dropdown spinner
        spinnerProjectType.setAdapter(adapter);

        //Day Recycle View
        GridLayoutManager gridLayoutManager = new GridLayoutManager(EditScheduleActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerViewDay.setLayoutManager(gridLayoutManager);
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayAdapter = new DayAdapter(days, new DayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String day, boolean isChecked) {
                Log.e(TAG, "onItemClick: " + day + " isChecked: " + isChecked);
            }

        });
        recyclerViewDay.setAdapter(dayAdapter);

        //Month Recycle View

        GridLayoutManager gridLayoutManagerMonth = new GridLayoutManager(EditScheduleActivity.this, 6, GridLayoutManager.VERTICAL, false);
        recyclerViewMonth.setLayoutManager(gridLayoutManagerMonth);
        List<String> months = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        monthAdapter = new MonthAdapter(months, new MonthAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String month, boolean isChecked) {
                Log.e(TAG, "onItemClick: " + month + " isChecked: " + isChecked);
            }
        });
        recyclerViewMonth.setAdapter(monthAdapter);


    }
}