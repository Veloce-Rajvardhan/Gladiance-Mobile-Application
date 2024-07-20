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
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.LaundryItemListAdapter;
import com.gladiance.ui.adapters.RoomServiceAdapter;
import com.gladiance.ui.fragment.ControlBouquet.ControlBouquetHorizontalParentFragment;
import com.gladiance.ui.models.roomservicelist.ObjectTag;
import com.gladiance.ui.models.roomservicelist.RoomServiceResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomServiceListActivity extends AppCompatActivity {

    RecyclerView recyclerViewRoomService;
    Button buttonAddMenu;
    private ArrayList<ObjectTag> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service_list);

        recyclerViewRoomService = findViewById(R.id.recyclerViewRoomServiceList);
        buttonAddMenu = findViewById(R.id.buttonRoomServiceAddMenu);
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

        fetchInRoomServiceRequests(projectSpaceRef,loginToken,loginDeviceId);

        buttonAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomServiceListActivity.this,RoomServiceActivity.class);
                startActivity(intent);
            }
        });

    }

    private void fetchInRoomServiceRequests(String gaaProjectSpaceRef,String loginToken,String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RoomServiceResponse> call = apiService.getInRoomServiceRequests(gaaProjectSpaceRef,loginToken, loginDeviceId);

        call.enqueue(new Callback<RoomServiceResponse>() {
            @Override
            public void onResponse(Call<RoomServiceResponse> call, Response<RoomServiceResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ObjectTag> diningRequestList = response.body().getObjectTag();

                    for(ObjectTag objectTag : diningRequestList){
                        Log.e(TAG, "onResponse Doc No : " + objectTag.getDocNo());
                        arrayList.add(new ObjectTag(objectTag.getRef(),objectTag.getgAAProjectSpaceRef(),objectTag.getDocNo(),objectTag.getSerialNumberWithinDate(),objectTag.getRequestedByUserRef(),objectTag.getRequestedAtDateTime(),objectTag.getMarkedAsAcceptedByUserRef(),objectTag.getMarkedAsAcceptedAtDateTime(),objectTag.getMarkedAsReadyToDeliverByUserRef(),objectTag.getMarkedAsReadyToDeliverAtDateTime(),objectTag.getMarkedAsDeliveredByUserRef(),objectTag.getMarkedAsDeliveredAtDateTime(),objectTag.getCancelledByUserRef(),objectTag.getCancelledAtDateTime(),objectTag.getRequestStatus(),objectTag.getgAAProjectSpaceName(),objectTag.getgAAProjectRef(),objectTag.getgAAProjectName(),objectTag.getRequestedByUserName(),objectTag.getMarkedAsAcceptedByUserName(),objectTag.getMarkedAsReadyToDeliverByUserName(),objectTag.getMarkedAsDeliveredByUserName(),objectTag.getCancelledByUserName(),objectTag.getRequestStatusName(),objectTag.getLineItems()));
                    }

                    RoomServiceAdapter roomServiceAdapter = new RoomServiceAdapter(arrayList);
                    recyclerViewRoomService.setAdapter(roomServiceAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(RoomServiceListActivity.this,1, GridLayoutManager.VERTICAL,false);
                    recyclerViewRoomService.setLayoutManager(gridLayoutManager);


                } else {

                }
            }

            @Override
            public void onFailure(Call<RoomServiceResponse> call, Throwable t) {
                Log.e("MainActivity", "onFailure: ", t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Navigate back to ControlBouquetHorizontalParentFragment
        Intent intent = new Intent(RoomServiceListActivity.this, ControlBouquetHorizontalParentFragment.class);
        startActivity(intent);
        finish(); // Finish the current activity to remove it from the back stack
    }

}
