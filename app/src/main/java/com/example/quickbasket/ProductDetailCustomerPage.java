package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailCustomerPage extends AppCompatActivity {
    String StoreID;
    String StoreName;
    Product product;
    String ProductID;
    String CustomerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_customer_page);
        Intent intent = getIntent();

        StoreID = intent.getStringExtra("StoreID");
        ProductID = intent.getStringExtra("ID");
        CustomerID = intent.getStringExtra("CustomerID");

        ImageButton backButton = findViewById(R.id.backButton_ProductDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreDetailCustomerPage.class);
                intent.putExtra(Constant.CustomerID, CustomerID);
                intent.putExtra(Constant.OwnerID, StoreID);
                startActivity(intent);
            }
        });



        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constant.StoreOwner).child(StoreID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Map storeMap = (Map) task.getResult().getValue();
                    StoreName = String.valueOf(storeMap.get(Constant.StoreName));
                    if (storeMap.get(Constant.Product) instanceof ArrayList) {
                        ArrayList<Map> productsList = (ArrayList<Map>) storeMap.get(Constant.Product);
                        for (Map<String, String> productMap : productsList)
                        {
                            if (productMap == null){
                                continue;
                            }
                            if (String.valueOf(productMap.get("id")).equalsIgnoreCase(ProductID)) {
                                product = new Product(Integer.valueOf(String.valueOf(productMap.get("id"))), String.valueOf(productMap.get("name")), String.valueOf(productMap.get(Constant.ProductDescription)), String.valueOf(productMap.get(Constant.ProductBrand)), Double.valueOf(String.valueOf(productMap.get(Constant.ProductPrice))), String.valueOf(productMap.get(Constant.ProductImageURl)));
                                break;
                            }
                        }
                    }
                    else {
                        HashMap<Integer, Object> productsList = (HashMap<Integer, Object>) storeMap.get(Constant.Product);
                        for (Map.Entry<Integer, Object> productrawMap : productsList.entrySet()) {
                            Map<String, String> productMap = (Map<String, String>) productrawMap.getValue();
                            if (String.valueOf(productMap.get("id")).equalsIgnoreCase(ProductID)) {
                                product = new Product(Integer.valueOf(String.valueOf(productMap.get("id"))), String.valueOf(productMap.get("name")), String.valueOf(productMap.get(Constant.ProductDescription)), String.valueOf(productMap.get(Constant.ProductBrand)), Double.valueOf(String.valueOf(productMap.get(Constant.ProductPrice))), String.valueOf(productMap.get(Constant.ProductImageURl)));
                                break;
                            }
                        }
                    }
                    getReadt();
                }
            }
        });



    }
    private void getReadt(){
        TextView t1 = (TextView) findViewById(R.id.StoreNameProductDetail);
        t1.setText(StoreName);

        TextView t2 = (TextView) findViewById(R.id.Description);
        t2.setText(String.valueOf(product.description));

        TextView t3 = (TextView) findViewById(R.id.Price);
        t3.setText("$" + product.price);
        t3.setContentDescription(String.valueOf(product.price));

        ImageView img1 = (ImageView) findViewById(R.id.product_image);
        new URLImageTask(img1).execute(product.imageURL);
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

    public void addCart(View view){
        DatabaseReference entireDB = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> cartProduct = new HashMap<>();
        cartProduct.put(Constant.ProductBrand, product.brand);
        cartProduct.put(Constant.ProductDescription, product.description);
        cartProduct.put(Constant.ProductImageURl, product.imageURL);
        cartProduct.put(Constant.ProductPrice, product.price);
        cartProduct.put(Constant.ProductName, product.name);
        TextView t1 = (TextView) findViewById(R.id.quantity);
        cartProduct.put(Constant.Quantity, Integer.valueOf(t1.getText().toString()));

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Constant.Customer).child(CustomerID).child(Constant.Cart).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    try {
                        ArrayList<Map> carts = (ArrayList<Map>) task.getResult().getValue();
                        Integer length = carts.size();
                        cartProduct.put("cartProductID", length);
                        entireDB.child(Constant.Customer).child(CustomerID).child(Constant.Cart).child(String.valueOf(length)).setValue(cartProduct);

                    }catch (NullPointerException e){
                        cartProduct.put("cartProductID", 0);
                        entireDB.child(Constant.Customer).child(CustomerID).child(Constant.Cart).child(String.valueOf(0)).setValue(cartProduct);
                    }
                    Intent intent = new Intent(getApplicationContext(), CustomerCheckout.class);
                    //Changed Strings to Constants from Java file, Ankit
                    intent.putExtra(Constant.OwnerID, StoreID);
                    intent.putExtra(Constant.CustomerID, CustomerID);
                    startActivity(intent);
                }
            }
        });
    }


    public void onClickCart(View view){
        Intent intent = new Intent(getApplicationContext(), CustomerCheckout.class);
        intent.putExtra(Constant.OwnerID, StoreID);
        intent.putExtra(Constant.CustomerID, CustomerID);
        startActivity(intent);
    }
}