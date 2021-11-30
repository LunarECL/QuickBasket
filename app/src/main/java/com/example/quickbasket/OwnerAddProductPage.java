package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OwnerAddProductPage extends AppCompatActivity {
    Integer ownerID;
    Integer productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_product);

        // Get owner id from previous activity
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

        // Change the product image view when image url edit text is edited
        EditText imageURLEditText = (EditText) findViewById(R.id.editTextTextPersonName10);
        imageURLEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                ImageView product_image = (ImageView) findViewById(R.id.imageView2);
                new URLImageTask(product_image).execute(imageURLEditText.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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

        // Get product id
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("StoreOwner");
        ref1.child(String.valueOf(ownerID)).child("ProductID").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                }
                else {
                    Log.i("demo", task.getResult().getValue().toString());
                    productID = (Integer) task.getResult().getValue();
                }
            }
        });

        // Use product id to add product to database and increment product id afterwards
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
        Product product = new Product(productID, name, description, brand, price, imageURL);
        ref2.child("StoreOwner").child(String.valueOf(ownerID)).child("Products").child(String.valueOf(productID)).setValue(product);
        ref2.child("StoreOwner").child(String.valueOf(ownerID)).child("ProductID").setValue(String.valueOf(productID + 1));

        // Go to next screen
        Intent intent = new Intent(this, main_screen_owner.class);
        intent.putExtra("ownerID", ownerID);
        startActivity(intent);
    }
}
