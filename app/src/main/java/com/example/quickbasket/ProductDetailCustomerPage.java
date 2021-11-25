package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProductDetailCustomerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_customer_page);

        ImageButton backButton = findViewById(R.id.backButton_ProductDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreDetailCustomerPage.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String sname = intent.getStringExtra("ID");


        TextView t1 = (TextView) findViewById(R.id.StoreName);
        t1.setText(sname);

        TextView t2 = (TextView) findViewById(R.id.Description);
        t2.setText("This is Description Sample just writing anything ~~~~~");

        TextView t3 = (TextView) findViewById(R.id.Price);
        t3.setText("$ 19.04");
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
}