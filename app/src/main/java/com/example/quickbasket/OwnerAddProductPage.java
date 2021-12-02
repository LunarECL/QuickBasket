package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerAddProductPage extends AppCompatActivity {
    Integer ownerID;
    String name;
    String brand;
    String price_string;
    Double price;
    String description;
    String imageURL;
    Integer productID;
    Context context;

    // Checks if a string price is a valid price
    public boolean isValidPrice(String price) {
        if (price.matches("[\\d]+\\.\\d\\d"))
            return true;
        return false;
    }

    // Shows toast on the screen
    public void setupToast(String string) {
        Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_VERTICAL, 0, 32);
        toast.show();
    }

    // Get owner id from previous activity
    public void getOwnerIDFromIntent() {
        Intent intent = getIntent();
        ownerID = new Integer(intent.getIntExtra("ownerID", 0));
    }

    // Back button code
    public void backButtonCode() {
        ImageButton backButton = findViewById(R.id.backButton_AddProductInfo);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_screen_owner.class);
                intent.putExtra("ownerID", ownerID.intValue());
                startActivity(intent);
            }
        });
    }

    // Change the product image view when image url edit text is edited
    public void changeProductImage() {
        EditText imageURLEditText = (EditText) findViewById(R.id.editTextTextPersonName10);
        ImageView productImageView = (ImageView) findViewById(R.id.imageView2);
        context = imageURLEditText.getContext();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_add_product);
        getOwnerIDFromIntent();
        backButtonCode();
        changeProductImage();
    }

    // Add product button code
    public void onAddProductClick(View view) {
        getEditTextValues();
    }

    // Get values from edit texts
    public void getEditTextValues() {
        EditText nameEditText = (EditText) findViewById(R.id.editTextTextPersonName3);
        EditText brandEditText = (EditText) findViewById(R.id.editTextTextPersonName6);
        EditText priceEditText = (EditText) findViewById(R.id.editTextTextPersonName7);
        EditText descriptionEditText = (EditText) findViewById(R.id.editTextTextMultiLine2);
        EditText imageURLEditText = (EditText) findViewById(R.id.editTextTextPersonName10);
        name = nameEditText.getText().toString();
        brand = brandEditText.getText().toString();
        price_string = priceEditText.getText().toString();
        description = descriptionEditText.getText().toString();
        imageURL = imageURLEditText.getText().toString();
        inputValidityCheck();
    }

    // User input validity check
    public void inputValidityCheck() {
        if (imageURL.equals("")) {
            setupToast("url cannot be empty");
        } else if (!URLUtil.isValidUrl(imageURL)) {
            setupToast("url cannot be valid");
        } else if (name.equals("")) {
            setupToast("name cannot be empty");
        } else if(brand.equals("")) {
            setupToast("brand cannot be empty");
        } else if(price_string.equals("")) {
            setupToast("price cannot be empty");
        } else if(!isValidPrice(price_string)) {
            setupToast("price is invalid");
        }else if(description.equals("")) {
            setupToast("description cannot be empty");
        } else {
            price = Double.parseDouble(price_string);
            getProductID();
        }
    }

    //Get product id
    public void getProductID() {
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference(Constant.ProductIDCount);
        ref1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo", "Error getting data", task.getException());
                } else {
                    productID = new Integer((int) Math.toIntExact((Long) task.getResult().getValue()));
                    addProductToDatabase();
                }
            }
        });
    }

    // Use product id to add product to database and increment product id afterwards
    public void addProductToDatabase() {
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
        Product product = new Product(productID.intValue(), name, description, brand, price, imageURL);
        ref2.child(Constant.StoreOwner).child(String.valueOf(ownerID.intValue())).child(Constant.StoreListProducts).child(String.valueOf(productID.intValue())).setValue(product);
        ref2.child(Constant.ProductIDCount).setValue(productID.intValue() + 1);
        toNextScreen();
    }

    // Go to next screen
    public void toNextScreen() {
        Intent intent = new Intent(this, main_screen_owner.class);
        intent.putExtra("ownerID", ownerID.intValue());
        startActivity(intent);
    }
}
