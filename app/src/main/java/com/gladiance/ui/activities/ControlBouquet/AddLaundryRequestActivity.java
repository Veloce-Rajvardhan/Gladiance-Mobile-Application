package com.gladiance.ui.activities.ControlBouquet;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.LaundryHotelItemAdapter;
import com.gladiance.ui.adapters.LaundryItemAdapter;
import com.gladiance.ui.models.laundryItemHotel.LaundryItemListResponse;
import com.gladiance.ui.models.laundryItemHotel.ObjectTag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLaundryRequestActivity extends AppCompatActivity implements LaundryItemAdapter.OnItemSelectionChangedListener {

    Spinner spinner;
    ArrayList<String> items;
    LaundryItemAdapter adapter;
    RecyclerView recyclerViewLaundryItemHotel;
    private ArrayList<ObjectTag> arrayList;
    private List<ObjectTag> allLaundryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laundry_request);

        spinner = findViewById(R.id.spinnerLaundryItem);
        recyclerViewLaundryItemHotel = findViewById(R.id.recyclerViewLaundryItemHotel);
        items = new ArrayList<>();

        arrayList = new ArrayList<>();

        adapter = new LaundryItemAdapter(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setOnItemSelectionChangedListener(this);
        spinner.setAdapter(adapter);

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
                        updateRecyclerView(adapter.getSelectedItems());
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

    @Override
    public void onItemSelectionChanged(ArrayList<String> selectedItems) {
        updateRecyclerView(selectedItems);
    }

    private void updateRecyclerView(List<String> selectedItems) {
        arrayList.clear();
        if (allLaundryItems != null) {
            for (ObjectTag objectTag : allLaundryItems) {
                if (selectedItems.contains(objectTag.getName())) {
                    arrayList.add(objectTag);
                }
            }
        }

        LaundryHotelItemAdapter laundryHotelItemAdapter = new LaundryHotelItemAdapter(arrayList);
        recyclerViewLaundryItemHotel.setAdapter(laundryHotelItemAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AddLaundryRequestActivity.this, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewLaundryItemHotel.setLayoutManager(gridLayoutManager);
    }
}
