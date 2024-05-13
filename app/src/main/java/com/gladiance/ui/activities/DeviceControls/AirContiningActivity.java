package com.gladiance.ui.activities.DeviceControls;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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

public class AirContiningActivity extends AppCompatActivity {

    Switch switchAirConditioning;

    String nodeId;
    private ProgressBar progressBar;
    private Button incrementButton, decrementButton;
    TextView progressTextView;
    private int progress = 64;
    NetworkApiManager networkApiManager;


    TextView textView, textViewMood, textViewUnit;
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

        espApp = new EspApplication(getApplicationContext());
        networkApiManager = new NetworkApiManager(context.getApplicationContext(), espApp);

        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
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

        //Progress Bar Code
        progressBar = findViewById(R.id.progressBar);

        progressTextView = findViewById(R.id.progressTextView);

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

        //Button Cool Heat Code

        buttonCool = findViewById(R.id.buttonCool);
        buttonHeat = findViewById(R.id.buttonHeat);
        textViewMood = findViewById(R.id.textMode);

        buttonCool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewMood.setText("Cool");
                String mood = "Cool";
                sendMood(mood);
            }
        });

        buttonHeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewMood.setText("Heat");
                String mood = "Heat";
                sendMood(mood);
            }
        });

        textViewUnit = findViewById(R.id.textUnit);
        buttonfah = findViewById(R.id.buttonFehr);
        buttonCon = findViewById(R.id.buttonCCent);

        buttonCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewUnit.setText("Centigrade");
                String mood = "Centigrade";
                sendUnit(mood);
            }
        });

        buttonfah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewUnit.setText("Fahrenheit");
                String mood = "Fahrenheit";
                sendUnit(mood);
            }
        });
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
        progressTextView.setText("" + progress + "");
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

        //////////////////////////////////////////
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("MESSAGE_KEY");
//        Log.e(TAG, "curtainAction: "+message );
//
//        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
//        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "node id: " +nodeId2);
//
//        RequestModel requestModel = new RequestModel();
//        requestModel.setSenderLoginToken(0);
//        requestModel.setTopic("node/"+ nodeId2 +"/params/remote");
//        Log.d(TAG, "sendFanSpeed: "+mood);
//        requestModel.setMessage("{\""+message+"\": {\"Mode\":\"" + mood + "\"}}");
//        Log.e(TAG, "sendMood: "+mood );
//
//        //requestModel.setQosLevel(0);
//        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
//        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                if (response.isSuccessful()) {
//                    ResponseModel responseModel = response.body();
//                    handleApiResponse(responseModel);
//                } else {
//                    // Handle unsuccessful response
//                    Toast.makeText(AirContiningActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                // Handle failure
//                Toast.makeText(AirContiningActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
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

        ////////////////////////////
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("MESSAGE_KEY");
//        Log.e(TAG, "curtainAction: "+message );
//
//        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
//        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "node id: " +nodeId2);
//
//        RequestModel requestModel = new RequestModel();
//        requestModel.setSenderLoginToken(0);
//        requestModel.setTopic("node/"+ nodeId2 +"/params/remote");
//        Log.d(TAG, "sendFanSpeed: "+unit);
//        requestModel.setMessage("{\""+message+"\": {\"Unit\":\"" + unit + "\"}}");
//        Log.e(TAG, "sendUnit: "+unit );
//
//        //requestModel.setQosLevel(0);
//        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
//        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                if (response.isSuccessful()) {
//                    ResponseModel responseModel = response.body();
//                    handleApiResponse(responseModel);
//                } else {
//                    // Handle unsuccessful response
//                    Toast.makeText(AirContiningActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                // Handle failure
//                Toast.makeText(AirContiningActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //Air Condition  Progress
    private void airConditioningProgress(String progress1) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Speed\":\"" + progress1 + "\"}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");

        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

        ///////////////////////////////////////////////

//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("MESSAGE_KEY");
//        Log.e(TAG, "curtainAction: "+message );
//
//        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
//        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "node id: " +nodeId2);
//
//        RequestModel requestModel = new RequestModel();
//        requestModel.setSenderLoginToken(0);
//        requestModel.setTopic("node/"+ nodeId2 +"/params/remote");
//        Log.d(TAG, "sendFanSpeed: "+progress1);
//        requestModel.setMessage("{\""+ message +"\": {\"Speed\":\"" + progress1 + "\"}}");
//        Log.e(TAG, "AirCondProgress: "+progress1);
//
//        //requestModel.setQosLevel(0);
//        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
//        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                if (response.isSuccessful()) {
//                    ResponseModel responseModel = response.body();
//                    handleApiResponse(responseModel);
//                } else {
//                    // Handle unsuccessful response
//                    Toast.makeText(AirContiningActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                // Handle failure
//                Toast.makeText(AirContiningActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //AirCondition Temp Speed Method
    private void sendTemperature(int fanSpeed) {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsName", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "");
        Log.e(TAG, "Name : " + name);

        String commandBody = "{\"" + name + "\": {\"Set\": " + fanSpeed + "}}";

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
        String remoteCommandTopic = "node/" + nodeId2 + "/params/remote";

        networkApiManager.updateParamValue(nodeId2, commandBody, apiService, remoteCommandTopic);

        ////////////////////////////
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("MESSAGE_KEY");
//        Log.e(TAG, "curtainAction: "+message );
//
//        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
//        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "node id: " +nodeId2);
//
//        RequestModel requestModel = new RequestModel();
//        requestModel.setSenderLoginToken(0);
//        requestModel.setTopic("node/"+ nodeId2 +"/params/remote");
//        Log.d(TAG, "sendFanSpeed: "+fanSpeed);
//        requestModel.setMessage("{\""+message+"\": {\"Set\": " + fanSpeed + "}}");
//
//
//        //requestModel.setQosLevel(0);
//        Log.d(TAG, "sendFanSpeed: "+requestModel.getMessage());
//        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                if (response.isSuccessful()) {
//                    ResponseModel responseModel = response.body();
//                    handleApiResponse(responseModel);
//                } else {
//                    // Handle unsuccessful response
//                    Toast.makeText(AirContiningActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                // Handle failure
//                Toast.makeText(AirContiningActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
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

        //////////////////////////////
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("MESSAGE_KEY");
//        Log.e(TAG, "curtainAction: "+message );
//
//        SharedPreferences preferences9 = getSharedPreferences("my_shared_prefe", MODE_PRIVATE);
//        String nodeId2 = preferences9.getString("KEY_USERNAMEs", "");
//        Log.d(TAG, "node id: " +nodeId2);
//
//        RequestModel requestModel = new RequestModel();
//        requestModel.setSenderLoginToken(0);
//        requestModel.setTopic("node/"+ nodeId2 +"/params/remote");
//
//
//        requestModel.setMessage("{\""+message+"\": {\"Power\": "+powerState+"}}");
//        Log.d(TAG, "sendSwitchState: "+powerState);
//        //  requestModel.setQosLevel(0);
//        // Make the API call
//        Call<ResponseModel> call = apiService.sendSwitchState(requestModel);
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//                if (response.isSuccessful()) {
//                    ResponseModel responseModel = response.body();
//                    Log.d(TAG, "onResponse: "+responseModel);
//                    handleApiResponse(responseModel);
//                } else {
//                    // Handle unsuccessful response
//                    Toast.makeText(AirContiningActivity.this, "Failed to make the API call", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                // Handle failure
//                Toast.makeText(AirContiningActivity.this, "Network error", Toast.LENGTH_SHORT).show();
//            }
//        });
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

            Toast.makeText(this, "Switch state updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle unsuccessful response
            Toast.makeText(this, "Failed to update switch state", Toast.LENGTH_SHORT).show();
        }
    }

}
