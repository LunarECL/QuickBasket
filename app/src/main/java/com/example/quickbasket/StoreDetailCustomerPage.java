package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StoreDetailCustomerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail_customer_page);


        String StoreID = "Test Store";


        //Get Store Info from DB with ID
        String StoreName = "Test Store";

        TextView t1 = (TextView) findViewById(R.id.StoreName);
        t1.setText(StoreID);

        for(int i=0; i<10;i++){
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setClickable(true);
            String ProductID = "test";


            ll.setContentDescription(ProductID);

            layoutParams.setMargins(20, 0, 20, 0);

            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams imgl = new LinearLayout.LayoutParams(600, 600);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            // Commented out this line because it was giving an error
            // new URLImageTask(img).execute("https://media.istockphoto.com/photos/university-of-toronto-picture-id519685267?b=1&k=20&m=519685267&s=170667a&w=0&h=R45ZMm2Bf62gStoi01J6gQYDdZRBmuP9Oj5cWQpYAE4=");

            TextView tv1 = new TextView(this);
            tv1.setText("Name");
            LinearLayout.LayoutParams tv11 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tv1.setGravity(Gravity.CENTER);

            TextView tv2 = new TextView(this);
            tv2.setText("$0.00");
            tv2.setGravity(Gravity.CENTER);

            ll.addView(img, imgl);
            ll.addView(tv1, tv11);
            ll.addView(tv2, tv11);
            LinearLayout l0 = (LinearLayout) findViewById(R.id.Store_Products);
            l0.addView(ll, layoutParams);
        }
    }

    public void getProductInfo(View view){
        Intent intent = new Intent(this, ProductDetailCustomerPage.class);
        String productID = view.getContentDescription().toString();

    }
}