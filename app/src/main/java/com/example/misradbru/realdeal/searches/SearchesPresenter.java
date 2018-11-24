package com.example.misradbru.realdeal.searches;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.ProductRepository;

public class SearchesPresenter implements SearchesContract.UserActionListener {

    private ProductRepository mProductRepository;
    private SearchesContract.View mSearchesView;

    SearchesPresenter(@NonNull ProductRepository productRepository,
                           @NonNull SearchesContract.View searchesView) {
        mSearchesView = searchesView;
        mProductRepository = productRepository;

    }
    @Override
    public void showFoundProducts(String uid, SearchesAdapter searchesAdapter) {
        registerDataSetObserver(searchesAdapter);
        mSearchesView.setProgressIndicator(true);
        mProductRepository.getSearches(uid, searchesAdapter);
    }

    private void registerDataSetObserver(SearchesAdapter foundProductsAdapter) {
        foundProductsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                mSearchesView.setProgressIndicator(false);
            }
        });
    }
}
