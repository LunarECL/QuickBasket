package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class main_screen_owner extends AppCompatActivity {
    String storeName;
    String logo;
    //ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_owner);

        // Back Button
        ImageButton backButton = findViewById(R.id.backButton_StoreOwner);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Reading from DB to setup screen

        // get store name
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("");
        ref.child("").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    // Get store name
                    Log.i("demo", task.getResult().getValue().toString());
                    storeName = (String) task.getResult().getValue();
                }
            }
        });*/

        // get logo
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("");
        ref.child("").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    // Get store name
                    Log.i("demo", task.getResult().getValue().toString());
                    logo = (String) task.getResult().getValue();
                }
            }
        });*/

        // get orders
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("");
        ref.child("").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    // Get store name
                    Log.i("demo", task.getResult().getValue().toString());
                    //orders = (ArrayList<Order>) task.getResult().getValue();
                }
            }
        });*/


        // Set store name to storeName
        /*TextView storeNameTextView = (TextView) findViewById(R.id.textView12);
        storeNameTextView.setText(storeName);*/

        // Set logo to logo
        // get image urland change image

        // Setup orders
        // get orders and display on screen
    }

    public void onViewProductsClick(View view) {
        Intent intent = new Intent(this, ViewProductsOwner.class);
        startActivity(intent);
    }

    public void onAddNewProductClick(View view) {
        Intent intent = new Intent(this, ViewOrderOwner.class);
        startActivity(intent);
    }
}