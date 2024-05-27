package com.gladiance.ui.fragment.MyProfile;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gladiance.R;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.EditScheduleActivity;
import com.gladiance.ui.adapters.DayAdapter;
import com.gladiance.ui.adapters.MonthAdapter;

import java.util.Arrays;
import java.util.List;


public class CreateScheduleFragment extends Fragment {

    private RecyclerView recyclerViewDay,recyclerViewMonth;
    private DayAdapter dayAdapter;
    private MonthAdapter monthAdapter;

    public CreateScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_schedule, container, false);

        recyclerViewDay = view.findViewById(R.id.recyclerViewDay);
        recyclerViewMonth = view.findViewById(R.id.recyclerViewMonth);

        //Day Recycle View
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false);
        recyclerViewDay.setLayoutManager(gridLayoutManager);
        List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        dayAdapter = new DayAdapter(days);
        recyclerViewDay.setAdapter(dayAdapter);

        //Month Recycle View

        GridLayoutManager gridLayoutManagerMonth = new GridLayoutManager(requireContext(), 6, GridLayoutManager.VERTICAL, false);
        recyclerViewMonth.setLayoutManager(gridLayoutManagerMonth);
        List<String> months = Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        );
        monthAdapter = new MonthAdapter(months);
        recyclerViewMonth.setAdapter(monthAdapter);

        return view;
    }




    private final OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
        @Override
        public void handleOnBackPressed() {
            AutomationActivity activity = (AutomationActivity) requireActivity();

            // Call the method in the activity
            activity.recreate(); // Replace "yourMethod()" with the actual method name you want to call
        }
    };
}