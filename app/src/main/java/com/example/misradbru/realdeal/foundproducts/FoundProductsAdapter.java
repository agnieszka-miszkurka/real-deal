package com.example.misradbru.realdeal.foundproducts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.misradbru.realdeal.R;
import com.example.misradbru.realdeal.data.FoundProduct;

import java.util.List;

public class FoundProductsAdapter  extends ArrayAdapter<FoundProduct> {

    FoundProductsAdapter(Context context, List<FoundProduct> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_foundproductitem, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.found_product_name_tv);
        TextView providerTextView = convertView.findViewById(R.id.found_product_provider_tv);
        FoundProduct product = getItem(position);
        assert product != null;
        titleTextView.setText(product.getName());
        providerTextView.setText(product.getProvider());

        return convertView;
    }

}