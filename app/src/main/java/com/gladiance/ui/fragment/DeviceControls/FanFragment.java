package com.gladiance.ui.fragment.DeviceControls;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.EspMainActivity;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

public class FanFragment extends Fragment {

    private static final String TAG = "FanFragment";

    private EspMainActivity espMainActivity;
    private EspApplication espApp;

    private Switch fanswitch;
    private ProgressBar progressBar;
    private Button incrementButton, decrementButton;
    private TextView progressTextView;

    private int progress = 0;
    private Context context;
    private NetworkApiManager networkApiManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fan, container, false);

        context = requireActivity();

        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        fanswitch = view.findViewById(R.id.switchButtonFan);
        incrementButton = view.findViewById(R.id.incrementButton);
        decrementButton = view.findViewById(R.id.decrementButton);

        progressBar = view.findViewById(R.id.progressBar);
        progressTextView = view.findViewById(R.id.progressTextView);

        disableSeekBars();

        fanswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                sendSwitchState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                } else {
                    disableSeekBars();
                }
            }
        });

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementProgress();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementProgress();
            }
        });

        return view;
    }

    private void incrementProgress() {
        if (progress < 5) {
            progress++;
            progressBar.setProgress(progress);
            updateProgressText();
            sendFanSpeed(progress);
            Log.e(TAG, "incrementProgress: " + progress);
        }
    }

    private void decrementProgress() {
        if (progress > 0) {
            progress--;
            progressBar.setProgress(progress);
            updateProgressText();
            sendFanSpeed(progress);
            Log.e(TAG, "decrementProgress: " + progress);
        }
    }

    private void updateProgressText() {
        progressTextView.setText(String.valueOf(progress));
    }

    public void sendFanSpeed(int fanSpeed) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        SharedPreferences preferences9 = requireActivity().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String commandBody = "{\"" + name + "\": {\"Speed\": " + fanSpeed + "}}";

        SharedPreferences sharedPreferencesFanSpeed = requireActivity().getSharedPreferences("MyPreferencesFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesFanSpeed.edit();
        editor.putInt("FanSpeed", fanSpeed);
        editor.apply();
        Log.e(TAG, "FanActivity Fan Speed " + fanSpeed);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    public void sendSwitchState(boolean powerState) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Power\": " + powerState + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = requireActivity().getSharedPreferences("my_shared_prefe", Context.MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "node id: " + nodeId2);

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    private void disableSeekBars() {
        incrementButton.setEnabled(false);
        decrementButton.setEnabled(false);
    }

    private void enableSeekBars() {
        incrementButton.setEnabled(true);
        decrementButton.setEnabled(true);
    }

    private void handleApiResponse(ResponseModel responseModel) {
        if (responseModel != null) {
            Log.d(TAG, "handleApiResponse: " + responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " + responseModel.getTag());
            Toast.makeText(context, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }
}
