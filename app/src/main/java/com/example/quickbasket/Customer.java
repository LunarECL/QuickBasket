package com.example.quickbasket;

public class Customer extends User{

    public Customer(String name, String password) {
        super(name, password);
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

}
