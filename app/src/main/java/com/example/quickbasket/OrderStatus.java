package com.example.quickbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class OrderStatus extends Activity implements View.OnClickListener{
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Double> mPrices = new ArrayList<>();
    private ArrayList<Integer> mQtys = new ArrayList<>();

    private int totalItems;
    private int counter = 0;
    private String customerID;
    private String ownerID;
    private double grandTotal;

    ArrayList<Product> cartProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Get Customer ID and OwnerID from previous page
        Intent intent = getIntent();
        customerID = intent.getStringExtra(Constant.CustomerID);
        ownerID = intent.getStringExtra(Constant.OwnerID);

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButtonStatus);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainScreenCustomer.class);
                activity2Intent.putExtra(Constant.CustomerID, customerID);
                startActivity(activity2Intent);
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        //Get all the cartProducts

        if (customerID != null)
        {
            mDatabase.child(Constant.Customer).child(customerID).child(Constant.Cart).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Log.d("SO the Product IDs are", String.valueOf(task.getResult().getValue()));

                        if (task.getResult().getValue() instanceof ArrayList) {
                            ArrayList cartProductIDMap = (ArrayList) task.getResult().getValue();
                            if (cartProductIDMap != null) {
                                ArrayList<Map> cartProductList = (ArrayList<Map>) cartProductIDMap;

                                for (Map<String, Object> entry : cartProductList) {
                                    if (entry != null) {
                                        String cartProductID = String.valueOf(entry.get(Constant.CartProductID));
                                        String productName = String.valueOf(entry.get(Constant.ProductName));
                                        String price = String.valueOf(entry.get(Constant.ProductPrice));
                                        String imageURL = String.valueOf(entry.get(Constant.ProductImageURl));
                                        String quantity = String.valueOf(entry.get(Constant.Quantity));

                                        Product newProduct = new Product(Integer.parseInt(cartProductID), productName, Double.parseDouble(price), imageURL, Integer.parseInt(quantity));
                                        cartProducts.add(newProduct);
                                    }
                                }
                            }
                            else{

                            }
                        }
                        else {
                            Map cartProductIDMap = (Map) task.getResult().getValue();
                            if (cartProductIDMap != null){
                                ArrayList<Map> cartProductList = new ArrayList<Map>(cartProductIDMap.values());

                                for (Map<String, Object> entry : cartProductList) {
                                    if (entry != null) {
                                        String cartProductID = String.valueOf(entry.get(Constant.CartProductID));
                                        String productName = String.valueOf(entry.get(Constant.ProductName));
                                        String price = String.valueOf(entry.get(Constant.ProductPrice));
                                        String imageURL = String.valueOf(entry.get(Constant.ProductImageURl));
                                        String quantity = String.valueOf(entry.get(Constant.Quantity));

                                        Product newProduct = new Product(Integer.parseInt(cartProductID), productName, Double.parseDouble(price), imageURL, Integer.parseInt(quantity));
                                        cartProducts.add(newProduct);
                                    }
                                }
                            }

                            else{

                            }
                        }
                        initImageBitmaps();
                    }
                }
            });


        }
    }

    private void initImageBitmaps() {
        for (Product product: cartProducts){
            mImageUrls.add(product.imageURL);
            mProductNames.add(product.name);
            mPrices.add(product.price);
            mQtys.add(product.qty);
            totalItems += product.qty;
            double qtyDouble = product.qty;
            double price = product.price;
            double total = qtyDouble * price;
            Log.d("The total is:", String.valueOf(total));
            grandTotal = grandTotal + total;
        }

        TextView tv1 = (TextView) findViewById(R.id.totalCostStatus);
        tv1.setText("Subtotal (" + totalItems + " items): $" + grandTotal);

        TextView tv2 = (TextView) findViewById(R.id.orderStatus);

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Order");

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Log.d("STATUS IS ", String.valueOf(datasnapshot.getValue()));
                Boolean status = false;

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (String.valueOf(order.customerID).equals(customerID)){
                        status = order.status;
                    }
                }

                if (status){
                    tv2.setText("Order Status: Ready");
                }

                else{
                    tv2.setText("Order Status: In-progress");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.order_product_list_recycle_view);
        OrderStatusAdapter adapter = new OrderStatusAdapter(this, mProductNames, mImageUrls, mPrices, mQtys);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {

    }
}
