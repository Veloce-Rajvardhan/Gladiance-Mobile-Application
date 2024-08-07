package com.gladiance.ui.fragment.MyProfile;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gladiance.R;
import com.gladiance.ui.activities.MyProfile.AboutUsActivity;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;
import com.gladiance.ui.activities.MyProfile.BasicInfoActivity;
import com.gladiance.ui.activities.MyProfile.DeviceActivity;
import com.gladiance.ui.activities.MyProfile.HelpActivity;
import com.gladiance.ui.activities.MyProfile.IntegrationActivity;
import com.gladiance.ui.activities.MyProfile.ProjectActivity;
import com.gladiance.ui.activities.MyProfile.SettingActivity;
import com.gladiance.ui.activities.MyProfile.SubscriptionActivity;


public class MyProfileFragment extends Fragment {

    public MyProfileFragment() {
        // Required empty public constructor
    }
    TextView tv_view_more;
    LinearLayout Project;
    LinearLayout Automation;
    LinearLayout AddDevice;
    LinearLayout subscription;
    LinearLayout Integration;
    LinearLayout Help;
    LinearLayout AboutUs;
    LinearLayout Setting;
    GridLayout gridLayout3;
    TextView tv_UserName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        tv_UserName = view.findViewById(R.id.tv_UserName);
        tv_view_more = view.findViewById(R.id.tv_view_more);

        SharedPreferences sharedPreferences3 = requireActivity().getSharedPreferences("MyPreferencesDN", Context.MODE_PRIVATE);
        String savedUserDeviceName = sharedPreferences3.getString("UserDisplayName", "");
        tv_UserName.setText(savedUserDeviceName);
        Log.e(TAG, "My Profile User Name: "+savedUserDeviceName );

        tv_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BasicInfoActivity.class);
                startActivity(intent);

            }
        });

        Project = view.findViewById(R.id.Project);
        Project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectActivity.class);
                startActivity(intent);
            }
        });

        Automation = view.findViewById(R.id.Automation);
        Automation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AutomationActivity.class);
                startActivity(intent);
            }
        });

        AddDevice = view.findViewById(R.id.AddDevice);
        AddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeviceActivity.class);
                startActivity(intent);
            }
        });

        subscription = view.findViewById(R.id.subscription);
        subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }
        });

        Integration = view.findViewById(R.id.Integration);
        Integration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IntegrationActivity.class);
                startActivity(intent);
            }
        });

        Help = view.findViewById(R.id.Help);
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        AboutUs = view.findViewById(R.id.AboutUs);
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

        Setting = view.findViewById(R.id.Setting);
        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                // Handle back button press here
//                Intent intent = new Intent(requireActivity(), HomeFragment.class);
//                startActivity(intent);

                //ikde lava
                MeowBottomNavigation bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation);

                bottomNavigation.show(3, true);
                requireActivity().onBackPressed();
                return true; // Consumes the back button press event
            }
            return false; // Otherwise, let the system handle it
        });
    }

}