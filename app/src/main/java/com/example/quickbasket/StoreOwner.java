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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }
}
