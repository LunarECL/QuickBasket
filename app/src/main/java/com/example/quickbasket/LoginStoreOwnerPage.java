package com.example.quickbasket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public void displayMessage2(String message){
        TextView textView = findViewById(R.id.textView29);
        textView.setText(message);
    }

    public String getUsername(){
        EditText editText = findViewById(R.id.editTextLoginOwner);
        return editText.getText().toString();
    }

    public String getPassword(){
        EditText editText = findViewById(R.id.editTextPasswordOwner);
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
        presenter.checkOwnerUsername();
        presenter.checkOwnerPassword();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.StoreOwner);
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot child: task.getResult().getChildren()){
                    StoreOwner owner = child.getValue(StoreOwner.class);
                    if (owner.getUsername().equals(getUsername()) && owner.getPassword().equals(getPassword())) {
                        id = owner.getOwnerID();
                        Log.d("id", String.valueOf(id));
                        ready();
                        break;
                    }
                }
            }
        });

    }

    private void ready(){
        Intent intent = new Intent(this, MainScreenOwner.class);
        intent.putExtra(Constant.OwnerID, id);
        startActivity(intent);
    }

}