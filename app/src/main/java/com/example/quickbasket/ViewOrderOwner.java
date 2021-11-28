package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewOrderOwner extends AppCompatActivity {

    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_owner);

        ListView mListView = (ListView) findViewById(R.id.orderlist);

        back_button = (Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOwnerMainPage();
            }
        });


        //Read from Database
        /*
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot child:snapshot.getChildren()){



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };*/

        //Demo Run with hardcoded items not relevant in future

        /*
        Product product1 = new Product("iphone","phone","apple",200,"url");
        Product product2 = new Product("surface pro","tablet","microsoft",600,"url");

        ArrayList<Product> productList = new ArrayList<>();

        productList.add(product1);
        productList.add(product2);

        ProductListAdapter adapter = new ProductListAdapter(this, R.layout.adapter_view_layout, productList);

        mListView.setAdapter(adapter);

         */



    }

    public void openOwnerMainPage(){
        Intent intent  = new Intent(this, main_screen_owner.class);
        startActivity(intent);
    }




}