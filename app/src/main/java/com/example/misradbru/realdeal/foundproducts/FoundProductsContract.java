package com.example.misradbru.realdeal.foundproducts;

import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.misradbru.realdeal.data.FoundProduct;

public interface FoundProductsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void openFoundProductPage(String url);

        void showSearches();

        ProgressBar getProgressBar();

        Button getButton();

        ListView getFoundProductsList();

    }

    interface UserActionsListener {

        void showFoundProducts(String searchPhrase, FoundProductsAdapter foundProductsAdapter);

        void foundProductClicked(FoundProduct foundProduct);

        void deleteProduct(String searchId);
    }
}
