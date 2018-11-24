package com.example.misradbru.realdeal.addsearch;

import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.SearchProduct;
import com.example.misradbru.realdeal.data.ProductRepository;

public class AddSearchPresenter implements AddSearchContract.UserActionsListener {

    private AddSearchContract.View mAddProductView;
    private ProductRepository mProductRepository;

    AddSearchPresenter(@NonNull AddSearchContract.View addProductView,
                       @NonNull ProductRepository productRepository) {
        mAddProductView = addProductView;
        mProductRepository = productRepository;
    }

    @Override
    public void saveProduct(String productName, String searchPhrase, String minPrice, String maxPrice, String uid) {
        if (productName.isEmpty() || searchPhrase.isEmpty() || minPrice.isEmpty()
                || maxPrice.isEmpty()) {
            mAddProductView.showEmptyProductError();
        } else {
            Integer minPriceInt = Integer.valueOf(minPrice);
            Integer maxPriceInt = Integer.valueOf(maxPrice);
            if (minPriceInt < maxPriceInt) {
                SearchProduct searchProduct = new SearchProduct(productName, searchPhrase, minPriceInt, maxPriceInt, uid);
                mProductRepository.saveProduct(searchProduct);
                mAddProductView.showProductList();
            } else {
                mAddProductView.showMinMaxPriceMismatch();
            }
        }
    }
}
