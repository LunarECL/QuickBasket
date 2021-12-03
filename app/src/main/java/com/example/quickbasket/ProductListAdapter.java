package com.example.quickbasket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {

    private Context mContext;
    int mResource;

    //Default constructor for the itemListAdapter

    public ProductListAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    public View getView(int position, View convertView, ViewGroup parent){

        //get item information
        Integer id = getItem(position).getId();
        String productName = getItem(position).getName();
        String description = getItem(position).getDescription();
        Double price = getItem(position).getPrice();
        String brand = getItem(position).getBrand();
        String imgURL = getItem(position).getImageURL();
        Integer qty = getItem(position).getQty();

        //create item with the information
        Product product = new Product(id,productName,description,brand,price,imgURL,qty);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        //Create TextViews to display the list from XML file

        TextView tvProductName = (TextView) convertView.findViewById(R.id.ProductName);

        TextView tvPrice = (TextView) convertView.findViewById(R.id.PriceView);

        TextView tvBrand = (TextView) convertView.findViewById(R.id.Brand);

        //TextView tvQty = (TextView) convertView.findViewById(R.id.);

        //Set the TextViews to display the information wanted

        tvProductName.setText(productName);
        tvPrice.setText(Double.toString(price));
        tvBrand.setText(brand);

        return convertView;

    }



}
