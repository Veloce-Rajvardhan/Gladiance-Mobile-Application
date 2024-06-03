package com.gladiance.ui.fragment.DoNotDisturb;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.gladiance.R;


public class DoNotDisturbFragment extends Fragment {


    public DoNotDisturbFragment() {
        // Required empty public constructor
    }

    TextView textView;
    Switch aSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_not_disturb, container, false);

        textView = view.findViewById(R.id.privacy);
        aSwitch = view.findViewById(R.id.switchButtonDimmer);

        textView.setText(aSwitch.isChecked() ? "DND ACTIVATED" : "DND DEACTIVATED");

        // Listen for switch state changes
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update TextView based on switch state
                textView.setText(isChecked ? "DND ACTIVATED" : "DND DEACTIVATED");

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