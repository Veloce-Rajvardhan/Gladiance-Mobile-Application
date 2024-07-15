package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.LaundrySingleItemAdapter;
import com.gladiance.ui.adapters.RoomServiceSingleItemAdapter;
import com.gladiance.ui.models.CancelRoomServiceResponse;
import com.gladiance.ui.models.roomservicesingleitemlist.LineItem;
import com.gladiance.ui.models.roomservicesingleitemlist.RoomServiceSingleItemRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomServiceDetailsActivity extends AppCompatActivity {

    TextView textViewOrderNo,textViewDateTime,textViewTotalQuantity,textViewTotalAmount;
    RecyclerView recyclerViewRoomServiceSingleItem;
    Button buttonRoomServiceCancelRequest;
    private ArrayList<LineItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service_details);

        textViewOrderNo = findViewById(R.id.tv_order_id);
        textViewDateTime = findViewById(R.id.requested_on_date);
        textViewTotalAmount = findViewById(R.id.tv_TotalAmountRS);
        recyclerViewRoomServiceSingleItem = findViewById(R.id.recyclerViewRoomServiceSingleItem);
        buttonRoomServiceCancelRequest = findViewById(R.id.button_cancel_room_service);

        arrayList = new ArrayList<>();

        SharedPreferences preferencesDocNo = getSharedPreferences("my_shared_prefe_Doc_No", MODE_PRIVATE);
        String DocNo = preferencesDocNo.getString("KEY_DOC_No", "");
        Log.d(TAG, "Doc No : " +DocNo);
        textViewOrderNo.setText(DocNo);

        SharedPreferences preferencesDate = getSharedPreferences("my_shared_prefe_Date", MODE_PRIVATE);
        String DateTime = preferencesDate.getString("KEY_DATE", "");
        Log.d(TAG, "Date and Time : " +DateTime);
        textViewDateTime.setText(DateTime);

        SharedPreferences preferencesRef = getSharedPreferences("my_shared_prefe_Ref", MODE_PRIVATE);
        Long laundryRequestRef = preferencesRef.getLong("KEY_REF", 0L);
        Log.d(TAG, "laundry Request Ref : " + laundryRequestRef);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: "+ GUID);
        String loginDeviceId = GUID.trim();


        SharedPreferences  sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: "+savedLoginDeviceId );
        String loginToken = savedLoginDeviceId.trim();

        fetchRoomServiceRequest(String.valueOf(laundryRequestRef),loginToken,loginDeviceId);

        buttonRoomServiceCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDiningRequest(String.valueOf(laundryRequestRef),loginToken,loginDeviceId);
            }
        });

    }

    private void fetchRoomServiceRequest(String laundryRequestRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RoomServiceSingleItemRes> call = apiService.getInRoomServiceSingleRequest(laundryRequestRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<RoomServiceSingleItemRes>() {
            @Override
            public void onResponse(Call<RoomServiceSingleItemRes> call, Response<RoomServiceSingleItemRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<LineItem> lineItems = response.body().getObjectTag().getLineItems();

                    double totalAmount = 0;

                    for(LineItem lineItem : lineItems){
                        Log.e(TAG, "onResponse RB Item Name: "+ lineItem.getrBItemName());
                        arrayList.add(new LineItem(lineItem.getRef(),lineItem.getgAAProjectSpaceInRoomDiningRequestRef(),lineItem.getrBItemRef(),lineItem.getQuantity(),lineItem.getgAAProjectRef(),lineItem.getgAAProjectName(),lineItem.getgAAProjectSpaceRef(),lineItem.getgAAProjectSpaceName(),lineItem.getrBItemName(),lineItem.getRate()));

                        totalAmount += lineItem.getQuantity() * lineItem.getRate();
                    }

                    textViewTotalAmount.setText(String.valueOf(totalAmount));
                    RoomServiceSingleItemAdapter roomServiceSingleItemAdapter = new RoomServiceSingleItemAdapter(arrayList);
                    recyclerViewRoomServiceSingleItem.setAdapter(roomServiceSingleItemAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(RoomServiceDetailsActivity.this, 1, GridLayoutManager.VERTICAL, false);
                    recyclerViewRoomServiceSingleItem.setLayoutManager(gridLayoutManager);

                }
            }

            @Override
            public void onFailure(Call<RoomServiceSingleItemRes> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void cancelDiningRequest(String requestRef, String loginToken, String deviceId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<CancelRoomServiceResponse> call = apiService.cancelInRoomDiningRequest(requestRef, loginToken, deviceId);
        call.enqueue(new Callback<CancelRoomServiceResponse>() {
            @Override
            public void onResponse(Call<CancelRoomServiceResponse> call, Response<CancelRoomServiceResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Request cancelled successfully");
                } else {
                    Log.e("MainActivity", "Failed to cancel request: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CancelRoomServiceResponse> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }
}