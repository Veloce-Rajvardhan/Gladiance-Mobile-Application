package com.gladiance.ui.fragment.DeviceControls;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

public class AirContiningFragment extends Fragment {

    private Switch switchAirConditioning;
    private ProgressBar progressBar;
    private Button incrementButton, decrementButton;
    private TextView progressTextView, textView, textViewMood, textViewUnit;
    private SeekBar seekbarAirCond;
    private Button buttonCool, buttonHeat, buttonCon, buttonfah;

    private String nodeId;
    private int progress = 64;

    private NetworkApiManager networkApiManager;
    private Context context;
    private EspApplication espApp;

    private final String[] progressStates = {"Off", "Low", "Mid", "High"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_air_contining, container, false);

        context = getContext();
        espApp = new EspApplication(context.getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        SharedPreferences preferences2 = context.getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        incrementButton = view.findViewById(R.id.incrementButton);
        decrementButton = view.findViewById(R.id.decrementButton);
        seekbarAirCond = view.findViewById(R.id.seekBarAirCond);

        disableSeekBars();

        //Set a AirCondition on the switch button
        switchAirConditioning = view.findViewById(R.id.switchButtonAirCon);
        switchAirConditioning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                sendSwitchState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                } else {
                    disableSeekBars();
                }
            }
        });

        //Progress Bar Code
        progressBar = view.findViewById(R.id.progressBar);

        progressTextView = view.findViewById(R.id.progressTextView);

        updateProgressText();

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

        //Seek Bar AirCondition
        textView = view.findViewById(R.id.textViewFan);

        seekbarAirCond.setMax(progressStates.length - 1);
        seekbarAirCond.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progressStates[progress]);
                String progressState = progressStates[progress];
                airConditioningProgress(progressState);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });

        //Button Cool Heat Code

        buttonCool = view.findViewById(R.id.buttonCool);
        buttonHeat = view.findViewById(R.id.buttonHeat);
        textViewMood = view.findViewById(R.id.textMode);

        buttonCool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewMood.setText("Cool");
                sendMood("Cool");
            }
        });

        buttonHeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewMood.setText("Heat");
                sendMood("Heat");
            }
        });

        textViewUnit = view.findViewById(R.id.textUnit);
        buttonfah = view.findViewById(R.id.buttonFehr);
        buttonCon = view.findViewById(R.id.buttonCCent);

        buttonCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewUnit.setText("Centigrade");
                sendUnit("Centigrade");
            }
        });

        buttonfah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewUnit.setText("Fahrenheit");
                sendUnit("Fahrenheit");
            }
        });

        return view;
    }

    private void incrementProgress() {
        if (progress < 86) {
            progress++;
            progressBar.setProgress(progress);
            updateProgressText();
            sendTemperature(progress);
            Log.e(TAG, "incrementProgress: " + progress);
        }
    }

    private void decrementProgress() {
        if (progress > 64) {
            progress--;
            progressBar.setProgress(progress);
            updateProgressText();
            sendTemperature(progress);
            Log.e(TAG, "decrementProgress: " + progress);
        }
    }

    private void updateProgressText() {
        progressTextView.setText(String.valueOf(progress));
    }

    //AirCondition Set Mood Method
    private void sendMood(String mood) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        String commandBody = "{\"" + name + "\": {\"Mode\":\"" + mood + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    //AirCondition Unit Method
    private void sendUnit(String unit) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        String commandBody = "{\"" + name + "\": {\"Unit\":\"" + unit + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = context.getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);
    }

    private void airConditioningProgress(String progress1) {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Speed\":\"" + progress1 + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void sendTemperature(int fanSpeed) {

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Set\": " + fanSpeed + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = requireContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void sendSwitchState(boolean powerState) {
        // Create a RequestModel with the required data

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefsName", MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Power\": " + powerState + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getContext().getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {
        incrementButton.setEnabled(false);
        decrementButton.setEnabled(false);
        seekbarAirCond.setEnabled(false);

    }

    private void enableSeekBars() {
        incrementButton.setEnabled(true);
        decrementButton.setEnabled(true);
        seekbarAirCond.setEnabled(true);

    }

    private void handleApiResponse(ResponseModel responseModel) {
        // Handle the response as needed
        if (responseModel != null) {
            // API call was successful
            // Access other fields from responseModel if needed
            Log.d(TAG, "handleApiResponse: " + responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " + responseModel.getTag());

            Toast.makeText(requireContext(), "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(requireContext(), "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }

}