package com.example.quickbasket;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Customer extends User{
    Integer id;
    String name;
    public Customer(){};
    public Customer(Integer id, String username, String name, String password) {
        super(username, password);
        this.id = id;
        this.name = name;
    }


    public String getUsername(){ return username; }

    public String getPassword(){
        return password;
    }

    public Integer getId() {return id; }

    public String getName() {return name; }

}
