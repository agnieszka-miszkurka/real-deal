package com.example.misradbru.realdeal.addproduct;

import android.support.annotation.NonNull;

import com.example.misradbru.realdeal.data.Product;
import com.example.misradbru.realdeal.data.ProductRepository;

public class AddProductPresenter implements AddProductContract.UserActionsListener {

    private AddProductContract.View mAddProductView;
    private ProductRepository mProductRepository;

    AddProductPresenter(@NonNull AddProductContract.View addProductView,
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
                Product product = new Product(productName, searchPhrase, minPriceInt, maxPriceInt, uid);
                mProductRepository.saveProduct(product);
                mAddProductView.showProductList();
            } else {
                mAddProductView.showMinMaxPriceMismatch();
            }
        }
    }
}
