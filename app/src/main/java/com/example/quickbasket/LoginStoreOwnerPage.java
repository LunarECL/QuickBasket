package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginStoreOwnerPage extends AppCompatActivity implements Contract.View{

    private Contract.Presenter presenter;
    private int id;
    public void displayMessage(String message){
        TextView textView = findViewById(R.id.textViewLoginOwner);
        textView.setText(message);
    }

    public String getUsername(){
        EditText editText = findViewById(R.id.editTextLoginOwner);
        return editText.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_store_owner_page);

        ImageButton testBackButtonOwnerLogin = findViewById(R.id.backButton_OwnerLogin);
        testBackButtonOwnerLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
            }
        });

        presenter = new MyPresenter(new MyModel(), this);
    }

    public void handleClick(View view){
        presenter.checkUsername();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.StoreOwner);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child: snapshot.getChildren()){
                    StoreOwner owner = child.getValue(StoreOwner.class);
                    if (owner.getUsername().equals(getUsername())) {
                        id = owner.getOwnerID();
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
        Intent intent = new Intent(this, main_screen_owner.class);
        intent.putExtra(Constant.OwnerID, id);
        startActivity(intent);
    }

}