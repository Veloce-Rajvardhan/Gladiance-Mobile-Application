package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.NetworkApiManager;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.EspApplication;
import com.gladiance.ui.activities.EspMainActivity;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.R;

public class FanActivity extends AppCompatActivity implements CircularSeekBarFan.OnProgressChangeListener{

    EspMainActivity espMainActivity;
    private SharedPreferences sharedPreferences;

    Switch fanswitch;
   // String nodeId;
    private ProgressBar progressBar;
    private Button incrementButton, decrementButton;
    private TextView progressTextView,textViewDeviceName;
    //String nodeId2;
    private int progress = 0;
    Context context = this;
    private EspApplication espApp;
    private CircularSeekBarFan circularSeekBar;



    NetworkApiManager networkApiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferencesPDR", Context.MODE_PRIVATE);
        String DeviceRef = sharedPreferences.getString("SpaceTypePlannedDeviceRef", "");
        Log.e(TAG, "Space Type Planned DeviceRef : "+DeviceRef);

        SharedPreferences preferences = getSharedPreferences("my_shared_prefe_label", MODE_PRIVATE);
        String Label = preferences.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "Label : " +Label);

        textViewDeviceName = findViewById(R.id.DeviceName);

        espApp = new EspApplication(getApplicationContext());
        fanswitch = findViewById(R.id.switchButtonFan);
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);


       textViewDeviceName.setText(Label);


        circularSeekBar = findViewById(R.id.circularSeekBar);

        // Example: Set progress to 10
        circularSeekBar.setProgress(0);
        circularSeekBar.setOnProgressChangeListener(this);

        CircularSeekBarFan circularSeekBar = findViewById(R.id.circularSeekBar);
        circularSeekBar.setOnProgressChangeListener(this);

        disableSeekBars();


        //Set a listener on the switch button
        fanswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: "+isChecked);
                sendSwitchState(isChecked);
                if (isChecked) {
                    enableSeekBars();
                } else {
                    disableSeekBars();
                }
            }
        });

    }


    private void updateProgressText() {
        progressTextView.setText("" + progress + "");
    }

    //Fan Speed Method
    public void sendFanSpeed(int fanSpeed){

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);


        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String commandBody = "{\""+name+"\": {\"Speed\": " + fanSpeed + "}}";

        Log.e(TAG, "sendFanSpeed2: "+commandBody );
        int shFanSpeed = fanSpeed;
        SharedPreferences sharedPreferencesFanSpeed = getSharedPreferences("MyPreferencesFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesFanSpeed.edit();
        editor.putInt("FanSpeed", shFanSpeed);
        editor.apply();
        Log.e(TAG, "FanActivity Fan Speed "+shFanSpeed );


        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    public void sendSwitchState(boolean powerState) {
        // Create a RequestModel with the required data
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+name+"\": {\"Power\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "node id: " +nodeId2);

        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {


    }

    private void enableSeekBars() {


    }


    private void handleApiResponse(ResponseModel responseModel) {
        // Handle the response as needed
        if (responseModel != null) {
            // API call was successful
            // Access other fields from responseModel if needed
            Log.d(TAG, "handleApiResponse: " +responseModel.getSuccessful());
            Log.d(TAG, "handleApiResponse: " +responseModel.getTag());

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
                sendFanSpeed(progress);

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