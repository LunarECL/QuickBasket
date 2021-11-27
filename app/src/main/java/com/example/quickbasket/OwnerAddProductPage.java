package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class OwnerAddProductPage extends AppCompatActivity {
    Integer storeID;

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

        // get storeID
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference("");
        ref.child("").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    // Get store name
                    Log.i("demo", task.getResult().getValue().toString());
                    storeID = (Integer) task.getResult().getValue();
                }
            }
        });*/

        // Get values from text views
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

        // Add product to DB
        /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Product product = new Product(storeID, name, description, brand, price, imageURL);
        ref.child("").child("").setValue(product);*/

        // Go to next screen
        Intent intent = new Intent(this, main_screen_owner.class);
        startActivity(intent);
    }
}
