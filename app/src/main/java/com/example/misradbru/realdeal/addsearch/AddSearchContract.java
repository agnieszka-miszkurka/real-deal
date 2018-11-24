package com.example.misradbru.realdeal.addsearch;

public interface AddSearchContract {

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
