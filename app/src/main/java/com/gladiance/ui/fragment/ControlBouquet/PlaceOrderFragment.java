package com.gladiance.ui.fragment.ControlBouquet;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gladiance.AppConstants;
import com.gladiance.R;
import com.gladiance.ui.activities.API.ApiService;
import com.gladiance.ui.activities.API.RetrofitClient;
import com.gladiance.ui.activities.ControlBouquet.AddLaundryRequestActivity;
import com.gladiance.ui.activities.Login.LoginActivity;
import com.gladiance.ui.adapters.PlaceOrderAdapter;
import com.gladiance.ui.models.LaundryApiResponse;
import com.gladiance.ui.models.LaundryRequest;
import com.gladiance.ui.models.PlaceOrderItem;
import com.gladiance.ui.models.RoomServiceApiResponse;
import com.gladiance.ui.models.RoomServiceRequest;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.laundryItemHotel.ObjectTag;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderFragment extends BottomSheetDialogFragment implements PlaceOrderAdapter.TotalSumListener {

    private static final String ARG_ORDER_LIST = "order_list";

    private List<PlaceOrderItem> orderList;
    private TextView textViewGrandTotalFM;
    Button buttonPlaceRoomServiceOrder;

    public static PlaceOrderFragment newInstance(ArrayList<PlaceOrderItem> orderList) {
        PlaceOrderFragment fragment = new PlaceOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ORDER_LIST, orderList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderList = (List<PlaceOrderItem>) getArguments().getSerializable(ARG_ORDER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);

        textViewGrandTotalFM = view.findViewById(R.id.tv_GrandTotalFM);
        buttonPlaceRoomServiceOrder = view.findViewById(R.id.buttonPlaceRoomServiceOrder);

        RecyclerView recyclerView = view.findViewById(R.id.rv_place_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PlaceOrderAdapter adapter = new PlaceOrderAdapter(orderList, this);
        recyclerView.setAdapter(adapter);

        // Calculate the initial total sum and set it to the TextView
        updateGrandTotal();

        buttonPlaceRoomServiceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherReferencesAndMakeApiCall();
            }
        });

        return view;
    }

    @Override
    public void onTotalSumUpdated(double totalSum) {
        textViewGrandTotalFM.setText(String.valueOf(totalSum));
    }

    private void updateGrandTotal() {
        double totalSum = 0;
        for (PlaceOrderItem item : orderList) {
            totalSum += Double.parseDouble(item.getRate());
        }
        textViewGrandTotalFM.setText(String.valueOf(totalSum));
    }

    private void gatherReferencesAndMakeApiCall() {
        getRoomServiceRef();
        int numberOfLineItems = orderList.size();
        getLineItemRoomServiceRefs(numberOfLineItems);

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
            String GUID = LoginActivity.getUserId(sharedPreferences);
            Log.e(TAG, "Project Space GUID/LoginDeviceId: " + GUID);
            String loginDeviceId = GUID.trim();

            SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String savedLoginDeviceId = sharedPreferences2.getString("LoginToken", "");
            Log.e(TAG, "Project Space loginToken: " + savedLoginDeviceId);
            String loginToken = savedLoginDeviceId.trim();

            raiseRoomServiceRequest(loginToken, loginDeviceId);
        }, 2000); // Adjust the delay as necessary to ensure references are fetched
    }

    private void raiseRoomServiceRequest(String loginToken, String loginDeviceId) {
        // Prepare the JSON payload
        List<RoomServiceRequest.LineItem> lineItems = new ArrayList<>();
        int index = 0;
        for (PlaceOrderItem item : orderList) {

                RoomServiceRequest.LineItem lineItem = new RoomServiceRequest.LineItem();
                lineItem.setRef(lineItemRoomServiceRefs.get(index)); // Use unique reference from the list
                lineItem.setGAAProjectSpaceInRoomDiningRequestRef(AppConstants.RoomServiceRef);
                lineItem.setRBItemRef(String.valueOf(item.getRef()));
                lineItem.setQuantity(Double.parseDouble(item.getQuantity()));

                lineItems.add(lineItem);
                index++;

        }
        SharedPreferences sharedPreferences3 = requireContext().getSharedPreferences("MyPrefsPSR", MODE_PRIVATE);
        String saveProjectSpaceRef = sharedPreferences3.getString("Project_Space_Ref", "");
        Log.e(TAG, "Project Space Ref: " + saveProjectSpaceRef);
        String projectSpaceRef = saveProjectSpaceRef.trim();

        RoomServiceRequest roomServiceRequest = new RoomServiceRequest();
        roomServiceRequest.setRef(AppConstants.RoomServiceRef);
        roomServiceRequest.setGAAProjectSpaceRef(projectSpaceRef);
        roomServiceRequest.setLineItems(lineItems);

        // Make the API call
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<RoomServiceApiResponse> call = apiService.raiseRoomServiceRequest(loginToken, loginDeviceId, roomServiceRequest);

        call.enqueue(new Callback<RoomServiceApiResponse>() {
            @Override
            public void onResponse(Call<RoomServiceApiResponse> call, Response<RoomServiceApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Request raised successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to raise request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoomServiceApiResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRoomServiceRef() {
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

                        AppConstants.RoomServiceRef = responseModel.getTag();

                        Log.d(EventBus.TAG, "Success2: " + success + ", Message2: " + message+ " Room Service Ref: "+AppConstants.RoomServiceRef);
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

    private List<String> lineItemRoomServiceRefs = new ArrayList<>();

    private void getLineItemRoomServiceRefs(int numberOfRefs) {
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
                            lineItemRoomServiceRefs.add(uniqueRef);

                            Log.d(EventBus.TAG, "Success: " + success + ", Message: " + message + " UniqueRoomServiceRef: " + uniqueRef);
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

}
