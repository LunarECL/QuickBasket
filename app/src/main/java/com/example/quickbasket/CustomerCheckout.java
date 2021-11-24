package com.example.quickbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerCheckout extends Activity {
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<Double> mPrices = new ArrayList<>();
    private ArrayList<Integer> mQtys = new ArrayList<>();

    //TextView totalCost = findViewById(R.id.totalCost);
   // double grandTotal = CustomerCheckoutRecyclerViewAdapter.getSubTotal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_customer);

        initImageBitmaps();
        //totalCost.setText(Double.toString(grandTotal));

        // CODE FOR BACK BUTTON
        ImageButton backButton = findViewById(R.id.backButtonCheckoutCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), main_screen_customer.class);
                startActivity(activity2Intent);
            }
        });
    }

    private void initImageBitmaps() {
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
