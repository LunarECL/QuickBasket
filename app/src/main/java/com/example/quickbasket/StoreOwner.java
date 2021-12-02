package com.example.quickbasket;

import java.util.ArrayList;
import java.util.Base64;

public class StoreOwner extends User{
    Integer ownerID;
    String storeName;
    String location;
    String logoURL;
    ArrayList<String> storeProductIDs = new ArrayList<>();

    public StoreOwner(){}

    public StoreOwner(Integer ownerID, String username, String password, String storeName, String location, String logoURL, ArrayList<String> storeProductIDs) {
        super(username, password);
        this.ownerID = ownerID;
        this.storeName = storeName;
        this.location = location;
        this.logoURL = logoURL;
        this.storeProductIDs = storeProductIDs;
    }

    public StoreOwner(int ownerID, String storeName, String location, String logoURL, ArrayList<String> storeProductIDs) {
        this.ownerID = ownerID;
        this.storeName = storeName;
        this.location = location;
        this.logoURL = logoURL;
        this.storeProductIDs = storeProductIDs;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public Integer getOwnerID(){return ownerID;}

    public ArrayList<String> getStoreProductIDs() {
        return storeProductIDs;
    }

    public void setStoreProductIDs(ArrayList<String> storeProductIDs) {
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
