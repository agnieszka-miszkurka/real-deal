package com.example.misradbru.realdeal.data;


import android.support.annotation.NonNull;

/**
 * Main entry point for accessing products data.
 */
public interface ProductRepository {
    void saveProduct(@NonNull Product product);
}
