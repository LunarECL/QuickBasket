package com.example.quickbasket;

import static com.example.quickbasket.Constant.userCount;
import static com.example.quickbasket.Constant.Customer;
import static com.example.quickbasket.Constant.CustomerID;
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

public class SignupCustomerPage extends AppCompatActivity{
    private int counter;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer_page);

        // Back button to main screen
        ImageButton testBackButtonCustomerSignup = findViewById(R.id.backButton_CustomerSignup);
        testBackButtonCustomerSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });
    }

    public void addCustomer(View view){
        // Get the customer count
        db.child(userCount).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    if (task.getResult().getValue() == null){
                        db.child(userCount).setValue(0);
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
        EditText editUsername = (EditText) findViewById(R.id.enterUsername);
        String username = editUsername.getText().toString();
        EditText editPassword = (EditText) findViewById(R.id.enterPassword);
        String password = editPassword.getText().toString();
        EditText editName = (EditText) findViewById(R.id.enterName);
        String name = editName.getText().toString();

        Boolean checkUsername = username.equals("");
        Boolean checkPassword = password.equals("");
        Boolean checkName = name.equals("");
        db.child(Customer).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<String> usernames = new ArrayList<String>();
                Integer checker = 0;

                for (DataSnapshot child: task.getResult().getChildren()){
                    checker++;
                    Customer cust = child.getValue(Customer.class);
                    usernames.add(cust.getUsername());
                }
                if (checker > 0){
                    if (usernames.contains(username)){
                        Toast.makeText(getApplicationContext(), "Username already exists. Please choose another username", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (checkUsername || checkPassword || checkName){
                            Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            counter += 1;
                            db.child(userCount).setValue(counter);
                            Customer customer = new Customer(counter, username, name, password);

                            db.child(Customer).child(String.valueOf(counter)).setValue(customer);
                            ready2();
                        }
                    }
                }
                else{
                    if (checkUsername || checkPassword || checkName){
                        Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        counter += 1;
                        db.child(userCount).setValue(counter);
                        Customer customer = new Customer(counter, username, name, password);
                        db.child(Customer).child(Integer.toString(counter)).setValue(customer);
                        ready2();
                    }
                }
            }
        });


    }

    private void ready2(){
        Intent intent = new Intent(this, MainScreenCustomer.class);
        intent.putExtra(CustomerID, Integer.toString(counter));
        startActivity(intent);
    }
}