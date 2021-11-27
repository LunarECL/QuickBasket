package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class ProductDetailCustomerPage extends AppCompatActivity {
    Product product;
    String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_customer_page);


        Intent intent = getIntent();
        String productID = intent.getStringExtra("ID");
        String storeID = intent.getStringExtra("StoreID");
        ImageButton backButton = findViewById(R.id.backButton_ProductDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreDetailCustomerPage.class);
                intent.putExtra("ID", storeID);
                startActivity(intent);
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("StoreOwner").child(storeID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Map storeMap = (Map) task.getResult().getValue();
                    storeName = String.valueOf(storeMap.get("name"));
                    Map<String, Object> productsMap = (Map) storeMap.get("Product");



                    for (Map.Entry<String, Object> entry : productsMap.entrySet()){
                        Map<String, Object> productMap = (Map<String, Object>) entry.getValue();
                        if (String.valueOf(productMap.get("id")) == productID){
                            product = new Product(String.valueOf(productMap.get("name")), String.valueOf(productMap.get("description")), String.valueOf(productMap.get("brand")), Double.valueOf(String.valueOf(productMap.get("price"))), String.valueOf(productMap.get("imageURL")));
                        }
                    }
                }
            }
        });



        TextView t1 = (TextView) findViewById(R.id.StoreName);
        t1.setText(storeName);

        TextView t2 = (TextView) findViewById(R.id.Description);
        t2.setText(product.description);

        TextView t3 = (TextView) findViewById(R.id.Price);
        t3.setText("$" + product.price);
    }

    public void onMinus(View view){
        TextView t1 = (TextView) findViewById(R.id.quantity);
        int value = Integer.parseInt(t1.getText().toString());
        if (value < 1) {
            return;
        }
        value--;
        t1.setText(String.valueOf(value));
        TextView t2 = (TextView) findViewById(R.id.Price);
        Double price = Double.valueOf(t2.getContentDescription().toString());
        t2.setText("$"+ Math.round(price*value*100.0)/100.0);
    }

    public void onPlus(View view){
        TextView t1 = (TextView) findViewById(R.id.quantity);
        int value = Integer.parseInt(t1.getText().toString());
        value++;
        t1.setText(String.valueOf(value));

        TextView t2 = (TextView) findViewById(R.id.Price);
        Double price = Double.valueOf(t2.getContentDescription().toString());
        t2.setText("$"+ Math.round(price*value*100.0)/100.0);
    }
}