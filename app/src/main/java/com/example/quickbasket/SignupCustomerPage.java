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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupCustomerPage extends AppCompatActivity{
    private int counter;
    public void addCustomer(View view){

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        EditText editUsername = (EditText) findViewById(R.id.enterUsername);
        String username = editUsername.getText().toString();
        EditText editPassword = (EditText) findViewById(R.id.enterPassword);
        String password = editPassword.getText().toString();
        EditText editName = (EditText) findViewById(R.id.enterName);
        String name = editName.getText().toString();
        db.child("userCount").setValue("0");

        db.child("userCount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    counter = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        db.child("Customer").child("customerID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.exists()){
                        System.out.println("Username already exists. Please choose another username.");
                    }
                    else{
                        Customer customer = new Customer(counter + 1, username, name, password);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer_page);

        ImageButton testBackButtonCustomerSignup = findViewById(R.id.backButton_CustomerSignup);
        testBackButtonCustomerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });

        Button testButtonCustomerSignup = findViewById(R.id.signup_button);
        testButtonCustomerSignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent activity2Intent = new Intent(getApplicationContext(), main_screen_customer.class);
                startActivity(activity2Intent);


            }
        });

    }

}