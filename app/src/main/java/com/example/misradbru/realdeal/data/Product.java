package com.example.misradbru.realdeal.data;

public final class Product {
    private final String name;
    private final String searchPhrase;
    private final Integer minPrice;
    private final Integer maxPrice;
    private final String uid;


    public Product(String name, String searchPhrase, Integer minPrice, Integer maxPrice, String uid) {
        this.name = name;
        this.searchPhrase = searchPhrase;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public String getUid() {
        return uid;
    }
}
