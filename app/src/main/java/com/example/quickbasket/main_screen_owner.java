package com.example.quickbasket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class main_screen_owner extends AppCompatActivity {
    Integer ownerID;
    String storeName;
    String logo;
    //ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_owner);

        // Get ownerID from previous activity
        Intent intent = getIntent();
        ownerID = intent.getIntExtra("ownerID", 0);

        // Back button code
        ImageButton backButton = findViewById(R.id.backButton_StoreOwner);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // TO DO
        // Reading from DB to set up screen

        // Get store name, logo and orders
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("StoreOwner");
        ref.child(String.valueOf(ownerID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    Log.i("demo", task.getResult().getValue().toString());
                    storeName = (String) task.getResult().child("StoreName").getValue();
                    logo = (String) task.getResult().child("StoreImage").getValue();

                    // TO DO
                    // Get orders

                }
            }
        });

        // Set store name to storeName
        TextView storeNameTextView = (TextView) findViewById(R.id.textView12);
        storeNameTextView.setText(storeName);

        // Set logo to logo
        ImageButton logoImageButton = (ImageButton) findViewById(R.id.cart_Button2);
//        logoImageButton.

        // Setup orders
        // get orders and display on screen
    }

    // View products button code
    public void onViewProductsClick(View view) {
        Intent intent = new Intent(this, ViewProductsOwner.class);
        intent.putExtra("ownerID", ownerID);
        startActivity(intent);
    }

    // Add new product button code
    public void onAddNewProductClick(View view) {
        Intent intent = new Intent(this, ViewOrderOwner.class);
        intent.putExtra("ownerID", ownerID);
        startActivity(intent);
    }
}