package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreDetailCustomerPage extends AppCompatActivity {
    ArrayList<Product> products;
    String StoreName;
    String StoreLocation;
    String StoreID;
    String CustomerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail_customer_page);

        Intent intent = getIntent();

        StoreID = intent.getStringExtra(Constant.OwnerID);
        CustomerID = intent.getStringExtra(Constant.CustomerID);

        ImageButton backButton = findViewById(R.id.backButton_StoreDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainScreenCustomer.class);
                intent.putExtra(Constant.CustomerID, CustomerID);
                startActivity(intent);
            }
        });



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("StoreOwner").child(StoreID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Map storeMap = (Map) task.getResult().getValue();
                    StoreName = String.valueOf(storeMap.get(Constant.StoreName));
                    Log.d("name", StoreName);
                    StoreLocation = String.valueOf(storeMap.get(Constant.StoreLocation));
                    products = new ArrayList<>();
                    if (storeMap.get(Constant.Product) instanceof ArrayList) {
                        ArrayList<Map> productsList = (ArrayList<Map>) storeMap.get(Constant.Product);
                        for (Map<String, String> productMap : productsList) {
                            if (productMap == null) {
                                continue;
                            }
                            Product product = new Product(Integer.valueOf(String.valueOf(productMap.get("id"))), String.valueOf(productMap.get("name")), String.valueOf(productMap.get(Constant.ProductDescription)), String.valueOf(productMap.get(Constant.ProductBrand)), Double.valueOf(String.valueOf(productMap.get(Constant.ProductPrice))), String.valueOf(productMap.get(Constant.ProductImageURl)));
                            products.add(product);
                        }
                    }
                    else {
                        HashMap<Integer, Object> productsList = (HashMap<Integer, Object>) storeMap.get(Constant.Product);
                        for (Map.Entry<Integer, Object> productrawMap : productsList.entrySet()) {
                            Map<String, String> productMap = (Map<String, String>) productrawMap.getValue();
                            Log.d("map", String.valueOf(productMap));
                            Product product = new Product(Integer.valueOf(String.valueOf(productMap.get("id"))), String.valueOf(productMap.get("name")), String.valueOf(productMap.get(Constant.ProductDescription)), String.valueOf(productMap.get(Constant.ProductBrand)), Double.valueOf(String.valueOf(productMap.get(Constant.ProductPrice))), String.valueOf(productMap.get(Constant.ProductImageURl)));
                            Log.d("product", String.valueOf(product.id));
                            products.add(product);
                        }
                    }
                }
                getReadt();
            }
        });

    }
    private void getReadt(){
        TextView t1 = (TextView) findViewById(R.id.StoreName);
        t1.setText(StoreName);

        TextView t2 = (TextView) findViewById(R.id.StoreDetailCustomerLocation);
        t2.setText(StoreLocation);

        for(Product product : products){
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ll.setContentDescription(String.valueOf(product.id));

            ll.setClickable(true);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProductDetailCustomerPage.class);
                    String productID = v.getContentDescription().toString();
                    intent.putExtra("ID", productID);
                    intent.putExtra("StoreID", StoreID);
                    intent.putExtra("CustomerID", CustomerID);

                    startActivity(intent);
                }
            });

            String ProductID = String.valueOf(product.id);

            ll.setContentDescription(ProductID);

            layoutParams.setMargins(20, 0, 20, 0);

            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams imgl = new LinearLayout.LayoutParams(800, 800);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            new URLImageTask(img).execute(product.imageURL);

            TextView tv1 = new TextView(this);
            tv1.setText(product.name);
            tv1.setTextSize(50);
            LinearLayout.LayoutParams tv11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tv1.setGravity(Gravity.CENTER);

            TextView tv2 = new TextView(this);
            tv2.setText("$" + product.price);
            tv2.setTextSize(30);
            tv2.setGravity(Gravity.CENTER);

            ll.addView(img, imgl);
            ll.addView(tv1, tv11);
            ll.addView(tv2, tv11);
            LinearLayout l0 = (LinearLayout) findViewById(R.id.Store_Products);
            l0.addView(ll, layoutParams);
        }
    }

    public void onClickCart(View view){
        Intent intent = new Intent(getApplicationContext(), CustomerCheckout.class);
        intent.putExtra(Constant.OwnerID, StoreID);
        intent.putExtra(Constant.CustomerID, CustomerID);
        startActivity(intent);
    }



}