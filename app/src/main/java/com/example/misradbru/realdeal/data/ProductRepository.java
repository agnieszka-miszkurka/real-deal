package com.example.misradbru.realdeal.data;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.example.misradbru.realdeal.foundproducts.FoundProductsAdapter;

/**
 * Main entry point for accessing products data.
 */
public interface ProductRepository {
    void saveProduct(@NonNull Product product);
    void getProducts(String userId, Context context, final ListView mProductListView);
    void getFoundProducts(String searchPhrase, final FoundProductsAdapter foundProductsAdapter);
}
