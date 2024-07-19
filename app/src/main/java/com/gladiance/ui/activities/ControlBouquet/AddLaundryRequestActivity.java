package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.LaundryHotelItemAdapter;
import com.gladiance.ui.adapters.LaundryItemAdapter;
import com.gladiance.ui.models.LaundryApiResponse;
import com.gladiance.ui.models.LaundryRequest;
import com.gladiance.ui.models.RefObject;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.laundryItemHotel.LaundryItemListResponse;
import com.gladiance.ui.models.laundryItemHotel.ObjectTag;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLaundryRequestActivity extends AppCompatActivity implements LaundryItemAdapter.OnItemSelectionChangedListener, LaundryHotelItemAdapter.OnTotalCostChangedListener {

    Spinner spinner;
    ArrayList<String> items;
    LaundryItemAdapter adapter;
    TextView tvAmountLaundry;
    RecyclerView recyclerViewLaundryItemHotel;
    private ArrayList<ObjectTag> arrayList;
    private List<ObjectTag> allLaundryItems;
    private LaundryHotelItemAdapter laundryHotelItemAdapter;
    Button buttonAddLaundryRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laundry_request);

        spinner = findViewById(R.id.spinnerLaundryItem);
        recyclerViewLaundryItemHotel = findViewById(R.id.recyclerViewLaundryItemHotel);
        items = new ArrayList<>();
        arrayList = new ArrayList<>();
        allLaundryItems = new ArrayList<>();

        adapter = new LaundryItemAdapter(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setOnItemSelectionChangedListener(this);
        spinner.setAdapter(adapter);

        tvAmountLaundry = findViewById(R.id.tvAmountLaundry);
        buttonAddLaundryRequest = findViewById(R.id.buttonAddLaundryRequest);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        String GUID = LoginActivity.getUserId(sharedPreferences);
        Log.e(TAG, "Project Space GUID/LoginDeviceId: " + GUID);
        String loginDeviceId = GUID.trim();

        SharedPreferences sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
        Log.e(TAG, "Project Space loginToken: " + savedLoginDeviceId);
        String loginToken = savedLoginDeviceId.trim();

        SharedPreferences sharedPreferences5 = getSharedPreferences("MyPrefsPR", Context.MODE_PRIVATE);
        String ProjectRef = sharedPreferences5.getString("ProjectRef", "");
        String gAAProjectRef = ProjectRef.trim();


        fetchLaundryItemListSpinner(gAAProjectRef, loginToken, loginDeviceId);
        fetchLaundryItemList(gAAProjectRef, loginToken, loginDeviceId);

        buttonAddLaundryRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch the necessary references and make the API call
                gatherReferencesAndMakeApiCall();
            }
        });

    }

    private void gatherReferencesAndMakeApiCall() {
        getLaundryRef();
        int numberOfLineItems = arrayList.size();
        getLineItemRefs(numberOfLineItems);

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
            String GUID = LoginActivity.getUserId(sharedPreferences);
            Log.e(TAG, "Project Space GUID/LoginDeviceId: " + GUID);
            String loginDeviceId = GUID.trim();

            SharedPreferences sharedPreferences2 = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
            Log.e(TAG, "Project Space loginToken: " + savedLoginDeviceId);
            String loginToken = savedLoginDeviceId.trim();

            raiseLaundryRequest(loginToken, loginDeviceId);
        }, 2000); // Adjust the delay as necessary to ensure references are fetched
    }


    private void raiseLaundryRequest(String loginToken, String loginDeviceId) {
        // Prepare the JSON payload
        List<LaundryRequest.LineItem> lineItems = new ArrayList<>();
        int index = 0;
        for (ObjectTag item : arrayList) {
            if (item.isSelected() || item.isSelectedPress()) {
                LaundryRequest.LineItem lineItem = new LaundryRequest.LineItem();
                lineItem.setRef(lineItemRefs.get(index)); // Use unique reference from the list
                lineItem.setGAAProjectSpaceLaundryRequestRef(AppConstants.LaundryRef);
                lineItem.setCustomerLaundryItemRef(String.valueOf(item.getRef()));

                if (item.isSelected()) {
                    lineItem.setLaundryServiceType(100);
                    lineItem.setQuantity(item.getDryWashQuantity());
                } else if (item.isSelectedPress()) {
                    lineItem.setLaundryServiceType(200);
                    lineItem.setQuantity(item.getPressQuantity());
                }

                lineItems.add(lineItem);
                index++;
            }
        }
        SharedPreferences sharedPreferences3 = getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: " + saveProjectSpaceRef);
        String projectSpaceRef = saveProjectSpaceRef.trim();

        LaundryRequest laundryRequest = new LaundryRequest();
        laundryRequest.setRef(AppConstants.LaundryRef);
        laundryRequest.setGAAProjectSpaceRef(projectSpaceRef);
        laundryRequest.setLineItems(lineItems);

        // Make the API call
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<LaundryApiResponse> call = apiService.raiseLaundryRequest(loginToken, loginDeviceId, laundryRequest);

        call.enqueue(new Callback<LaundryApiResponse>() {
            @Override
            public void onResponse(Call<LaundryApiResponse> call, Response<LaundryApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddLaundryRequestActivity.this, "Request raised successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddLaundryRequestActivity.this, "Failed to raise request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LaundryApiResponse> call, Throwable t) {
                Toast.makeText(AddLaundryRequestActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchLaundryItemListSpinner(String gaaProjectRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LaundryItemListResponse> call = apiService.getLaundryItemList(gaaProjectRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<LaundryItemListResponse>() {
            @Override
            public void onResponse(Call<LaundryItemListResponse> call, Response<LaundryItemListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LaundryItemListResponse laundryItemListResponse = response.body();
                    if (laundryItemListResponse.getSuccessful()) {
                        List<ObjectTag> laundryItems = laundryItemListResponse.getObjectTag();
                        for (ObjectTag item : laundryItems) {
                            items.add(item.getName());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddLaundryRequestActivity.this, "Failed to fetch items", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddLaundryRequestActivity.this, "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LaundryItemListResponse> call, Throwable t) {
                Log.e("AddLaundryRequest", "Error: " + t.getMessage());
                Toast.makeText(AddLaundryRequestActivity.this, "Error fetching items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchLaundryItemList(String gaaProjectRef, String loginToken, String loginDeviceId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LaundryItemListResponse> call = apiService.getLaundryItemList(gaaProjectRef, loginToken, loginDeviceId);

        call.enqueue(new Callback<LaundryItemListResponse>() {
            @Override
            public void onResponse(Call<LaundryItemListResponse> call, Response<LaundryItemListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LaundryItemListResponse laundryItemListResponse = response.body();
                    if (laundryItemListResponse.getSuccessful()) {
                        allLaundryItems = laundryItemListResponse.getObjectTag();
                    } else {
                        Toast.makeText(AddLaundryRequestActivity.this, "Failed to fetch items", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddLaundryRequestActivity.this, "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LaundryItemListResponse> call, Throwable t) {
                Log.e("AddLaundryRequest", "Error: " + t.getMessage());
                Toast.makeText(AddLaundryRequestActivity.this, "Error fetching items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLaundryRef() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<AllocateSingleIdResponse> call = apiService.allocateSingleId();
        call.enqueue(new Callback<AllocateSingleIdResponse>() {
            @Override
            public void onResponse(Call<AllocateSingleIdResponse> call, Response<AllocateSingleIdResponse> response) {
                if (response.isSuccessful()) {
                    AllocateSingleIdResponse responseModel = response.body();
                    if (responseModel != null) {
                        boolean success = responseModel.getSuccessful();
                        String message = responseModel.getMessage();

                        AppConstants.LaundryRef = responseModel.getTag();

                        Log.d(EventBus.TAG, "Success2: " + success + ", Message2: " + message+ " LaundryRed=f: "+AppConstants.LaundryRef);
                    }
                } else {
                    Log.e(EventBus.TAG, "API call failed with code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                Log.e(EventBus.TAG, "API call failed: " + t.getMessage());
            }
        });
    }

    private List<String> lineItemRefs = new ArrayList<>();

    private void getLineItemRefs(int numberOfRefs) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        for (int i = 0; i < numberOfRefs; i++) {
            Call<AllocateSingleIdResponse> call = apiService.allocateSingleId();
            call.enqueue(new Callback<AllocateSingleIdResponse>() {
                @Override
                public void onResponse(Call<AllocateSingleIdResponse> call, Response<AllocateSingleIdResponse> response) {
                    if (response.isSuccessful()) {
                        AllocateSingleIdResponse responseModel = response.body();
                        if (responseModel != null) {
                            boolean success = responseModel.getSuccessful();
                            String message = responseModel.getMessage();

                            String uniqueRef = responseModel.getTag();
                            lineItemRefs.add(uniqueRef);

                            Log.d(EventBus.TAG, "Success: " + success + ", Message: " + message + " UniqueRef: " + uniqueRef);
                        }
                    } else {
                        Log.e(EventBus.TAG, "API call failed with code: " + response.code());
                    }
                }
                @Override
                public void onFailure(Call<AllocateSingleIdResponse> call, Throwable t) {
                    Log.e(EventBus.TAG, "API call failed: " + t.getMessage());
                }
            });
        }
    }




    @Override
    public void onItemSelectionChanged(ArrayList<String> selectedItems) {
        updateRecyclerView(selectedItems);
    }

    @Override
    public void onTotalCostChanged(int totalCost) {
        tvAmountLaundry.setText(String.valueOf(totalCost));
    }

    private void updateRecyclerView(List<String> selectedItems) {
        for (String selectedItem : selectedItems) {
            for (ObjectTag objectTag : allLaundryItems) {
                if (objectTag.getName().equals(selectedItem)) {
                    arrayList.add(objectTag);
                }
            }
        }

        if (laundryHotelItemAdapter == null) {
            // Pass AddLaundryRequestActivity as OnTotalCostChangedListener to LaundryHotelItemAdapter
            laundryHotelItemAdapter = new LaundryHotelItemAdapter(arrayList,this);
            recyclerViewLaundryItemHotel.setAdapter(laundryHotelItemAdapter);
            recyclerViewLaundryItemHotel.setLayoutManager(new GridLayoutManager(AddLaundryRequestActivity.this, 1, GridLayoutManager.VERTICAL, false));
        } else {
            laundryHotelItemAdapter.notifyDataSetChanged();
        }
    }
}
