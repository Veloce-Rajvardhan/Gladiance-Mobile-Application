package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Home.ProjectSpaceGroupActivity;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.LaundryItemAdapter;
import com.gladiance.ui.adapters.LaundryItemListAdapter;
import com.gladiance.ui.models.ProjectSpaceGroupReqModel;
import com.gladiance.ui.models.SpaceGroup;
import com.gladiance.ui.models.laundrylist.LaundryRes;
import com.gladiance.ui.models.laundrylist.ObjectTag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaundryActivity extends AppCompatActivity {

    RecyclerView recyclerViewLaundryReq;
    private ArrayList<ObjectTag> arrayList;
    Button buttonAddLaundryRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry);

        recyclerViewLaundryReq = findViewById(R.id.recyclerViewLaundryListItem);
        buttonAddLaundryRequest = findViewById(R.id.AddLaundryRequest);
        arrayList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences  sharedPreferences5 = getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences5.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: "+saveProjectSpaceRef );
        String projectSpaceRef = saveProjectSpaceRef.trim();


        fetchLaundryRequest(projectSpaceRef,loginToken,loginDeviceId);

        buttonAddLaundryRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaundryActivity.this,AddLaundryRequestActivity.class);
                startActivity(intent);
            }
        });

    }


    private void fetchLaundryRequest(String gaaProjectSpaceRef,String loginToken,String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LaundryRes> call = apiService.getLaundryRequest(gaaProjectSpaceRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<LaundryRes>() {
            @Override
            public void onResponse(Call<LaundryRes> call, Response<LaundryRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LaundryRes laundryRes = response.body();
                    if (laundryRes != null && laundryRes.getSuccessful()) {
                    List<ObjectTag> objectTags = laundryRes.getObjectTag();

                        for(ObjectTag objectTag : objectTags){
                            Log.e(TAG, "onResponse Doc No : " + objectTag.getDocNo());
                            arrayList.add(new ObjectTag(objectTag.getRef(),objectTag.getgAAProjectSpaceRef(),objectTag.getDocNo(),objectTag.getSerialNumberWithinDate(),objectTag.getRequestedByUserRef(),objectTag.getRequestedAtDateTime(),objectTag.getMarkedAsPickedUpByUserRef(),objectTag.getMarkedAsPickedUpAtDateTime(),objectTag.getMarkedAsDeliveredByUserRef(),objectTag.getMarkedAsDeliveredAtDateTime(),objectTag.getCancelledByUserRef(),objectTag.getCancelledAtDateTime(),objectTag.getRequestStatus(),objectTag.getPickupRequestedFromDateTime(),objectTag.getPickupRequestedUptoDateTime(),objectTag.getgAAProjectSpaceName(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName(),objectTag.getRequestedByUserName(),objectTag.getMarkedAsPickedUpByUserName(),objectTag.getMarkedAsDeliveredByUserName(),objectTag.getCancelledByUserName(),objectTag.getRequestStatusName(),objectTag.getLineItems()));
                        }

                        LaundryItemListAdapter laundryItemListAdapter = new LaundryItemListAdapter(arrayList);
                        recyclerViewLaundryReq.setAdapter(laundryItemListAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(LaundryActivity.this,1, GridLayoutManager.VERTICAL,false);
                        recyclerViewLaundryReq.setLayoutManager(gridLayoutManager);

                    }
                } else {
                    Log.e("API Error", "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LaundryRes> call, Throwable t) {
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }
}