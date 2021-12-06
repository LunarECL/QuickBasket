package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewOrderOwner extends AppCompatActivity {

    private Button back_button;
    String StoreName;
    String StoreLocation;
    String StoreID;
    String CustomerID;
    ArrayList<Integer> productIDs = new ArrayList<>();
    private  TextView mStatus;
    private Button mConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_owner);

        ListView mListView = (ListView) findViewById(R.id.orderlist);


        //back_button = (Button) findViewById(R.id.back_button);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Integer orderID = extras.getInt("orderID");
        Integer ownerID = extras.getInt("ownerID");


        ImageButton backButton = findViewById(R.id.backButton_StoreDetail);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainScreenOwner.class);
                intent.putExtra("ownerID",ownerID);
                startActivity(intent);
            }
        });

        mStatus = (TextView) findViewById(R.id.OrderStatus);

        DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference().child("Order").child(orderID.toString()).child("status");

        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                //debug
                //String ma = datasnapshot.getValue().toString();
                //System.out.println("1  1 1"  + ma);

                Boolean status = datasnapshot.getValue(Boolean.class);

                if(!status){

                    mStatus.setText("Order status: Not completed");

                }
                else{

                    mStatus.setText("Order status: Complete");
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mConfirm = (Button) findViewById(R.id.ConfirmButton);

        DatabaseReference reference4 = FirebaseDatabase.getInstance().getReference().child("Order").child(orderID.toString());

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference4.child("status").setValue(true);

            }
        });





        //ArrayList<Integer> productIDs = new ArrayList<>();

        ArrayList<String> list = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_product, list);
        mListView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Order").child(orderID.toString()).child("customerID");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                //debug

                CustomerID = datasnapshot.getValue().toString();

                System.out.println(CustomerID);

                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Customer").child(CustomerID).child("cart");

                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                        //debug
                        //String ma = datasnapshot.getValue().toString();
                       //System.out.println("1  1 1"  + ma);

                        for(DataSnapshot snapshot : datasnapshot.getChildren()){


                            String brand = snapshot.child("brand").getValue(String.class);
                            String description = snapshot.child("description").getValue(String.class);
                            String imageURL = snapshot.child("imageURL").getValue(String.class);
                            Integer quantity = snapshot.child("quantity").getValue(Integer.class);
                            Double price = snapshot.child("price").getValue(Double.class);
                            String productName = snapshot.child("productName").getValue(String.class);

                            String information = "Product name: " + productName + " ● Quantity: " + quantity;

                            list.add(information);

                            //System.out.println("->" + brand + " " + price);

                        }

                        adapter.notifyDataSetChanged();

                        //getReadt(products);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });



    }











}