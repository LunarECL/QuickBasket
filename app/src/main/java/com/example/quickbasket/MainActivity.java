package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate:");
        ListView mListView = (ListView) findViewById((R.id.listView));

        //Create Person Objects
        StoreInformation john = new StoreInformation("John", "12-20-1998", "Male");
        StoreInformation steve = new StoreInformation("Steve", "08-03-1987", "Male");
        StoreInformation stacy = new StoreInformation("Stacy", "11-15-2000", "Female");

        ArrayList<StoreInformation> peopleList = new ArrayList<>();
        peopleList.add(john);
        peopleList.add(steve);
        peopleList.add(stacy);

        StoreInfoAdapter adapter = new StoreInfoAdapter(this, R.layout.customer_store_info_adapter_view_layout, peopleList);
        mListView.setAdapter(adapter);
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