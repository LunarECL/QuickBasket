package com.example.quickbasket;

public class Customer extends User{
    Integer id;
    String name;
    public Customer(Integer id, String username, String name, String password) {
        super(username, password);
        this.id = id;
        this.name = name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
