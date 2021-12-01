package com.example.quickbasket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class main_screen_owner extends AppCompatActivity {
    Integer ownerID;
    String storeName;
    String logoURL;
    String productURL;
    String productDescription;
    ArrayList<OrderListItem> orderList = new ArrayList<OrderListItem>();

    // Setup string containing first 3 (at most) product names from order
    public void setupOrderDescription(ArrayList<Integer> productIDsList) {
        productDescription = "";
        for (int i = 0; i < Math.min(productIDsList.size(), 3); i++) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Product");
            ref.child(String.valueOf(productIDsList.get(i))).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("demo", "Error getting data", task.getException());
                    }
                    else {
                        Log.i("demo", task.getResult().getValue().toString());
                        productDescription.concat((String) task.getResult().getValue() + "\n");
                    }
                }
            });
        }
        if (productIDsList.size() > 3)
            productDescription.concat("\n...");
    }

    // Setup string containing url of first image from order
    public void setupOrderUrl(Integer productID) {
        productURL = "";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Product");
        ref.child(String.valueOf(productID)).child("imageURL").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    Log.i("demo", task.getResult().getValue().toString());
                    productURL = (String) task.getResult().getValue();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_owner);

        // Get owner id from previous activity
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

        // Get store name and logo
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("StoreOwner");
        ref1.child(String.valueOf(ownerID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    Log.i("demo", task.getResult().getValue().toString());
                    storeName = (String) task.getResult().child("StoreName").getValue();
                    logoURL = (String) task.getResult().child("StoreImage").getValue();
                }
            }
        });

        // Get information each order
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Order");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    if(child.child("OwnerID").equals(String.valueOf(ownerID))) {
                        ArrayList<Integer> productIDsList = new ArrayList<Integer>();
                        for (DataSnapshot productID : child.child("ProductIDsList").getChildren()) {
                            productIDsList.add((Integer) productID.getValue());
                        }
                        setupOrderDescription(productIDsList);
                        String description = "";
                        description.concat(productDescription);
                        setupOrderUrl(productIDsList.get(0));
                        String url = "";
                        url.concat(productURL);
                        orderList.add(new OrderListItem(description, url));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref2.addValueEventListener(listener);

        // Set store name to storeName
        TextView storeNameTextView = (TextView) findViewById(R.id.textView12);
        storeNameTextView.setText(storeName);

        // Set logo image to image corresponding to logoURL
        ImageView logoImageView = (ImageView) findViewById(R.id.cart_Button2);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop();
        Glide.with(this).load(logoURL).apply(options).into(logoImageView);

        // Setting up recycler view and displaying orders
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        OrderListAdapter adapter = new OrderListAdapter(orderList, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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