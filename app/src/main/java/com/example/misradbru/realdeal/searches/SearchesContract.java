package com.example.misradbru.realdeal.searches;

import com.example.misradbru.realdeal.data.SearchProduct;

public interface SearchesContract {
    interface View {

        void setProgressIndicator(boolean active);

        void showNoSearchesMessage(boolean show);

        void showAddSearch();

        void showFoundProductsUi(SearchProduct searchProduct);

    }

    interface UserActionListener {

        void loadSearchProducts(String uid, SearchesAdapter searchesAdapter);

        void openFoundProducts(SearchProduct searchProduct);

        void addNewSearch();
    }
}
