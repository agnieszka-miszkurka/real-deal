package com.example.misradbru.realdeal.addproduct;

public interface AddProductContract {

    interface View {

        void showEmptyProductError();

        void showProductList();

        void showMinMaxPriceMismatch();

    }

    interface UserActionsListener {

        void saveProduct(String productName, String searchPhrase, String minPrice, String maxPrice,
                         String uid);

    }
}
