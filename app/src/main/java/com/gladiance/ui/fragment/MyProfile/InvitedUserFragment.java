package com.gladiance.ui.fragment.MyProfile;

import static org.greenrobot.eventbus.EventBus.TAG;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gladiance.R;
import com.gladiance.ui.adapters.InvitedUserAdapter;

import java.util.ArrayList;
import java.util.List;


public class InvitedUserFragment extends Fragment {

    private RecyclerView recyclerView;
    private InvitedUserAdapter adapter;
    private List<String> controlTypes;


    public InvitedUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG, "Hi: " );
        View view = inflater.inflate(R.layout.fragment_invited_user, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.controlTypeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create and set the adapter
        List<String> dataset = new ArrayList<>(); // Replace this with your actual dataset
        dataset.add("Item 1");
        dataset.add("Item 2");
        dataset.add("Item 3");
        InvitedUserAdapter adapter = new InvitedUserAdapter(dataset, getContext());
        recyclerView.setAdapter(adapter);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                Log.d(ContentValues.TAG, "handleOnBackPressed: ");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        return view;

    }
}