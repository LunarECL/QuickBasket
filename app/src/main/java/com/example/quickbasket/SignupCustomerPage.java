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

import java.util.HashMap;
import java.util.Map;

public class SignupCustomerPage extends AppCompatActivity{
    private Integer counter = 0;

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
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        EditText editUsername = (EditText) findViewById(R.id.enterUsername);
        String username = editUsername.getText().toString();
        EditText editPassword = (EditText) findViewById(R.id.enterPassword);
        String password = editPassword.getText().toString();
        EditText editName = (EditText) findViewById(R.id.enterName);
        String name = editName.getText().toString();

        // Get the customer count
        /*db.child("userCount").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().getValue() == null){
                        db.child("userCount").setValue(counter);
                    }
                    else{
                        counter = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    }
                    Log.d("check outside", String.valueOf(counter));
                }
            }
        });*/

        db.child("Customer").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("demo", "data changed");

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String dbUsername = String.valueOf(dataSnapshot.child("username").getValue());
                    Log.d("Checking username", username);

                    if (dbUsername.equals(username)){
                        Toast.makeText(getApplicationContext(), "Username already exists. Please choose another username", Toast.LENGTH_SHORT).show();
                        Log.d("Checking if statement", "check");
                    }
                    else {
                        // increment counter
                        counter += 1;
                        // update the userCount
                        //db.child("userCount").setValue(counter);
                        // create the customer object
                        Customer customer = new Customer(counter, username, name, password);
                        // set the customerID to customer object
                        Log.d("Counter is this", String.valueOf(counter));
                        db.child("Customer").child(String.valueOf(counter)).setValue(customer);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent(this, MainScreenCustomer.class);
        intent.putExtra("customerID", counter);
        startActivity(intent);

    }
}