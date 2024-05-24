package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

public class AirContiningActivity extends AppCompatActivity implements CircularSeekBar.OnProgressChangeListener{

    Switch switchAirConditioning;

    String nodeId;
    private ProgressBar progressBar;
    private Button incrementButton, decrementButton;

   // private int progress = 64;
    NetworkApiManager networkApiManager;

    private CircularSeekBar circularSeekBar;
    private int progress = 0;


    TextView textView, textViewDeviceName,tvCel,tvFer;
    ImageView imgCool,imgHot;
    SeekBar seekbarAirCond;
    private String[] progressStates = {"Off", "Low", "Mid", "High"};

    Button buttonCool, buttonHeat, buttonCon, buttonfah;

    Context context = this;
    private EspApplication espApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_contining);

        SharedPreferences preferences2 = getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        SharedPreferences preferences = getSharedPreferences("my_shared_prefe_label", MODE_PRIVATE);
        String Label = preferences.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "Label : " +Label);

        espApp = new EspApplication(getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        circularSeekBar = findViewById(R.id.circularSeekBar);

        // Example: Set progress to 10
        circularSeekBar.setProgress(0);
        circularSeekBar.setOnProgressChangeListener(this);

        CircularSeekBar circularSeekBar = findViewById(R.id.circularSeekBar);
        circularSeekBar.setOnProgressChangeListener(this);

        textViewDeviceName = findViewById(R.id.DeviceName);

        textViewDeviceName.setText(Label);


        seekbarAirCond = findViewById(R.id.seekBarAirCond);

        disableSeekBars();

        //Set a AirCondition on the switch button
        switchAirConditioning = findViewById(R.id.switchButtonAirCon);
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




        //Seek Bar AirCondition

        textView = findViewById(R.id.textViewFan);

        // String progress1;

        seekbarAirCond.setMax(progressStates.length - 1);
        seekbarAirCond.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progressStates[progress]);
                if (progress == 0) {
                    String progress1 = "Off";
                    airConditioningProgress(progress1);

                } else if (progress == 1) {
                    String progress1 = "Low";
                    airConditioningProgress(progress1);

                } else if (progress == 2) {
                    String progress1 = "Mid";
                    airConditioningProgress(progress1);

                } else {
                    String progress1 = "High";
                    airConditioningProgress(progress1);
                }
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


        tvCel = findViewById(R.id.tvCel);
        tvFer = findViewById(R.id.tvFer);

        tvCel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCel.setBackgroundResource(R.drawable.trasparent_orange_top_bg);
                String mood = "Centigrade";
                sendUnit(mood);
                tvFer.setBackgroundResource(android.R.color.transparent);
            }
        });

        tvFer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFer.setBackgroundResource(R.drawable.trasparent_orange_botton_bg);
                String mood = "Fahrenheit";
                sendUnit(mood);
                tvCel.setBackgroundResource(android.R.color.transparent);
            }
        });

        imgHot = findViewById(R.id.imgHot);
        imgCool = findViewById(R.id.imgCool);

        imgHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHot.setBackgroundResource(R.drawable.trasparent_orange_top_bg);
                String mood = "Heat";
                sendMood(mood);
                imgCool.setBackgroundResource(android.R.color.transparent);
            }
        });

        imgCool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCool.setBackgroundResource(R.drawable.trasparent_orange_botton_bg);
                String mood = "Cool";
                sendMood(mood);
                imgHot.setBackgroundResource(android.R.color.transparent);
            }
        });

    }










    //AirCondition Set Mood Method
    private void sendMood(String mood) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Mode\":\"" + mood + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    //AirCondition Unit Method
    private void sendUnit(String unit) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);
        String commandBody = "{\"" + name + "\": {\"Unit\":\"" + unit + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    //Air Condition  Progress
    private void airConditioningProgress(String progress1) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Speed\":\"" + progress1 + "\"}}";
        Log.e(TAG, "sendTemperature: "+commandBody );
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    //AirCondition Temp Speed Method
    private void sendTemperature(int fanSpeed) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Set\": " + fanSpeed + "}}";
        Log.e(TAG, "sendTemperature: "+commandBody );
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void sendSwitchState(boolean powerState) {
        // Create a RequestModel with the required data

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Power\": " + powerState + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {
        seekbarAirCond.setEnabled(false);
        circularSeekBar.setEnabled(false);
    }

    private void enableSeekBars() {
        seekbarAirCond.setEnabled(true);
        circularSeekBar.setEnabled(true);
    }

    private void handleApiResponse(ResponseModel responseModel) {
        // Handle the response as needed
        if (responseModel != null) {
            // API call was successful
            // Access other fields from responseModel if needed
            Log.d(TAG, "handleApiResponse: " + responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " + responseModel.getTag());

            Toast.makeText(this, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(this, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // Here, you have the progress value when touch is released
                Log.d("Progress", "Progress when touch released: " + progress);
                Toast.makeText(this, "Progress when touch released: " + progress, Toast.LENGTH_SHORT).show();
                // You can do whatever you want with the progress value here
                sendTemperature(progress);
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onProgressChanged(String progressText) {
        // Here you receive the progress text
        // Parse it to a float and update the progress variable
        try {
            progress = (int) Float.parseFloat(progressText);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


}
