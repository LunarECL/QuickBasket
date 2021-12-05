package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CODE FOR CUSTOMER LOGIN BUTTON
        Button testButtonCustomerLogin = findViewById(R.id.customerLogin);
        testButtonCustomerLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), LoginCustomerPage.class);
                startActivity(activity2Intent);
            }
        });

        // CODE FOR OWNER LOGIN BUTTON
        Button testButtonOwnerLogin = findViewById(R.id.ownerLogin);
        testButtonOwnerLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), LoginStoreOwnerPage.class);
                startActivity(activity2Intent);
            }
        });

        // CODE FOR CUSTOMER SIGNUP BUTTON
        Button testButtonCustomerSignup = findViewById(R.id.customerSignup);
        testButtonCustomerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), SignupCustomerPage.class);
                startActivity(activity2Intent);
            }
        });

        // CODE FOR OWNER SIGNUP BUTTON
        Button testButtonOwnerSignup = findViewById(R.id.ownerSignup);
        testButtonOwnerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), SignupOwnerPage.class);
                startActivity(activity2Intent);
            }
        });

    }


}