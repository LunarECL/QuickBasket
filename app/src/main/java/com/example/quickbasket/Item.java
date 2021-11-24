package com.example.quickbasket;

/*

Item object class

Contains information about the item such as : product name, price and brand

 */

public class Item {

    private String productName;
    private Double price;
    private String brand;

    public Item(String productName, Double price, String brand){

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
