package com.example.quickbasket;

public class Product {
    Integer id;

    Integer storeid;
    String name;
    String description;
    String brand;
    double price;
    String imageURL;

    public Product(Integer storeid, String name, String description, String brand, double price, String imageURL){
        this.storeid = storeid;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.imageURL = imageURL;
    }
}
