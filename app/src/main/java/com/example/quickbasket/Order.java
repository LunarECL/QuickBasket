package com.example.quickbasket;

import java.util.ArrayList;

public class Order {

    int orderID;
    int ownerID;
    int customerID;
    boolean status;
    ArrayList<Integer>  cartProductsIDs;
    ArrayList<Product> cartProducts;

    //used to set the data in the database
    public Order(int orderID, int ownerID, int customerID, ArrayList<Integer> cartProductsIDs, boolean status) {
        this.orderID = orderID;
        this.ownerID = ownerID;
        this.customerID = customerID;
        this.cartProductsIDs = cartProductsIDs;
        this.status = status;
    }

    //used to get the data in the database
    public Order(int ownerID, int customerID, ArrayList<Product> cartProducts) {
        this.ownerID = ownerID;
        this.customerID = customerID;
        this.cartProducts = cartProducts;
    }

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(ArrayList<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Integer> getCartProductsIDs() {
        return cartProductsIDs;
    }

    public void setCartProductsIDs(ArrayList<Integer> cartProductsIDs) {
        this.cartProductsIDs = cartProductsIDs;
    }
}
