package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gladiance.R;

import java.util.ArrayList;

public class LaundryItemAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private Context context;
    private OnItemSelectionChangedListener selectionChangedListener;

    public LaundryItemAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    public void setOnItemSelectionChangedListener(OnItemSelectionChangedListener listener) {
        this.selectionChangedListener = listener;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.dropdown_item_laundry, parent, false);
        TextView textView = row.findViewById(R.id.textviewLaundryItem);

        textView.setText(items.get(position));

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionChangedListener != null) {
                    ArrayList<String> selectedItem = new ArrayList<>();
                    selectedItem.add(items.get(position));
                    selectionChangedListener.onItemSelectionChanged(selectedItem);
                }
            }
        });

        return row;
    }

    public interface OnItemSelectionChangedListener {
        void onItemSelectionChanged(ArrayList<String> selectedItems);
    }
}
