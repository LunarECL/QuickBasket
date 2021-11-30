package com.example.quickbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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

public class CustomerCheckout extends Activity {
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Double> mPrices = new ArrayList<>();
    private ArrayList<Integer> mQtys = new ArrayList<>();


    //TextView totalCost = findViewById(R.id.totalCost);
   // double grandTotal = CustomerCheckoutRecyclerViewAdapter.getSubTotal();

    private ArrayList<Order> orders = new ArrayList<>();
    private int counter = 0;
    private String customerID;
    private String ownerID;
    private String qty;

    ArrayList<Integer> cartProductIDs = new ArrayList<Integer>();

    DatabaseReference entireDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_customer);

        initImageBitmaps();
        //totalCost.setText(Double.toString(grandTotal));

        //Get Customer ID from previous page
        Intent intent = getIntent();
        customerID = intent.getStringExtra(Constant.CustomerID);
        customerID = "0";
        ownerID = intent.getStringExtra(Constant.OwnerID);
        ownerID = "1";

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButtonCheckoutCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainScreenCustomer.class);
                startActivity(activity2Intent);
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        //Get all the Product IDs

        mDatabase.child(Constant.Customer).child(customerID).child(Constant.Cart).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("SO the Product IDs are", String.valueOf(task.getResult().getValue()));
                    ArrayList<String> storeList = (ArrayList<String>) task.getResult().getValue();
                   // Log.d("AND", String.valueOf(storeList.get(0)));
                    /*for (Map<String, Object> entry : storeList){
                        if (entry != null) {
                            cartProductIDs = (ArrayList<Integer>) entry.get(Constant.Cart);
                        }
                    }*/
                }
            }
        });



        /*mDatabase.child(Constant.Customer).child(customerID).child(Constant.Cart).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    ArrayList cartIDMap = (ArrayList) task.getResult().getValue();
                    ArrayList<Map> cartList = (ArrayList<Map>) cartIDMap;

                    for (Map<String, Object> entry : cartList){
                        if (entry != null) {
                            String tempOwnerID = String.valueOf(entry.get(Constant.OwnerID));
                            String tempQty = String.valueOf(entry.get(Constant.Quantity));

                            //Product product = (Product) entry.get("Product");

                            Order order = new Order(counter, Integer.parseInt(tempOwnerID), Integer.parseInt(customerID), cartProductIDs, false);

                            if (order.productIDsList != null) {
                                orders.add(order);
                                writeOrder(order);
                            }
                        }
                    }

                    initImageBitmaps();
                }
            }
        });

        */
    }

    public void writeOrder(Order newOrder) {
        Order order = newOrder;
        entireDB.child(Constant.Order).child(Integer.toString(order.orderID)).setValue(order);
    }

    /*public void writeNewOrder(int orderID, int ownerID, int customerID, ArrayList<Integer> productIDsList, boolean status) {
        Order order = new Order(orderID,ownerID,customerID,productIDsList, status);

        entireDB.child(Constant.Order).child(Integer.toString(orderID)).setValue(order);
    }*/


    private void initImageBitmaps() {
       /* Log.d("CHECKPOINT", "1");

        for (int id: cartProductIDs){
            Log.d("YEHH", String.valueOf(id));
        }

        for (Order order: orders){
            for(int id: cartProductIDs){
                //GET QUANTITY OF THIS PRODUCT
                Log.d("CHECKPOINT", "2");
                entireDB.child(Constant.Customer).child(customerID).child(Constant.Cart).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            ArrayList cartIDMap = (ArrayList) task.getResult().getValue();
                            ArrayList<Map> cartList = (ArrayList<Map>) cartIDMap;

                            for (Map<String, Object> entry : cartList){
                                if (String.valueOf(entry.get(Constant.ProductID)).equals(String.valueOf(id))) {
                                    Log.d("The Quantity is", String.valueOf(entry.get(Constant.Quantity)));
                                    qty = String.valueOf(entry.get(Constant.Quantity));
                                }
                            }
                        }
                    }
                });

                entireDB.child(Constant.StoreOwner).child(String.valueOf(order.ownerID)).child(Constant.StoreListProducts).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            ArrayList productIDMap = (ArrayList) task.getResult().getValue();
                            ArrayList<Map> cartList = (ArrayList<Map>) productIDMap;

                            for (Map<String, Object> entry : cartList){
                                if (String.valueOf(entry.get("id")).equals(String.valueOf(id))) {
                                    String imageURL = String.valueOf(entry.get(Constant.ProductImageURl));
                                    String productName = String.valueOf(entry.get(Constant.ProductName));
                                    String productPrice = String.valueOf(entry.get(Constant.ProductPrice));
                                    mImageUrls.add(imageURL);
                                    mProductNames.add(productName);
                                    mPrices.add(Double.parseDouble(productPrice));
                                    mQtys.add(Integer.parseInt(qty));
                                }
                            }
                        }
                    }
                });
            }
        }*/


        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/Honeycrisp-Apple.jpg/2269px-Honeycrisp-Apple.jpg");
        mProductNames.add("Apple");
        mPrices.add(19.99);
        mQtys.add(2);

        mImageUrls.add("https://media.istockphoto.com/photos/orange-picture-id185284489?k=20&m=185284489&s=612x612&w=0&h=LLY2os0YTG2uAzpBKpQZOAC4DGiXBt1jJrltErTJTKI=");
        mProductNames.add("Oranges");
        mPrices.add(17.99);
        mQtys.add(3);

        mImageUrls.add("https://images.immediate.co.uk/production/volatile/sites/30/2017/01/Bananas-218094b-scaled.jpg?quality=90&resize=960%2C872");
        mProductNames.add("Bananas");
        mPrices.add(16.99);
        mQtys.add(1);

        mImageUrls.add("https://solidstarts.com/wp-content/uploads/Kiwi_edited-scaled.jpg");
        mProductNames.add("Kiwi");
        mPrices.add(17.99);
        mQtys.add(4);

        mImageUrls.add("https://5.imimg.com/data5/UH/AR/MY-41645336/fresh-strawberry-500x500.jpg");
        mProductNames.add("Strawberry");
        mPrices.add(18.99);
        mQtys.add(5);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.product_list_recycle_view);
        CustomerCheckoutRecyclerViewAdapter adapter = new CustomerCheckoutRecyclerViewAdapter(this, mProductNames, mImageUrls, mPrices, mQtys);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
