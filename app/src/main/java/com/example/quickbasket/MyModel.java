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
    ArrayList<String> usernames = new ArrayList<String>();
    public MyModel(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Customer customer = child.getValue(Customer.class);
                    usernames.add(customer.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);
    }

    public boolean userExists(String username){
        return usernames.contains(username);
    }


}
