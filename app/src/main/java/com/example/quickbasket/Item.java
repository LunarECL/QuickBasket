package com.example.quickbasket;

/*

Item object class

Contains information about the item such as : product name, price and brand

 */

public class Item {

    private String productName;
    private String price;
    private String brand;

    public Item(String productName, String price, String brand){

        this.productName = productName;
        this.price = price;
        this.brand = brand;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
