package com.example.quickbasket;

import java.util.Base64;

public class StoreOwner extends User{
    String storename;
    String location;
    Base64 image;

    public StoreOwner(String name, String password, String storename, String location, Base64 image) {
        super(name, password);
        this.storename = storename;
        this.location = location;
        this.image = image;
    }
}
