package com.gladiance.ui.fragment.ControlBouquet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gladiance.R;


public class NewPlaceOrderFragment extends Fragment {



    public NewPlaceOrderFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_place_order, container, false);

        return view;
    }
}