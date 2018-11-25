package com.example.misradbru.realdeal.foundproducts;

import com.example.misradbru.realdeal.data.FoundProduct;

public interface FoundProductsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void openFoundProductPage(String url);

    }

    interface UserActionsListener {

        void showFoundProducts(String searchPhrase, FoundProductsAdapter foundProductsAdapter);

        void foundProductClicked(FoundProduct foundProduct);

        void deleteProduct();
    }
}
