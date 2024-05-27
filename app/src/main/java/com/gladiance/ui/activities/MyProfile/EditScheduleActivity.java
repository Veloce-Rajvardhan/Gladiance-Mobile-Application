package com.gladiance.ui.activities.MyProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gladiance.R;
import com.gladiance.ui.activities.Home.ProjectSpaceActivity;
import com.gladiance.ui.adapters.DayAdapter;
import com.gladiance.ui.adapters.MonthAdapter;

import java.util.Arrays;
import java.util.List;

public class EditScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDay,recyclerViewMonth;
    private DayAdapter dayAdapter;
    private MonthAdapter monthAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);


        recyclerViewDay = findViewById(R.id.recyclerViewDay);
        recyclerViewMonth = findViewById(R.id.recyclerViewMonth);

        //Day Recycle View
        GridLayoutManager gridLayoutManager = new GridLayoutManager(EditScheduleActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerViewDay.setLayoutManager(gridLayoutManager);
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayAdapter = new DayAdapter(days);
        recyclerViewDay.setAdapter(dayAdapter);

        //Month Recycle View

        GridLayoutManager gridLayoutManagerMonth = new GridLayoutManager(EditScheduleActivity.this, 6, GridLayoutManager.VERTICAL, false);
        recyclerViewMonth.setLayoutManager(gridLayoutManagerMonth);
        List<String> months = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        monthAdapter = new MonthAdapter(months);
        recyclerViewMonth.setAdapter(monthAdapter);

    }
}