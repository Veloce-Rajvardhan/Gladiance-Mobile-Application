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
import android.widget.TextView;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiClient;
import com.gladiance.ui.activities.API.ApiInterface;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.LaundryItemListAdapter;
import com.gladiance.ui.adapters.LaundrySingleItemAdapter;
import com.gladiance.ui.models.CancelLaundryResponse;
import com.gladiance.ui.models.laundrylist.ObjectTag;
import com.gladiance.ui.models.laundrysinglelist.LaundrySingleRes;
import com.gladiance.ui.models.laundrysinglelist.LineItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaundryDetailsActivity extends AppCompatActivity {

    TextView textViewDocNo,textViewDateTime,textViewTotalQuantity,textViewTotalAmount;
    RecyclerView recyclerViewLaundrySingleItem;
    Button buttonLaundryCancelRequest;
    private ArrayList<LineItem> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_details);

        textViewDocNo = findViewById(R.id.tv_Doc_No);
        textViewDateTime = findViewById(R.id.requested_on_date);
        recyclerViewLaundrySingleItem = findViewById(R.id.recyclerViewLaundrySingleDetails);
        textViewTotalQuantity = findViewById(R.id.tv_TotalQuantityNO);
        textViewTotalAmount = findViewById(R.id.tv_TotalAmount);
        buttonLaundryCancelRequest = findViewById(R.id.LaundryCancelRequest);

        arrayList = new ArrayList<>();

        SharedPreferences preferencesDocNo = getSharedPreferences("my_shared_prefe_Doc_No", MODE_PRIVATE);
        String DocNo = preferencesDocNo.getString("KEY_DOC_No", "");
        Log.d(TAG, "Doc No : " +DocNo);
        textViewDocNo.setText(DocNo);

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

        fetchLaundryRequest(String.valueOf(laundryRequestRef),loginToken,loginDeviceId);

        buttonLaundryCancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelLaundryRequest(String.valueOf(laundryRequestRef),loginToken,loginDeviceId);
            }
        });

    }

    private void fetchLaundryRequest(String laundryRequestRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LaundrySingleRes> call = apiService.getSingleLaundryRequest(laundryRequestRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<LaundrySingleRes>() {
            @Override
            public void onResponse(Call<LaundrySingleRes> call, Response<LaundrySingleRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LaundrySingleRes laundryRes = response.body();
                    if (laundryRes != null && laundryRes.getSuccessful()) {
                        List<LineItem> lineItems = laundryRes.getObjectTag().getLineItems();
                        double totalQuantity = 0;
                        double totalAmount = 0;
                        for (LineItem lineItem : lineItems) {
                            Log.e(TAG, "onResponse Laundry Item Ref : " + lineItem.getCustomerLaundryItemRef());
                            arrayList.add(new LineItem(lineItem.getRef(), lineItem.getgAAProjectSpaceLaundryRequestRef(), lineItem.getCustomerLaundryItemRef(), lineItem.getLaundryServiceType(), lineItem.getQuantity(), lineItem.getgAAProjectRef(), lineItem.getgAAProjectName(), lineItem.getgAAProjectSpaceRef(), lineItem.getgAAProjectSpaceName(), lineItem.getCustomerLaundryItemName(), lineItem.getLaundryServiceTypeName(), lineItem.getRate()));
                            totalQuantity += lineItem.getQuantity(); // Add quantity to total
                            totalAmount += lineItem.getQuantity() * lineItem.getRate();
                        }

                        // Set total quantity to TextView
                        textViewTotalQuantity.setText(String.valueOf(totalQuantity));
                        textViewTotalAmount.setText(String.valueOf(totalAmount));
                        LaundrySingleItemAdapter laundrySingleItemAdapter = new LaundrySingleItemAdapter(arrayList);
                        recyclerViewLaundrySingleItem.setAdapter(laundrySingleItemAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(LaundryDetailsActivity.this, 1, GridLayoutManager.VERTICAL, false);
                        recyclerViewLaundrySingleItem.setLayoutManager(gridLayoutManager);
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<LaundrySingleRes> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }

    private void cancelLaundryRequest(String laundryRequestRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<CancelLaundryResponse> call = apiService.cancelLaundryRequest(laundryRequestRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<CancelLaundryResponse>() {
            @Override
            public void onResponse(Call<CancelLaundryResponse> call, Response<CancelLaundryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CancelLaundryResponse cancelRes = response.body();
                    if (cancelRes.isSuccessful()) {
                        Intent intent = new Intent(LaundryDetailsActivity.this,LaundryActivity.class);
                        startActivity(intent);
                        Log.d(TAG, "Laundry request cancelled successfully");
                    } else {
                        // Handle unsuccessful cancellation
                        Log.d(TAG, "Failed to cancel laundry request: " + cancelRes.getMessage());
                    }
                } else {
                    // Handle unsuccessful response
                    Log.d(TAG, "Failed to cancel laundry request: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CancelLaundryResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }


}