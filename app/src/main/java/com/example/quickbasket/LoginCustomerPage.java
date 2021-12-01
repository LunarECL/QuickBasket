package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginCustomerPage extends AppCompatActivity implements Contract.View{

    private Contract.Presenter presenter;
    private Integer id;
    public void displayMessage(String message){
        TextView textView = findViewById(R.id.textViewLoginCustomer);
        textView.setText(message);
    }

    public String getUsername(){
        EditText editText = findViewById(R.id.editTextLoginCustomer);
        return editText.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ImageButton testBackButtonCustomerLogin = findViewById(R.id.backButton_CustomerLogin);
        testBackButtonCustomerLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });

        presenter = new MyPresenter(new MyModel(), this);
    }

    public void handleClick(View view){
        presenter.checkUsername();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Customer");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    Customer customer = child.getValue(Customer.class);
                    if (customer.getUsername().equals(getUsername())) {
                        id = customer.getId();
                        ready();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);

    }

    private void ready(){
        Intent intent = new Intent(this, MainScreenCustomer.class);
        intent.putExtra(Constant.CustomerID, id.toString());
        startActivity(intent);
    }

}