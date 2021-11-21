package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class main_screen_owner extends AppCompatActivity {

    private static final String TAG = "main_screen_owner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_owner);

        //RecyclerVeiw list = (RecyclerVeiw) findViewById(R.id.listOrders);
        Log.d(TAG,"onCreate: Started");

        ArrayList<String> names = new ArrayList<>();
        names.add("Ankit");
        names.add("Joshua");
        names.add("Beatrice");
        names.add("Virthiya");
        names.add("Adrian");
    }
}