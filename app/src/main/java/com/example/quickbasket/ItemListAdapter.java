package com.example.quickbasket;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ItemListAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    int mResource;

    //Default constructor for the itemListAdapter

    public ItemListAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        //get item information
        String productName = getItem(position).getProductName();
        String price = getItem(position).getPrice();
        String brand = getItem(position).getBrand();

        //create item with the information
        Item item = new Item(productName,price,brand);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        //Create TextViews to display the list from XML file

        TextView tvProductName = (TextView) convertView.findViewById(R.id.ProductName);

        TextView tvPrice = (TextView) convertView.findViewById(R.id.PriceView);

        TextView tvBrand = (TextView) convertView.findViewById(R.id.Brand);

        //Set the TextViews to display the information wanted

        tvProductName.setText(productName);
        tvPrice.setText(price);
        tvBrand.setText(brand);

        return convertView;

    }


}

