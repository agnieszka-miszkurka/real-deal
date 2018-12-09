package com.example.misradbru.realdeal.foundproducts;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.FoundProduct;
import com.example.misradbru.realdeal.data.ProductRepository;

public class FoundProductsPresenter implements FoundProductsContract.UserActionsListener {

    private ProductRepository mProductRepository;
    private FoundProductsContract.View mFoundProductsView;
    DataSetObserver dataSetObserver;

    FoundProductsPresenter(@NonNull ProductRepository productRepository,
                                  @NonNull FoundProductsContract.View foundProductView) {
        mFoundProductsView = foundProductView;
        mProductRepository = productRepository;
        dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                mFoundProductsView.setProgressIndicator(false);
            }
        };

    }

    @Override
    public void showFoundProducts(String searchId, FoundProductsAdapter foundProductsAdapter) {

        registerDataSetObserver(foundProductsAdapter);
        mFoundProductsView.setProgressIndicator(true);
        mProductRepository.getFoundProducts(searchId, foundProductsAdapter);
    }

    @Override
    public void foundProductClicked(FoundProduct foundProduct) {
        mFoundProductsView.openFoundProductPage(foundProduct.getLink());
    }

    private void registerDataSetObserver(FoundProductsAdapter foundProductsAdapter) {
        foundProductsAdapter.registerDataSetObserver(dataSetObserver);
    }

    @Override
    public void deleteProduct(String searchId) {
        mProductRepository.deleteSearch(searchId);
        mFoundProductsView.showSearches();
    }
}
