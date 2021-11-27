package com.example.quickbasket;

public class Product {
    Integer id;

    String name;
    String description;
    String brand;
    double price;
    String imageURL;

    public Product(String name, String description, String brand, double price, String imageURL){
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.imageURL = imageURL;
    }
}
