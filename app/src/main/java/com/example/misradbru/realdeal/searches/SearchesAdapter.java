package com.example.misradbru.realdeal.searches;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.SearchProduct;

import java.util.List;

/**
 * Adapter for ListView containing current user searches
 */
public class SearchesAdapter extends ArrayAdapter<SearchProduct> {

    public SearchesAdapter(Context context, List<SearchProduct> objects) {
        super(context, 0, objects);
    }

    @Override
    public @NonNull View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_searchproduct, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.list_item_name);
        SearchProduct searchProduct = getItem(position);

        assert searchProduct != null;
        titleTextView.setText(searchProduct.getName());

        return convertView;
    }

}
