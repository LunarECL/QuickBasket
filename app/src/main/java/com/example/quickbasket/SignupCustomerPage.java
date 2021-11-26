package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignupCustomerPage extends AppCompatActivity {

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
                //if (bunch of stuff)
                counter++;
            }
        });
    }

    public int getCounter(){
        return counter;
    }

}