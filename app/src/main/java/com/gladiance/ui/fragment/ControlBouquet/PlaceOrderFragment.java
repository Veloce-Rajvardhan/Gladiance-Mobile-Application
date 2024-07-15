package com.gladiance.ui.fragment.ControlBouquet;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gladiance.R;
import com.gladiance.ui.adapters.PlaceOrderAdapter;
import com.gladiance.ui.models.PlaceOrderItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderFragment extends BottomSheetDialogFragment {

    private static final String ARG_ORDER_LIST = "order_list";

    private List<PlaceOrderItem> orderList;

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

        RecyclerView recyclerView = view.findViewById(R.id.rv_place_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PlaceOrderAdapter adapter = new PlaceOrderAdapter(orderList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
