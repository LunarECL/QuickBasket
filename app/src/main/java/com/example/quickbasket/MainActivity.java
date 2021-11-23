package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //CODE FOR TEST MAIN CUSTOMER BUTTON
        Button testButtonMainCustomer = findViewById(R.id.testMainCustomer);
        testButtonMainCustomer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), main_screen_customer.class);
                startActivity(activity2Intent);
            }
        });
    }



//sample Firebase Test code. It will be moved to customer register Activity soon.
    /*public void sendData(View view) {
        EditText idEditText = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText passwordEditText = (EditText) findViewById(R.id.editTextTextPassword);

        String name = idEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        DatabaseReference ref2 =  FirebaseDatabase.getInstance().getReference("Customer");
        ref2.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.i("demo", "Error getting data");
                }
                else {
                    try {
                        Log.i("demo", task.getResult().getValue().toString());
                    } catch (NullPointerException e){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Customer customer = new Customer(name, password);
                        ref.child("Customer").child(customer.getName()).setValue(customer);
                    }
                }
            }
        });
    }*/
}