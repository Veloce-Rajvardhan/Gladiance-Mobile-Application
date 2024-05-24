package com.gladiance.ui.fragment.MyProfile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gladiance.R;
import com.gladiance.ui.activities.MyProfile.AboutUsActivity;
import com.gladiance.ui.activities.MyProfile.BasicInfoActivity;
import com.gladiance.ui.activities.MyProfile.DeviceActivity;
import com.gladiance.ui.activities.MyProfile.HelpActivity;
import com.gladiance.ui.activities.MyProfile.IntegrationActivity;
import com.gladiance.ui.activities.MyProfile.ProjectActivity;
import com.gladiance.ui.activities.MyProfile.SettingActivity;
import com.gladiance.ui.activities.MyProfile.SubscriptionActivity;
import com.gladiance.ui.activities.RoomControl.AutomationActivity;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);


        tv_view_more = view.findViewById(R.id.tv_view_more);

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

}