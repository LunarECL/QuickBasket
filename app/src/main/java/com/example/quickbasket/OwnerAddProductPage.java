package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class OwnerAddProductPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_product);

        ImageButton backButton = findViewById(R.id.backButton_AddProductInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_screen_owner.class);
                startActivity(intent);
            }
        });
    }

    public void onAddProductClick(View view) {
        Intent intent = new Intent(this, main_screen_owner.class);
        startActivity(intent);
    }
}
