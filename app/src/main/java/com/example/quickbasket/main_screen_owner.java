package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class main_screen_owner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_owner);

        ImageButton backButton = findViewById(R.id.backButton_MainCustomer);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onViewProductsClick(View view) {
        Intent intent = new Intent(this, ViewProductsOwner.class);
        startActivity(intent);
    }

    public void onAddNewProductClick(View view) {
        Intent intent = new Intent(this, ViewOrderOwner.class);
        startActivity(intent);
    }
}