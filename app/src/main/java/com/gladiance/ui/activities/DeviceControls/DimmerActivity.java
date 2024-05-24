package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

public class DimmerActivity extends AppCompatActivity {

    Switch dimmerswitch;
    String nodeId;
    TextView textView,textViewDeviceName;
    SeekBar seekBar;
    NetworkApiManager networkApiManager;
    ImageView lampImg;
    Context context = this;
    private EspApplication espApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimmer);


        SharedPreferences preferences2 = getSharedPreferences("MyPrefse", MODE_PRIVATE);
        nodeId = preferences2.getString("nodeId", "");
        Log.d(TAG, "Fannodeee: " + nodeId);

        SharedPreferences preferences = getSharedPreferences("my_shared_prefe_label", MODE_PRIVATE);
        String Label = preferences.getString("KEY_USERNAMEs", "");
        Log.d(TAG, "Label : " +Label);

        espApp = new EspApplication(getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);


        textViewDeviceName = findViewById(R.id.DeviceName);
        dimmerswitch = findViewById(R.id.switchButtonDimmer);
        seekBar = findViewById(R.id.seekBarDimmer);
        textView = findViewById(R.id.textView);
        lampImg = findViewById(R.id.dimmer1);
        textView.setVisibility(View.GONE);

//        textViewDeviceName.setText(Label);

        disableSeekBars();

        //Dimmer ON/OFF Code
        dimmerswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle switch state change
                Log.d(TAG, "onCheckedChanged: "+isChecked);
                dimmerState(isChecked);
                if (isChecked) {
                   enableSeekBars();
                    int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
                    if (isDarkTheme) {
                        lampImg.setImageResource(R.drawable.lamp1);
                    }else {
                        lampImg.setImageResource(R.drawable.lampblack1);
                    }
                    textView.setVisibility(View.VISIBLE);
                } else {

                    disableSeekBars();
                    lampImg.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }
        });

        seekBar.setMax(99);

        // Setting initial progress
        seekBar.setProgress(0);
        textView.setText("0");

        // SeekBar change listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Display current progress value
                textView.setText(String.valueOf(progress + 1));
                int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

                // Check if it's night mode (dark theme)
                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
                if (isDarkTheme) {
                if (progress % 10 == 0) {
                    if (progress == 0) {
                        lampImg.setImageResource(R.drawable.lamp1);
                    } else if (progress == 10) {
                        lampImg.setImageResource(R.drawable.lamp2);
                    } else if (progress == 20) {
                        lampImg.setImageResource(R.drawable.lamp3);
                    } else if (progress == 30) {
                        lampImg.setImageResource(R.drawable.lamp4);
                    } else if (progress == 40) {
                        lampImg.setImageResource(R.drawable.lamp5);
                    }else if (progress == 50) {
                        lampImg.setImageResource(R.drawable.lamp6);
                    } else if (progress == 60) {
                        lampImg.setImageResource(R.drawable.lamp7);
                    } else if (progress == 70) {
                        lampImg.setImageResource(R.drawable.lamp8);
                    }else if (progress == 80) {
                        lampImg.setImageResource(R.drawable.lamp9);
                    } else if (progress == 90) {
                        lampImg.setImageResource(R.drawable.lamp10);
                    }
                }
                }else
                if (progress == 0) {
                    lampImg.setImageResource(R.drawable.lampblack1);
                }else if (progress == 10) {
                    lampImg.setImageResource(R.drawable.lampblack2);
                }else if (progress == 20) {
                    lampImg.setImageResource(R.drawable.lampblack3);
                } else if (progress == 30) {
                    lampImg.setImageResource(R.drawable.lampblack4);
                } else if (progress == 40) {
                    lampImg.setImageResource(R.drawable.lampblack5);
                }else if (progress == 50) {
                    lampImg.setImageResource(R.drawable.lampblack6);
                } else if (progress == 60) {
                    lampImg.setImageResource(R.drawable.lampblack7);
                } else if (progress == 70) {
                    lampImg.setImageResource(R.drawable.lampblack8);
                }else if (progress == 80) {
                    lampImg.setImageResource(R.drawable.lampblack9);
                } else if (progress == 90) {
                    lampImg.setImageResource(R.drawable.lampblack10);
                }
                dimmerProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed
            }
        });

    }

    //Dimmer Progress Method 1 to 100
    private void dimmerProgress(int progress){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Intensity\": " + progress + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

    }

    private void dimmerState(boolean powerState) {
        // Create a RequestModel with the required data

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : "+name);

        String commandBody = "{\""+ name +"\": {\"Power\": "+powerState+"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/"+ nodeId2 +"/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);


    }

    private void disableSeekBars() {
        seekBar.setEnabled(false);
    }

    // Function to enable all SeekBars
    private void enableSeekBars() {
        seekBar.setEnabled(true);
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
}