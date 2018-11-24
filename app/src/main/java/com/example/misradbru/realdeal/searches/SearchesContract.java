package com.example.misradbru.realdeal.searches;

public interface SearchesContract {
    interface View {

        void setProgressIndicator(boolean active);

    }

    interface UserActionListener {

        void showFoundProducts(String uid, SearchesAdapter searchesAdapter);
    }
}
