package com.example.misradbru.realdeal.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.Product;

import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Product> {

    public ProductsAdapter(Context context, List<Product> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_item_name);
        Product product = getItem(position);
        titleTextView.setText(product.getName());

        return convertView;
    }

}
