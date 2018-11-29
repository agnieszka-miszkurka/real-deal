package com.example.misradbru.realdeal.data;


import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.foundproducts.FoundProductsAdapter;
import com.example.misradbru.realdeal.searches.SearchesAdapter;

/**
 * Main entry point for accessing products data.
 */
public interface ProductRepository {
    void saveSearchProduct(@NonNull SearchProduct searchProduct);

    void getSearches(String userId, SearchesAdapter mSearchesAdapter);

    void getFoundProducts(String searchPhrase, final FoundProductsAdapter foundProductsAdapter);

    void deleteSearch(String searchId);
}
