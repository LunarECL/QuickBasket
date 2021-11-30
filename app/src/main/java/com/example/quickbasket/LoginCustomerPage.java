package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginCustomerPage extends AppCompatActivity implements Contract.View{

    private Contract.Presenter presenter;

    public void displayMessage(String message){
        TextView textView = findViewById(R.id.textViewLoginCustomer);
        textView.setText(message);
    }

    public String getUsername(){
        EditText editText = findViewById(R.id.editTextLoginCustomer);
        return editText.getText().toString();
    }

    public void handleClick(View view){
        presenter.checkUsername();
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

}