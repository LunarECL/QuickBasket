package com.example.quickbasket;

public class Customer extends User{
    Integer id;
    String username;
    public Customer(Integer id, String username, String name, String password) {
        super(name, password);
        this.id = id;
        this.username = username;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

}
