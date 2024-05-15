package com.gladiance.ui.fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gladiance.R;


public class FavoriteFragment extends Fragment {




    public FavoriteFragment() {
        // Required empty public constructor
    }
    Button buttonMood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        buttonMood = view.findViewById(R.id.mood);

        buttonMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MoodFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(R.id.moodContainer, fragment).addToBackStack(null)
                        .commit();

            }
        });





        return view;
    }




}