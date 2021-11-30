package com.example.quickbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class MainScreenCustomer extends Activity implements MainScreenCustomerRecyclerViewAdapter.OnNoteListener{
    private ArrayList<String> mStoreNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mLocations = new ArrayList<>();
    private ArrayList<String> mStoreIDs = new ArrayList<>();

    private ArrayList<StoreOwner> owners = new ArrayList<>();
    private int customerID;
    private String customerName;
    DatabaseReference entireDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_customer);

        //Get Customer ID from previous page
        Intent intent = getIntent();
        customerID = intent.getIntExtra("customerID", 0);

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButton_MainCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });

        entireDB.child("Customer").child(String.valueOf(customerID)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        Log.d("Customer Stuff", String.valueOf(task.getResult().getValue()));
                        Map customerMap = (Map) task.getResult().getValue();
                        customerName = String.valueOf(customerMap.get("name"));

                        Log.d("Customer Name is ", customerName);
                    }
                }
            });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("StoreOwner").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    ArrayList storeIDMap = (ArrayList) task.getResult().getValue();
                    ArrayList<Map> storeList = (ArrayList<Map>) storeIDMap;
                    int index = 0;
                    //ERROR HERE
                    int check = 0;
                    for (Map<String, Object> entry : storeList){
                        if (entry != null) {
                            String storeName = String.valueOf(entry.get("Name"));
                            String location = String.valueOf(entry.get("Location"));
                            String logoURL = String.valueOf(entry.get("logoURL"));
                            String storeID = String.valueOf(index);
                            Product product = (Product) entry.get("Product");
                            ArrayList<Product> storeProducts = new ArrayList<>();
                            storeProducts.add(product);

                            Log.d("storeIDS are ", storeID);
                            StoreOwner owner = new StoreOwner(storeName, location, logoURL, storeProducts);
                            owners.add(owner);
                            mStoreIDs.add(storeID);
                            index++;
                        }
                    }

                    initImageBitmaps();
                }
            }
        });

        ArrayList<Integer> temp = new ArrayList<>();

        temp.add(0);
        temp.add(1);

        writeNewOrder(1,2,3,temp);
        //writeNewOwner("12", "ankit", "Fruits and Veggies", "Canada", "https://www.ryerson.ca/content/dam/international/admissions/virtual-tour-now.jpg");
    }

    public void writeNewOwner(String userID, String password, String storeName, String location, String logoURL) {
        StoreOwner owner = new StoreOwner(userID, password, storeName, location, logoURL);

        entireDB.child("StoreOwner").child(userID).setValue(owner);
    }

    public void writeNewOrder(int orderID, int ownerID, int customerID, ArrayList<Integer> productIDsList ) {
        Order order = new Order(orderID,ownerID,customerID,productIDsList);

        entireDB.child("Order").child(Integer.toString(orderID)).setValue(order);
    }

    private void initImageBitmaps() {
        if (owners != null){
            for (StoreOwner owner : owners) {
                if (owner != null) {
                    if (owner.storeProducts != null){
                        mImageUrls.add(owner.logoURL);
                        mStoreNames.add(owner.storeName);
                        mLocations.add(owner.location);
                    }
                }
            }
        }

        TextView cName = (TextView) findViewById(R.id.customerName);
        cName.setText(customerName);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.store_list_recycle_view);
        MainScreenCustomerRecyclerViewAdapter adapter = new MainScreenCustomerRecyclerViewAdapter(this, mStoreNames, mImageUrls, mLocations, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, StoreDetailCustomerPage.class);
        Log.d("Value of ID is ", mStoreIDs.get(position));
        intent.putExtra("CustomerID", String.valueOf(customerID));
        intent.putExtra("StoreID", mStoreIDs.get(position));
        startActivity(intent);
    }
}
