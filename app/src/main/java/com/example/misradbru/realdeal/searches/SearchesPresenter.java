package com.example.misradbru.realdeal.searches;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.ProductRepository;
import com.example.misradbru.realdeal.data.SearchProduct;

public class SearchesPresenter implements SearchesContract.UserActionListener {

    private ProductRepository mProductRepository;
    private SearchesContract.View mSearchesView;

    SearchesPresenter(@NonNull ProductRepository productRepository,
                           @NonNull SearchesContract.View searchesView) {
        mSearchesView = searchesView;
        mProductRepository = productRepository;

    }
    @Override
    public void loadSearchProducts(String uid, SearchesAdapter searchesAdapter) {
        registerDataSetObserver(searchesAdapter);
        mSearchesView.setProgressIndicator(true);
        mProductRepository.getSearches(uid, searchesAdapter);
    }

    @Override
    public void openFoundProducts(SearchProduct searchProduct) {
        mSearchesView.showFoundProductsUi(searchProduct);
    }

    @Override
    public void addNewSearch() {
        mSearchesView.showAddSearch();
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
