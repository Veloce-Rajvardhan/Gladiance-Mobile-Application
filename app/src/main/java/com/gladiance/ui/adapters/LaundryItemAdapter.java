package com.gladiance.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.gladiance.R;

import java.util.ArrayList;

public class LaundryItemAdapter extends ArrayAdapter<String> {

    private ArrayList<String> items;
    private ArrayList<String> selectedItems;
    private Context context;
    private OnItemSelectionChangedListener selectionChangedListener;

    public LaundryItemAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
        this.selectedItems = new ArrayList<>();
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
        final CheckBox checkBox = row.findViewById(R.id.checkbox);

        checkBox.setText(items.get(position));
        checkBox.setChecked(selectedItems.contains(items.get(position)));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    selectedItems.add(items.get(position));
                } else {
                    selectedItems.remove(items.get(position));
                }
                if (selectionChangedListener != null) {
                    selectionChangedListener.onItemSelectionChanged(selectedItems);
                }
            }
        });

        return row;
    }

    public ArrayList<String> getSelectedItems() {
        return selectedItems;
    }

    public interface OnItemSelectionChangedListener {
        void onItemSelectionChanged(ArrayList<String> selectedItems);
    }
}
