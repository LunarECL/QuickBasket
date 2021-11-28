package com.example.quickbasket;

import java.util.Base64;

public class StoreOwner extends User{
    String storeName;
    String location;
    String logoURL;

    public StoreOwner(String username, String password, String storeName, String location, String logoURL) {
        super(username, password);
        this.storeName = storeName;
        this.location = location;
        this.logoURL = logoURL;
    }
}
