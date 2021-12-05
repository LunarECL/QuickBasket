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

public class CustomerCheckout extends Activity implements View.OnClickListener, CustomerCheckoutRecyclerViewAdapter.OnBtnClick{
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Double> mPrices = new ArrayList<>();
    private ArrayList<Integer> mQtys = new ArrayList<>();

    private int totalItems;
    private int counter = 0;
    private String customerID;
    private String ownerID;
    private double grandTotal;
    boolean existingOrder = false;

    ArrayList<Product> cartProducts = new ArrayList<>();
    ArrayList<Integer> cartProductsIDs = new ArrayList<>();

    DatabaseReference entireDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_customer);

        //Get Customer ID and OwnerID from previous page
        Intent intent = getIntent();
        customerID = intent.getStringExtra(Constant.CustomerID);
        Log.d("CUSTOMER ID IS", customerID);
        ownerID = intent.getStringExtra(Constant.OwnerID);
        Log.d("OWNER ID IS", ownerID);

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButtonCheckoutCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainScreenCustomer.class);
                activity2Intent.putExtra(Constant.CustomerID, customerID);
                activity2Intent.putExtra(Constant.OwnerID, ownerID);
                startActivity(activity2Intent);
            }
        });

        // CODE FOR Status BUTTON
        Button statusButton = findViewById(R.id.status_button);
        statusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), OrderStatus.class);
                activity2Intent.putExtra(Constant.CustomerID, customerID);
                startActivity(activity2Intent);
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        //Check if Order Exists

        checkOrderExists();


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

            // CODE FOR Confirm Order BUTTON

            for (Product product: cartProducts){
                Log.d("The product name is :", product.name);
            }

            Button submitButton = findViewById(R.id.submitButton);
            submitButton.setEnabled(true);

            submitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    checkOrderExists();
                    entireDB.child(Constant.orderCount).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                if (!cartProducts.isEmpty()){
                                    if (task.getResult().getValue() == null) {
                                        entireDB.child(Constant.orderCount).setValue(0);
                                        counter = 0;
                                    } else {
                                        counter = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                                        counter += 1;
                                        entireDB.child(Constant.orderCount).setValue(counter);
                                    }
                                    for (Product product : cartProducts) {
                                        cartProductsIDs.add(product.id);
                                    }

                                    Order newOrder = new Order(counter, Integer.parseInt(ownerID), Integer.parseInt(customerID), cartProductsIDs, false);
                                    writeOrder(newOrder);
                                }

                                else{
                                    Log.d("error" , "hha");
                                    Toast.makeText(getApplicationContext(), "Please have atleast one item in cart", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }
            });
        }
    }

    public void checkOrderExists(){
        entireDB.child(Constant.Order).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("SO the Orders are ", String.valueOf(task.getResult().getValue()));

                    if (task.getResult().getValue() instanceof ArrayList) {
                        ArrayList orderIDMap = (ArrayList) task.getResult().getValue();
                        if (orderIDMap != null) {
                            ArrayList<Map> orderList = (ArrayList<Map>) orderIDMap;

                            for (Map<String, Object> entry : orderList) {
                                if (entry != null) {
                                    Log.d("CustomerIDs ArrayList are ", String.valueOf(entry.get(Constant.CustomerID)));
                                    if (String.valueOf(entry.get(Constant.CustomerID)).equals(customerID)) {
                                        existingOrder = true;

                                    }
                                }
                            }
                        }
                        Log.d("EXISTING ORDER ARRAY ", String.valueOf(existingOrder));
                    }

                    else{
                        Map orderIDMap = (Map) task.getResult().getValue();
                        if (orderIDMap != null) {
                            ArrayList<Map> orderList = (ArrayList<Map>) orderIDMap;

                            for (Map<String, Object> entry : orderList) {
                                if (entry != null) {
                                    Log.d("CustomerIDs MAP are ", String.valueOf(entry.get(Constant.CustomerID)));
                                    if (String.valueOf(entry.get(Constant.CustomerID)).equals(customerID)) {
                                        existingOrder = true;
                                    }
                                }
                            }
                        }
                        Log.d("EXISTING ORDER MAP ", String.valueOf(existingOrder));
                    }
                }
            }
        });
    }


    public void writeOrder(Order newOrder) {
        Order order = newOrder;
        Log.d("The customer ID is ", customerID);

        if (existingOrder){
            Toast.makeText(getApplicationContext(), "Order limit reached", Toast.LENGTH_SHORT).show();
        }

        else{
            entireDB.child(Constant.Order).child(String.valueOf(order.orderID)).setValue(order);
            Toast.makeText(getApplicationContext(), "Order submitted. Thank you!", Toast.LENGTH_SHORT).show();
            Intent activity2Intent = new Intent(getApplicationContext(), MainScreenCustomer.class);
            activity2Intent.putExtra(Constant.CustomerID, customerID);
            activity2Intent.putExtra(Constant.OwnerID, ownerID);
            startActivity(activity2Intent);
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

        TextView tv1 = (TextView) findViewById(R.id.totalCost);
        tv1.setText("Subtotal (" + totalItems + " items): $" + grandTotal);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.product_list_recycle_view);
        CustomerCheckoutRecyclerViewAdapter adapter = new CustomerCheckoutRecyclerViewAdapter(this, mProductNames, mImageUrls, mPrices, mQtys, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDeleteBtnClick(int position) {
        if (!existingOrder) {
            Product productDeleted = cartProducts.get(position);
            String cartProductIDelete = String.valueOf(productDeleted.id);
            cartProducts.remove(position);
            mImageUrls.clear();
            mProductNames.clear();
            mPrices.clear();
            mQtys.clear();

            totalItems = 0;
            grandTotal = 0;
            for (Product product : cartProducts) {
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

            initRecyclerView();

            TextView tv1 = (TextView) findViewById(R.id.totalCost);
            tv1.setText("Subtotal (" + totalItems + " items): $" + grandTotal);

            Log.d("The delete order ID is ", cartProductIDelete);
            entireDB.child(Constant.Customer).child(customerID).child(Constant.Cart).child(cartProductIDelete).setValue(null);
            checkOrderExists();

        }

        else{
            Toast.makeText(getApplicationContext(), "Can't edit a submitted order", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}