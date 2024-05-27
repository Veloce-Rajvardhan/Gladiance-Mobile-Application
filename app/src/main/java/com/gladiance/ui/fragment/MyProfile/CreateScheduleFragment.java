package com.gladiance.ui.fragment.MyProfile;

import static org.greenrobot.eventbus.EventBus.TAG;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;

import com.gladiance.R;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.EditScheduleActivity;
import com.gladiance.ui.adapters.DayAdapter;
import com.gladiance.ui.adapters.MonthAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class CreateScheduleFragment extends Fragment {

    private RecyclerView recyclerViewDay,recyclerViewMonth;
    private DayAdapter dayAdapter;
    private MonthAdapter monthAdapter;
    CheckBox CBWeek;
    CheckBox CBMonth;
    CheckBox CBYear;
    Button btnSave;

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
        CBWeek = view.findViewById(R.id.CBWeek);
        CBMonth = view.findViewById(R.id.CBMonth);
        CBYear = view.findViewById(R.id.CBYear);
        btnSave = view.findViewById(R.id.btnSave);

        /// time ///
        // Initialize hour picker
        NumberPicker hourPicker = view.findViewById(R.id.hourPicker);
        NumberPicker hourPicker2 = view.findViewById(R.id.hourPicker2);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23); // 24 hours format
        // Set initial value
        hourPicker.setValue(12); // Default value 12

        hourPicker2.setMinValue(0);
        hourPicker2.setMaxValue(23); // 24 hours format
        // Set initial value
        hourPicker2.setValue(12); // Default value 12


//        NumberPicker.Formatter hourFormatter = new NumberPicker.Formatter() {
//            @Override
//            public String format(int value) {
//                // Add padding to the displayed value
//                return String.format("%02d", value); // Adds leading zeros if necessary
//            }
//        };
//
//        hourPicker.setFormatter(hourFormatter);


        // Initialize minute picker
        NumberPicker minutePicker = view.findViewById(R.id.minutePicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59); // 60 minutes
        // Set initial value
        minutePicker.setValue(0); // Default value 0

     //   hourPicker.setPadding(20, 0, 20, 0);

        // Initialize minute picker
        NumberPicker minutePicker2 = view.findViewById(R.id.minutePicker2);
        minutePicker2.setMinValue(0);
        minutePicker2.setMaxValue(59); // 60 minutes
        // Set initial value
        minutePicker2.setValue(0); // Default value 0

     //For year
        // Initialize year picker
        NumberPicker yearPicker = view.findViewById(R.id.yearPicker);

        // Get current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Set range of years (from current year to next 10 years)
        yearPicker.setMinValue(currentYear);
        yearPicker.setMaxValue(currentYear + 10);

        // Set initial value to current year
        yearPicker.setValue(currentYear);


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

        // Add listener to get the selected value
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Get the selected value
                int selectedYear = yearPicker.getValue();
                Log.e(TAG, "onValueChange: "+selectedYear);
                // Do something with the selected year
            }
        });


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