package com.example.misradbru.realdeal.data;

public class FoundProduct {

    private String link;
    private String name;
    private String price;
    private String provider;

    FoundProduct() {}

    FoundProduct(String name, String link, String price, String provider) {
        this.name = name;
        this.link = link;
        this.price = price;
        this.provider = provider;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
