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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupCustomerPage extends AppCompatActivity{


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
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        //Customer customer = new Customer(db.child("userCount").getValue(), username, name, password);
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