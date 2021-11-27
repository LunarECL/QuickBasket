package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupCustomerPage extends AppCompatActivity implements Contract.View{

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer_page);

        ImageButton testBackButtonCustomerSignup = findViewById(R.id.backButton_CustomerSignup);
        testBackButtonCustomerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                //if (!usernameExists && !customeridExists && !passwordExists??)
                counter++;
            }
        });

    }

    public String getUsername(){
        EditText editUsername = (EditText) findViewById(R.id.enterUsername);
        return editUsername.getText().toString();
    }

    public String getPassword(){
        EditText editPassword = (EditText) findViewById(R.id.enterPassword);
        return editPassword.getText().toString();
    }

    public void addCustomer(View view){
        DatabaseReference signup = FirebaseDatabase.getInstance().getReference("Customers");
        //Customer customer = new Customer(counter, getUsername(), )
    }

}