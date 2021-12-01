package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OwnerAddProductPage extends AppCompatActivity {
    Integer ownerID;
    Integer productID;
    Context context = getApplicationContext();

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
        ImageView productImageView = (ImageView) findViewById(R.id.imageView2);
        imageURLEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String imageURL = imageURLEditText.getText().toString();
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .centerCrop();
                Glide.with(context).load(imageURL).apply(options).into(productImageView);
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
