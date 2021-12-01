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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class main_screen_owner extends AppCompatActivity {
    Integer ownerID;
    String storeName;
    String logoURL;
    ArrayList<Order> orders;

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


        /////////////////////////////////////// Get Orders /////////////////////////////////////////

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
                    logoURL = (String) task.getResult().child("StoreImage").getValue();

                    // TO DO
                    // get orders


                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////


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


        /////////////////////////////////////// Display Orders /////////////////////////////////////

        // TO DO
        // get first product image in each order and display it in the recycler view
        // write first 3 products in each order in the recycler view
        // when order list item pressed go to next screen (pass order id to next screen?)

        // Test code for recycler view
        OrderListItem[] orderList = new OrderListItem[] {
                new OrderListItem("Apple\nOrange\nBanana\n...", "https://assets.epicurious.com/photos/560d459cf3a00aeb2f1c31c4/6:4/w_1998,h_1332,c_limit/apples.jpg"),
                new OrderListItem("Orange\nApple\nBanana\n...", "https://cdn1.sph.harvard.edu/wp-content/uploads/sites/30/2018/08/bananas-1354785_1920-1200x800.jpg"),
                new OrderListItem("Banana\nApple\nOrange\n...", "https://cdn1.sph.harvard.edu/wp-content/uploads/sites/30/2018/08/bananas-1354785_1920-1200x800.jpg"),
                new OrderListItem("Apple\nOrange\nBanana", "https://www.treetop.com/wp-content/uploads/2019/03/apples-bg-190329.jpg"),
                new OrderListItem("Apple\nOrange\nBanana", "https://www.treetop.com/wp-content/uploads/2019/03/apples-bg-190329.jpg"),
                new OrderListItem("Apple\nOrange\nBanana", "https://www.treetop.com/wp-content/uploads/2019/03/apples-bg-190329.jpg"),
                new OrderListItem("Banana\nApple\nOrange", "https://cdn1.sph.harvard.edu/wp-content/uploads/sites/30/2018/08/bananas-1354785_1920-1200x800.jpg"),
                new OrderListItem("Orange\nApple\nBanana", "https://cdn1.sph.harvard.edu/wp-content/uploads/sites/30/2018/08/bananas-1354785_1920-1200x800.jpg"),
        };

        // Setting up recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        OrderListAdapter adapter = new OrderListAdapter(orderList, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ////////////////////////////////////////////////////////////////////////////////////////////
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