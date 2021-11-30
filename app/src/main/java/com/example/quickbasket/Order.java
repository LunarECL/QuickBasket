package com.example.quickbasket;

import java.util.ArrayList;

public class Order {

    int orderID;
    int ownerID;
    int customerID;
    boolean status;
    ArrayList<Integer>  productIDsList;

    public Order(int orderID, int ownerID, int customerID, ArrayList<Integer> productIDsList) {
        this.orderID = orderID;
        this.ownerID = ownerID;
        this.customerID = customerID;
        this.productIDsList = productIDsList;
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

    public ArrayList<Integer> getProductIDsList() {
        return productIDsList;
    }

    public void setProductIDsList(ArrayList<Integer> productIDsList) {
        this.productIDsList = productIDsList;
    }
}
