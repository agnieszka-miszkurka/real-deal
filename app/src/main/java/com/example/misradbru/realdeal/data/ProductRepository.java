package com.example.misradbru.realdeal.data;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Main entry point for accessing products data.
 */
public interface ProductRepository {
    void saveProduct(@NonNull Product product);
    void getProducts(FirebaseAuth mAuth, Context context, final ListView mProductListView);
}
