package com.example.misradbru.realdeal.foundproducts;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.FoundProduct;
import com.example.misradbru.realdeal.data.ProductRepository;

public class FoundProductsPresenter implements FoundProductsContract.UserActionsListener {

    private ProductRepository mProductRepository;
    private FoundProductsContract.View mFoundProductView;

    FoundProductsPresenter(@NonNull ProductRepository productRepository,
                                  @NonNull FoundProductsContract.View foundProductView) {
        mFoundProductView = foundProductView;
        mProductRepository = productRepository;

    }

    @Override
    public void showFoundProducts(String searchId, FoundProductsAdapter foundProductsAdapter) {

        registerDataSetObserver(foundProductsAdapter);
        mFoundProductView.setProgressIndicator(true);
        mProductRepository.getFoundProducts(searchId, foundProductsAdapter);
    }

    @Override
    public void foundProductClicked(FoundProduct foundProduct) {
        mFoundProductView.openFoundProductPage(foundProduct.getLink());
    }

    private void registerDataSetObserver(FoundProductsAdapter foundProductsAdapter) {
        foundProductsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                mFoundProductView.setProgressIndicator(false);
            }
        });
    }

    @Override
    public void deleteProduct(String searchId) {
        mProductRepository.deleteSearch(searchId);
        mFoundProductView.showSearches();
    }
}
