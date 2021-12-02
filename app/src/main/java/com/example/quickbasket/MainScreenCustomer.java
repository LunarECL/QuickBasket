package com.example.quickbasket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainScreenCustomer extends Activity implements View.OnClickListener, MainScreenCustomerRecyclerViewAdapter.OnNoteListener{
    private ArrayList<String> mStoreNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mLocations = new ArrayList<>();
    private ArrayList<String> mStoreIDs = new ArrayList<>();

    private ArrayList<StoreOwner> owners = new ArrayList<>();
    private String customerID;
    private String customerName;
    DatabaseReference entireDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_customer);

        //Get Customer ID from previous page
        Intent intent = getIntent();
        customerID = intent.getStringExtra(Constant.CustomerID);
        customerID = "1";

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButton_MainCustomer);
        backButton.setOnClickListener(this);

        entireDB.child(Constant.Customer).child(customerID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        Log.d("Customer Stuff", String.valueOf(task.getResult().getValue()));
                        Map customerMap = (Map) task.getResult().getValue();
                        customerName = String.valueOf(customerMap.get(Constant.CustomerName));

                        Log.d("Customer Name is ", customerName);
                    }
                }
            });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constant.StoreOwner).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            //child(Constant.StoreOwner).
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Map storeIDMap = (Map) task.getResult().getValue();
                    ArrayList<Map> storeList = new ArrayList<Map>(storeIDMap.values());

                    for (Map<String, Object> entry : storeList){
                        if (entry != null) {
                            String storeName = String.valueOf(entry.get(Constant.StoreName));
                            String location = String.valueOf(entry.get(Constant.StoreLocation));
                            String logoURL = String.valueOf(entry.get(Constant.StoreLogoURL));
                            //String storeID = String.valueOf(index);
                            String ownerID = String.valueOf(entry.get(Constant.OwnerID));

                            //Product product = (Product) entry.get("Product");
                            ArrayList<String> storeProductIDs = (ArrayList<String>) entry.get(Constant.StoreListProducts);

                            Log.d("storeIDS are ", ownerID);
                            StoreOwner owner = new StoreOwner(Integer.parseInt(ownerID), storeName, location, logoURL, storeProductIDs);

                            if (owner.storeProductIDs != null) {
                                owners.add(owner);
                                mStoreIDs.add(ownerID);
                            }
                        }
                    }

                    initImageBitmaps();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton_MainCustomer:
                alertDialog();
                break;
        }
    }
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to log out?");
        dialog.setTitle("Confirm");
        dialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(activity2Intent);
                    }
                });
        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
    private void initImageBitmaps() {

        for (StoreOwner owner : owners) {
            mImageUrls.add(owner.logoURL);
            mStoreNames.add(owner.storeName);
            mLocations.add(owner.location);
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
        Log.d("Value of ID is ", String.valueOf(owners.get(position).ownerID));
        intent.putExtra(Constant.CustomerID, customerID);
        intent.putExtra(Constant.OwnerID, String.valueOf(owners.get(position).ownerID));
        startActivity(intent);
    }
}
