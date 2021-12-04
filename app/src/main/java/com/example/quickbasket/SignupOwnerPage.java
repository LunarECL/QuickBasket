package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignupOwnerPage extends AppCompatActivity {

    private Integer counter;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_owner_page);

        ImageButton testBackButtonOwnerSignup = findViewById(R.id.backButton_OwnerSignup);
        testBackButtonOwnerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });
    }
    public void addStoreOwner(View view){

        // Get the customer count
        ref.child(Constant.userCount).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().getValue() == null){
                        ref.child(Constant.userCount).setValue(0);
                        counter = 0;
                    }
                    else{
                        counter = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    }
                }
                after();
            }
        });


    }

    public void after(){
        EditText editOwnerUsername = (EditText) findViewById(R.id.enterOwnerUsername);
        String username = editOwnerUsername.getText().toString();
        EditText editOwnerPassword = (EditText) findViewById(R.id.enterOwnerPassword);
        String password = editOwnerPassword.getText().toString();
        EditText editStoreName = (EditText) findViewById(R.id.enterStoreName);
        String storeName = editStoreName.getText().toString();
        EditText editLocation = (EditText) findViewById(R.id.enterLocation);
        String location = editLocation.getText().toString();
        EditText editLogo = (EditText) findViewById(R.id.enterLogo);
        String logo = editLogo.getText().toString();

        Boolean checkUsername = username.equals("");
        Boolean checkPassword = password.equals("");
        Boolean checkStoreName = storeName.equals("");
        Boolean checkLocation = location.equals("");
        Boolean checkLogo = logo.equals("");
        ref.child(Constant.StoreOwner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> usernames = new ArrayList<String>();
                ArrayList<String> productIDs = new ArrayList<String>();
                Integer checker = 0;

                for (DataSnapshot child: snapshot.getChildren()){
                    checker++;
                    Log.d("store owner", String.valueOf(child.getValue(StoreOwner.class)));
                    StoreOwner owner = child.getValue(StoreOwner.class);
                    usernames.add(owner.getUsername());
                }
                if (checker > 0){
                    if (usernames.contains(username)){
                        Toast.makeText(getApplicationContext(), "Username already exists. Please choose another username", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (checkUsername || checkPassword || checkStoreName || checkLocation || checkLogo){
                            Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            counter += 1;
                            ref.child(Constant.userCount).setValue(counter);
                            StoreOwner storeowner = new StoreOwner(counter, username, password, storeName, location, logo, productIDs);
                            ref.child(Constant.StoreOwner).child(String.valueOf(counter)).setValue(storeowner);
                            ready2();
                        }
                    }
                }
                else{
                    if (checkUsername || checkPassword || checkStoreName || checkLocation || checkLogo){
                        Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        counter += 1;
                        ref.child(Constant.userCount).setValue(counter);
                        StoreOwner storeowner = new StoreOwner(counter, username, password, storeName, location, logo, productIDs);
                        ref.child(Constant.StoreOwner).child(String.valueOf(counter)).setValue(storeowner);
                        ready2();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void ready2(){
        Intent intent = new Intent(this, MainScreenOwner.class);
        intent.putExtra(Constant.OwnerID, counter);
        startActivity(intent);
    }

}