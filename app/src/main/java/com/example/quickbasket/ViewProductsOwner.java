package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProductsOwner extends AppCompatActivity {

    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products_owner);

        ListView mListView = (ListView) findViewById(R.id.orderlist);

        back_button = (Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOwnerMainPage();
            }
        });

        int ownerID = 10; // get the id from the main screen owner

        ArrayList<Product> productList = new ArrayList<>();

        ProductListAdapter adapter = new ProductListAdapter(this, R.layout.adapter_view_layout, productList);
        mListView.setAdapter(adapter);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("StoreOwner");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Integer id = snapshot.child("id").getValue(Integer.class);

                    if(id == ownerID){

                        /*Integer productID = snapshot.child("Products/id").getValue(Integer.class);
                        String brand = snapshot.child("Products/brand").getValue(String.class);
                        String description = snapshot.child("Products/description").getValue(String.class);
                        String imgURL = snapshot.child("Products/imageURL").getValue(String.class);
                        String name = snapshot.child("Products/name").getValue(String.class);
                        Double price = snapshot.child("Products/price").getValue(Double.class);*/

                        for(DataSnapshot snapshot1 : dataSnapshot.child("Products").getChildren()){

                            Product product = snapshot1.getValue(Product.class);

                            productList.add(product);

                            adapter.notifyDataSetChanged();

                        }

                    }



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }

    public void openOwnerMainPage(){
        Intent intent  = new Intent(this, main_screen_owner.class);
        startActivity(intent);
    }



}