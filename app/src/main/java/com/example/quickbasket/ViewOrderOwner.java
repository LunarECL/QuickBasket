package com.example.quickbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewOrderOwner extends AppCompatActivity {

    private Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_owner);

        ListView mListView = (ListView) findViewById(R.id.orderlist);

        back_button = (Button) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOwnerMainPage();
            }
        });


        //Demo Run with hardcoded items not relevant in future

        Item item1 = new Item("iphone",800.0,"Apple");
        Item item2 = new Item("surface pro",1000.0,"Microsoft");
        Item item3 = new Item("pixel 6",700.0,"Google");

        ArrayList<Item> itemList = new ArrayList<>();

        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.adapter_view_layout, itemList);
        mListView.setAdapter(adapter);



    }

    public void openOwnerMainPage(){
        Intent intent  = new Intent(this, main_screen_owner.class);
        startActivity(intent);
    }




}