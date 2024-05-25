package com.gladiance.ui.fragment.ControlBouquet;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gladiance.ui.activities.ControlBouquet.BillViewActivity;
import com.gladiance.ui.activities.ControlBouquet.DoorCameraActivity;
import com.gladiance.ui.activities.ControlBouquet.SurveillanceActivity;
import com.gladiance.ui.activities.ControlBouquet.EmergencyActivity;
import com.gladiance.ui.activities.ControlBouquet.FeedbackActivity;
import com.gladiance.ui.activities.ControlBouquet.HotelInfoActivity;
import com.gladiance.ui.activities.ControlBouquet.HouseKeepingActivity;
import com.gladiance.ui.activities.ControlBouquet.LaundryActivity;
import com.gladiance.ui.activities.ControlBouquet.MessagingActivity;
import com.gladiance.ui.activities.ControlBouquet.PromotionActivity;
import com.gladiance.ui.activities.ControlBouquet.RoomServiceActivity;
import com.gladiance.R;


public class ControlBouquetHorizontalParentFragment extends Fragment  {

    CardView CVHouseKipping,CVRoomService,CVLaundry,CVBillView,CVFeedback,CVHotelInfo,CVPromotion,CVMessaging,CVSurveillance,
                CVEmergency,CVSafety;
    public ControlBouquetHorizontalParentFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_bouquet_horizontal_parent, container, false);

        CVHouseKipping = view.findViewById(R.id.houseKipping);
        CVRoomService = view.findViewById(R.id.roomService);
        CVLaundry = view.findViewById(R.id.laundry);
        CVBillView = view.findViewById(R.id.billView);
        CVFeedback = view.findViewById(R.id.feedback);
        CVHotelInfo = view.findViewById(R.id.hotelInfo);
        CVPromotion = view.findViewById(R.id.promotion);
        CVMessaging = view.findViewById(R.id.messaging);
        CVSurveillance = view.findViewById(R.id.surveillance);
        CVEmergency = view.findViewById(R.id.emergency);
        CVSafety = view.findViewById(R.id.safety);

        CVHouseKipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HouseKeepingActivity.class);
                startActivity(intent);
            }
        });

        CVRoomService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomServiceActivity.class);
                startActivity(intent);
            }
        });

        CVLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LaundryActivity.class);
                startActivity(intent);
            }
        });

        CVBillView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BillViewActivity.class);
                startActivity(intent);
            }
        });

        CVFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });

        CVHotelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelInfoActivity.class);
                startActivity(intent);
            }
        });

        CVPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PromotionActivity.class);
                startActivity(intent);
            }
        });

        CVMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessagingActivity.class);
                startActivity(intent);
            }
        });

        CVSurveillance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SurveillanceActivity.class);
                startActivity(intent);
            }
        });

        CVEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmergencyActivity.class);
                startActivity(intent);
            }
        });

        CVSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmergencyActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }






}