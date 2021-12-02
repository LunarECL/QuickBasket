package com.example.quickbasket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;

public class main_screen_owner extends AppCompatActivity {
    Integer ownerID;
    String storeName;
    String logoURL;
    String productURL;
    String productDescription;
    Double productTotalPrice;
    ArrayList<OrderListItem> orderList = new ArrayList<OrderListItem>();
    ArrayList<ArrayList<Integer>> productIDs = new ArrayList<ArrayList<Integer>>();

    // Get owner id from previous activity
    public void getOwnerIDFromIntent() {
        Intent intent = getIntent();
        ownerID = new Integer(intent.getIntExtra("ownerID", 0));
    }

    // Back button code
    public void backButtonCode() {
        ImageButton backButton = findViewById(R.id.backButton_StoreOwner);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_owner);
        getOwnerIDFromIntent();
        backButtonCode();
        getStoreNameAndLogo();
    }

    // Get store name and logo
    public void getStoreNameAndLogo() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference(Constant.StoreOwner);
        ref1.child(String.valueOf(ownerID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                } else {
                    storeName = (String) task.getResult().child(Constant.StoreName).getValue();
                    logoURL = (String) task.getResult().child(Constant.StoreLogoURL).getValue();
                    setupStoreNameView();
                }
            }
        });
    }

    // Set store name to storeName
    public void setupStoreNameView() {
        TextView storeNameTextView = (TextView) findViewById(R.id.textView12);
        storeNameTextView.setText(storeName);
        setupLogoView();
    }

    // Set logo image to image corresponding to logoURL
    public void setupLogoView() {
        ImageView logoImageView = (ImageView) findViewById(R.id.cart_Button2);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop();
        Glide.with(this).load(logoURL).apply(options).into(logoImageView);
        checkProductIDInDatabase();
    }

    // Setup product ID count in database
    public void checkProductIDInDatabase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("demo", "data changed");
                if (!dataSnapshot.hasChild(Constant.ProductIDCount)) {
                    ref.child(Constant.ProductIDCount).setValue(0);
                }
                getOrderInformation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("warning", "loadPost:onCancelled", databaseError.toException());
            }
        };
        ref.addListenerForSingleValueEvent(listener);
    }

    // Get information about each order
    public void getOrderInformation() {
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference(Constant.Order);
        if (ref2 != null) {
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("demo", "data changed");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (String.valueOf(child.child(Constant.OwnerID).getValue()).equals(String.valueOf(ownerID))) {
                            orderList.add(new OrderListItem("", "", 0.0,
                                    (Math.toIntExact((Long) child.child(Constant.OrderID).getValue())),
                                    (Math.toIntExact((Long) child.child(Constant.OwnerID).getValue())),
                                    (Math.toIntExact((Long) child.child(Constant.CustomerID).getValue()))));
                            productIDs.add(new ArrayList<Integer>());
                            for (DataSnapshot productID : child.child(Constant.CartProductsIDs).getChildren()) {
                                productIDs.get(productIDs.size() - 1).add(Math.toIntExact((Long) productID.getValue()));
                            }
                        }
                    }
                    setupOrderDescription();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("warning", "loadPost:onCancelled", databaseError.toException());
                }
            };
            ref2.addValueEventListener(listener);
        }
    }

    // Setup each order list item with a string containing first 3 (at most) product names from order
    public void setupOrderDescription() {
        productDescription = "";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.StoreOwner);
        ref.child(String.valueOf(ownerID)).child(Constant.StoreListProducts).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (int i = 0; i < orderList.size(); i++) {
                        productDescription = "";
                        for (int j = 0; j < Math.min(productIDs.get(i).size(), 3); j++) {
                            productDescription = productDescription.concat((String) task.getResult().child(String.valueOf(productIDs.get(i).get(j))).child(Constant.ProductName).getValue() + "\n");
                        }
                        if (productIDs.get(i).size() > 3)
                            productDescription.concat("\n...");
                        orderList.get(i).setDescription(orderList.get(i).getDescription().concat(productDescription));
                    }
                    setupOrderUrl();
                }
            }
        });
    }

    // Setup each order list item with string containing url of first image from order
    public void setupOrderUrl() {
        productURL = "";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.StoreOwner);
        ref.child(String.valueOf(ownerID)).child(Constant.StoreListProducts).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (int i = 0; i < orderList.size(); i++) {
                        productURL = (String) task.getResult().child(String.valueOf(productIDs.get(i).get(0))).child(Constant.ProductImageURl).getValue();
                        orderList.get(i).setUrl(orderList.get(i).getUrl().concat(productURL));
                    }
                    setupOrderTotalPrice();
                }
            }
        });
    }

    // Setup each order list item with double containing the total price of the orders
    public void setupOrderTotalPrice() {
        productTotalPrice = 0.0;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.StoreOwner);
        ref.child(String.valueOf(ownerID)).child(Constant.StoreListProducts).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    for (int i = 0; i < orderList.size(); i++) {
                        productTotalPrice = 0.0;
                        for (int j = 0; j < productIDs.get(i).size(); j++) {
                            productTotalPrice += ((Long) task.getResult().child(String.valueOf(productIDs.get(i).get(j))).child(Constant.ProductPrice).getValue()).doubleValue();
                        }
                        productTotalPrice = Math.round(productTotalPrice * 100.0) / 100.0;
                        orderList.get(i).setPrice(productTotalPrice.doubleValue());
                    }
                    setupRecyclerView();
                }
            }
        });
    }

    // Set up recycler view and display orders
    public void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        OrderListAdapter adapter = new OrderListAdapter(orderList, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // View products button code
    public void onViewProductsClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewProductsOwner.class);
        intent.putExtra("ownerID", ownerID.intValue());
        startActivity(intent);
    }

    // Add new product button code
    public void onAddNewProductClick(View view) {
        Intent intent = new Intent(getApplicationContext(), OwnerAddProductPage.class);
        intent.putExtra("ownerID", ownerID.intValue());
        startActivity(intent);
    }
}