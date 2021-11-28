package com.example.quickbasket;

import java.util.Base64;

public class StoreOwner extends User{
    String storeName;
    String location;
    String logoURL;

    public StoreOwner(String name, String password, String storeName, String location, String logoURL) {
        super(name, password);
        this.storeName = storeName;
        this.location = location;
        this.logoURL = logoURL;
    }
}
