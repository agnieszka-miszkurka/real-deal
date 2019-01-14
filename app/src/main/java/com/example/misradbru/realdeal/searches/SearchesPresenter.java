package com.example.misradbru.realdeal.searches;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.ProductRepository;
import com.example.misradbru.realdeal.data.SearchProduct;

public class SearchesPresenter implements SearchesContract.UserActionListener {

    private ProductRepository mProductRepository;
    private SearchesContract.View mSearchesView;

    DataSetObserver dataSetObserver;

    SearchesPresenter(@NonNull ProductRepository productRepository,
                           @NonNull SearchesContract.View searchesView) {
        mSearchesView = searchesView;
        mProductRepository = productRepository;
        dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                mSearchesView.setProgressIndicator(false);
            }
        };
    }

    /**
     * Calls repository function to get searches for user.
     * Before searches are fetched, progress indicator will be active.
     * @param uid - user id
     * @param searchesAdapter - adapter for searches list
     */
    @Override
    public void loadSearchProducts(String uid, SearchesAdapter searchesAdapter) {
        searchesAdapter.registerDataSetObserver(dataSetObserver);
        mSearchesView.setProgressIndicator(true);
        mProductRepository.getSearches(uid, searchesAdapter);
    }

    /**
     * Calls View function to show found products for chosen search.
     *
     * @param searchProduct - search chosen by the user (clicked)
     */
    @Override
    public void openFoundProducts(SearchProduct searchProduct) {
        mSearchesView.showFoundProductsUi(searchProduct);
    }

    /**
     * Calls View function to add new search.
     */
    @Override
    public void addNewSearch() {
        mSearchesView.showAddSearch();
    }
}
