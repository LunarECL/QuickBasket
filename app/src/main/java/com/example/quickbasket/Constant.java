package com.example.quickbasket;

public class Constant {

    //Customer TABLE
    static String Customer = "Customer";
    static String Cart = "cart";
    static String CartProductID = "cartProductID";
    static String CustomerID = "customerID";
    static String CustomerName = "name";
    static String Password = "password";
    static String Username = "username";
    static String userCount = "userCount";
    // Same as the one from StoreOwner Table: static String OwnerID = "ownerID";
    static String ProductID = "productID";
    static String Quantity = "quantity";


    //Order Table
    static String Order = "Order";
    static String orderCount = "orderCount";
    static String OrderID = "orderID"; // Added by Virthiya
    static String CartProductsIDs = "cartProductsIDs"; // Added by Virthiya
    static String Status = "status";


    //StoreOwner Table
    static String OwnerID = "ownerID";
    static String StoreOwner = "StoreOwner";
    static String StoreName = "storeName";
    static String StoreLocation = "location";
    static String StoreLogoURL = "logoURL";
    static String Product = "Product";
    // Stored as direct child of the database (similar to userCount)
    static String ProductIDCount = "productIDCount";
    static String ProductImageURl = "imageURL";
    static String ProductName = "productName";
    static String ProductPrice = "price";
    static String ProductBrand = "brand";
    static String ProductDescription = "description";



}
