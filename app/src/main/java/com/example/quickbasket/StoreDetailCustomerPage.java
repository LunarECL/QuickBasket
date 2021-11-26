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
import java.util.Map;

public class StoreDetailCustomerPage extends AppCompatActivity {
    ArrayList<Product> products;
    String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail_customer_page);

        ImageButton backButton = findViewById(R.id.backButton_StoreDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_screen_customer.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String StoreID = intent.getStringExtra("ID");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Product").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    collectProducts((Map<String, Object>) task.getResult().getValue(), Integer.valueOf(StoreID));
                }
            }
        });
        mDatabase.child("StoreOwner").child(StoreID).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    storeName = String.valueOf(task.getResult().getValue());
                }
            }
        });

        TextView t1 = (TextView) findViewById(R.id.StoreName);
        t1.setText(StoreID);

        for(Product product : products){
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setClickable(true);

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProductDetailCustomerPage.class);
                    String productID = v.getContentDescription().toString();
                    intent.putExtra("ID", productID);
                    intent.putExtra("StoreID", StoreID);
                    startActivity(intent);
                }
            });

            String ProductID = String.valueOf(product.id);

            ll.setContentDescription(ProductID);

            layoutParams.setMargins(20, 0, 20, 0);

            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams imgl = new LinearLayout.LayoutParams(600, 600);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            new URLImageTask(img).execute(product.imageURL);

            TextView tv1 = new TextView(this);
            tv1.setText(product.name);
            LinearLayout.LayoutParams tv11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tv1.setGravity(Gravity.CENTER);

            TextView tv2 = new TextView(this);
            tv2.setText("$" + product.price);
            tv2.setGravity(Gravity.CENTER);

            ll.addView(img, imgl);
            ll.addView(tv1, tv11);
            ll.addView(tv2, tv11);
            LinearLayout l0 = (LinearLayout) findViewById(R.id.Store_Products);
            l0.addView(ll, layoutParams);
        }
    }

    private void collectProducts(Map<String,Object> rawProducts, Integer storeid) {
        for (Map.Entry<String, Object> entry : rawProducts.entrySet()){
            Product product = (Product) entry.getValue();
            if (product.storeid == storeid){
                products.add(product);
            }
        }
    }


}