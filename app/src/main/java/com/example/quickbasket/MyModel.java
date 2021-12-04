package com.example.quickbasket;

import android.renderscript.Sampler;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyModel implements Contract.Model{
    Boolean flag = false;
    Boolean flag1 = false;
    ArrayList<String> customerUsernames = new ArrayList<String>();
    ArrayList<String> ownerUsernames = new ArrayList<String>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public MyModel(){
        ref.child(Constant.Customer).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Customer customer = child.getValue(Customer.class);
                    customerUsernames.add(customer.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref.child(Constant.StoreOwner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    StoreOwner owner = child.getValue(StoreOwner.class);
                    ownerUsernames.add(owner.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean customerExists(String username){
        return customerUsernames.contains(username);
    }

    public boolean ownerExists(String username){
        return ownerUsernames.contains(username);
    }

    public boolean customerPasswordMatches(String username, String password){
        ref.child(Constant.Customer).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Customer customer = child.getValue(Customer.class);
                    if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                        flag = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return flag;
    }

    public boolean ownerPasswordMatches(String username, String password){
        ref.child(Constant.StoreOwner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    StoreOwner owner = child.getValue(StoreOwner.class);
                    if (owner.getUsername().equals(username) && owner.getPassword().equals(password)){
                        flag1 = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return flag1;
    }
}
