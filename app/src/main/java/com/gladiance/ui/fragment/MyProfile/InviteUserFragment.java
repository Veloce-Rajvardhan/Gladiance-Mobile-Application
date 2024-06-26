package com.gladiance.ui.fragment.MyProfile;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gladiance.R;
import com.gladiance.ui.activities.MyProfile.AutomationActivity;

public class InviteUserFragment extends Fragment {


    public InviteUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_user, container, false);



//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.popBackStack();
//                Log.d(TAG, "handleOnBackPressed: ");
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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