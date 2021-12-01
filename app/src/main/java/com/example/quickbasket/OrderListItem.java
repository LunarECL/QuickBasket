package com.example.quickbasket;

public class OrderListItem {
    private String description;
    private String url;
    private Integer orderID;
    private Integer ownerID;
    private Integer customerID;

    public OrderListItem(String description, String url, Integer orderID, Integer ownerID, Integer customerID) {
        this.description = description;
        this.url = url;
        this.orderID = orderID;
        this.ownerID = ownerID;
        this.customerID = customerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public Integer getOrderID() { return orderID; }

    public void setOrderID(Integer orderID) { this.orderID = orderID; }

    public Integer getOwnerID() { return ownerID; }

    public void setOwnerID(Integer ownerID) { this.ownerID = ownerID; }

    public Integer getCustomerID() { return customerID; }

    public void setCustomerID(Integer customerID) { this.customerID = customerID; }
}
