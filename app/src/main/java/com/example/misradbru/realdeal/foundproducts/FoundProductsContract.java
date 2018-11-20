package com.example.misradbru.realdeal.foundproducts;

public interface FoundProductsContract {

    interface View {

        void setProgressIndicator(boolean active);

    }

    interface UserActionsListener {

        void showFoundProducts(String searchPhrase, FoundProductsAdapter foundProductsAdapter);

        void deleteProduct();
    }
}
