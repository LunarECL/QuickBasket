package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerAddProductPage extends AppCompatActivity {
    Integer ownerID;
    Integer productIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_product);

        // Get ownerID from previous activity
        Intent intent = getIntent();
        ownerID = intent.getIntExtra("ownerID", 0);

        // Back button code
        ImageButton backButton = findViewById(R.id.backButton_AddProductInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_screen_owner.class);
                startActivity(intent);
            }
        });
    }

    // Add product button code
    public void onAddProductClick(View view) {
        // Get values from edit texts
        EditText nameEditText = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText brandEditText = (EditText) findViewById(R.id.editTextTextPersonName6);
        EditText priceEditText = (EditText) findViewById(R.id.editTextTextPersonName7);
        EditText descriptionEditText = (EditText) findViewById(R.id.editTextTextMultiLine2);
        EditText imageURLEditText = (EditText) findViewById(R.id.editTextTextPersonName10);
        String name = nameEditText.getText().toString();
        String brand = brandEditText.getText().toString();
        String price_string = priceEditText.getText().toString();
        Double price = Double.parseDouble(price_string);
        String description = descriptionEditText.getText().toString();
        String imageURL = imageURLEditText.getText().toString();

        // Get product index
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("StoreOwner");
        ref1.child(String.valueOf(ownerID)).child("ProductIndex").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    Log.i("demo", task.getResult().getValue().toString());
                    productIndex = (Integer) task.getResult().getValue();
                }
            }
        });

        // Use product index to add product to DB and increment product index afterwards
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
        Product product = new Product(name, description, brand, price, imageURL); // <- this should be with product id too, Joshua Kim
        ref2.child("StoreOwner").child(String.valueOf(ownerID)).child("Products").child(String.valueOf(productIndex)).setValue(product);
        ref2.child("StoreOwner").child(String.valueOf(ownerID)).child("ProductIndex").setValue(String.valueOf(productIndex + 1));

        // Go to next screen
        Intent intent = new Intent(this, main_screen_owner.class);
        intent.putExtra("ownerID", ownerID);
        startActivity(intent);
    }
}
