package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class SignupCustomerPage extends AppCompatActivity{
    private Integer counter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer_page);

        // Back button to main screen
        ImageButton testBackButtonCustomerSignup = findViewById(R.id.backButton_CustomerSignup);
        testBackButtonCustomerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });
    }

    public void addCustomer(View view){

        // Get the customer count
        db.child("userCount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().getValue() == null){
                        Log.d("check null", "null");
                        db.child("userCount").setValue(0);
                        counter = 0;
                    }
                    else{
                        counter = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    }
                }
                after();
            }
        });


    }

    public void after(){
        EditText editUsername = (EditText) findViewById(R.id.enterUsername);
        String username = editUsername.getText().toString();
        EditText editPassword = (EditText) findViewById(R.id.enterPassword);
        String password = editPassword.getText().toString();
        EditText editName = (EditText) findViewById(R.id.enterName);
        String name = editName.getText().toString();
        db.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> usernames = new ArrayList<String>();
                Integer checker = 0;

                for (DataSnapshot child: snapshot.getChildren()){
                    checker++;
                    Customer cust = child.getValue(Customer.class);
                    usernames.add(cust.getUsername());
                }
                if (usernames.contains(username)){
                    Toast.makeText(getApplicationContext(), "Username already exists. Please choose another username", Toast.LENGTH_SHORT).show();
                }
                else {
                    // increment counter
                    counter += 1;
                    // update the userCount
                    db.child("userCount").setValue(counter);
                    // create the customer object
                    Customer customer = new Customer(counter, username, name, password);
                    db.child("Customer").child(String.valueOf(counter)).setValue(customer);
                }

                if (checker < 1){
                    Customer customer = new Customer(counter, username, name, password);
                    db.child("Customer").child(String.valueOf(counter)).setValue(customer);
                }
                ready2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void ready2(){
        Intent intent = new Intent(this, MainScreenCustomer.class);
        intent.putExtra("customerID", counter);
        startActivity(intent);
    }
}