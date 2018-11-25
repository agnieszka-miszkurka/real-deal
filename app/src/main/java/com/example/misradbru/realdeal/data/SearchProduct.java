package com.example.misradbru.realdeal.data;

public final class SearchProduct {
    private String name;
    private String searchPhrase;
    private Integer minPrice;
    private Integer maxPrice;
    private String uid;
    private String searchProductId;

    public SearchProduct() {}

    public SearchProduct(String productName, String searchPhrase, Integer minPriceInt, Integer maxPriceInt, String uid) {
        this.name = productName;
        this.searchPhrase = searchPhrase;
        this.minPrice = minPriceInt;
        this.maxPrice = maxPriceInt;
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

    public String getSearchProductId() {
        return searchProductId;
    }

    public void setSearchProductId(String id) {
        this.searchProductId = id;
    }
}
