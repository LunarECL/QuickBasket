package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProductDetailCustomerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_customer_page);

        Intent intent = getIntent();
        String sname = intent.getStringExtra("ID");


        TextView t1 = (TextView) findViewById(R.id.StoreName);
        t1.setText(sname);

        TextView t2 = (TextView) findViewById(R.id.Description);
        t2.setText("This is Description Sample just writing anything ~~~~~");

        TextView t3 = (TextView) findViewById(R.id.Price);
        t3.setText("$ 19.04");
    }
    //test
}