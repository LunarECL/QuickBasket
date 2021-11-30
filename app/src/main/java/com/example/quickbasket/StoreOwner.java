package com.example.quickbasket;

import java.util.ArrayList;
import java.util.Base64;

public class StoreOwner extends User{
    int ownerID;
    String storeName;
    String location;
    String logoURL;
    ArrayList<String> storeProductIDs = new ArrayList<>();

    public StoreOwner(int ownerID, String storeName, String location, String logoURL, ArrayList<String> storeProductIDs) {
        this.ownerID = ownerID;
        this.storeName = storeName;
        this.location = location;
        this.logoURL = logoURL;
        this.storeProductIDs = storeProductIDs;
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
