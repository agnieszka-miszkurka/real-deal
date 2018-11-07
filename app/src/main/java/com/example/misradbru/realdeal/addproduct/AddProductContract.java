package com.example.misradbru.realdeal.addproduct;

public interface AddProductContract {

    interface View {

        void showEmptyNoteError();

        void showNotesList();

    }

    interface UserActionsListener {

        void saveProduct(String productName, String searchPhrase, String minPrice, String maxPrice);

    }
}
