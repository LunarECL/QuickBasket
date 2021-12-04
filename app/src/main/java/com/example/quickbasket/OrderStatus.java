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

public class OrderStatus extends Activity implements View.OnClickListener, CustomerCheckoutRecyclerViewAdapter.OnBtnClick{
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
    ArrayList<Integer> cartProductsIDs = new ArrayList<>();

    DatabaseReference entireDB = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_customer);

        //Get Customer ID and OwnerID from previous page
        Intent intent = getIntent();
        customerID = intent.getStringExtra(Constant.CustomerID);
        // customerID = "1";
        ownerID = intent.getStringExtra(Constant.OwnerID);
        // ownerID = "1";

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButtonCheckoutCustomer);
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

            // CODE FOR Confirm Order BUTTON

            Button submitButton = findViewById(R.id.submitButton);
            submitButton.setEnabled(true);

            submitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    //get Order ID
                    entireDB.child(Constant.orderCount).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                if (!cartProductsIDs.isEmpty()){
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
                                    Toast.makeText(getApplicationContext(), "Order submitted. Thank you!", Toast.LENGTH_SHORT).show();
                                }

                                else{
                                    Toast.makeText(getApplicationContext(), "Please have atleast one item in cart", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
                }
            });
        }
    }

    public void writeOrder(Order newOrder) {
        Order order = newOrder;
        Log.d("The order ID is ", Integer.toString(order.orderID));
        entireDB.child(Constant.Order).child(String.valueOf(order.orderID)).setValue(order);
    }

    /*public void writeNewOrder(int orderID, int ownerID, int customerID, ArrayList<Integer> productIDsList, boolean status) {
        Order order = new Order(orderID,ownerID,customerID,productIDsList, status);

        entireDB.child(Constant.Order).child(Integer.toString(orderID)).setValue(order);
    }*/

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

        /*
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

        */

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
        Product productDeleted = cartProducts.get(position);
        String cartProductIDelete = String.valueOf(productDeleted.id);


        cartProducts.remove(position);
        mImageUrls.clear();
        mProductNames.clear();
        mPrices.clear();
        mQtys.clear();

        totalItems = 0;
        grandTotal = 0;
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

        initRecyclerView();

        TextView tv1 = (TextView) findViewById(R.id.totalCost);
        tv1.setText("Subtotal (" + totalItems + " items): $" + grandTotal);

        Log.d("The delete order ID is ", cartProductIDelete);
        entireDB.child(Constant.Customer).child(customerID).child(Constant.Cart).child(cartProductIDelete).setValue(null);
    }

    @Override
    public void onClick(View v) {

    }
}
