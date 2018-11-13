package com.example.misradbru.realdeal.products;

import java.util.ArrayList;

public class SearchListItemsProvider {
    public ArrayList<String> provideItems() {
        ArrayList<String> items = new ArrayList<>();
        for (int i=1;i<21;i++) {
            items.add("Item number " + i);
        }
        return items;
    }
}
