package com.example.quickbasket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerCheckoutRecyclerViewAdapter extends RecyclerView.Adapter<CustomerCheckoutRecyclerViewAdapter.ViewHolder_Checkout> {
    //private static double total;
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<Double> mPrices = new ArrayList<>();
    private ArrayList<Integer> mQtys = new ArrayList<>();
    private Context mContext;



    public CustomerCheckoutRecyclerViewAdapter(Context mContext, ArrayList<String> mProductName, ArrayList<String> mImages, ArrayList<Double> mPrice, ArrayList<Integer> mQty) {
        this.mProductNames = mProductName;
        this.mImages = mImages;
        this.mPrices = mPrice;
        this.mQtys = mQty;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder_Checkout onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layout_listproducts_customer, parent, false);
        ViewHolder_Checkout holder = new ViewHolder_Checkout(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Checkout holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.productName.setText(mProductNames.get(position));
        holder.price.setText("$" + Double.toString(mPrices.get(position)));
        holder.qty.setText(Integer.toString(mQtys.get(position)));
    }

    @Override
    public int getItemCount() {
        return mPrices.size();
    }

    public class ViewHolder_Checkout extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView productName;
        TextView price;
        TextView qty;
        ConstraintLayout parentLayout;

        public ViewHolder_Checkout(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            parentLayout = itemView.findViewById(R.id.parent_layout_store);

           /* //Functionality for '+' Button
            itemView.findViewById(R.id.addQty).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentQty = qty.getText().toString();
                    int newQty = Integer.parseInt(currentQty) + 1;
                    qty.setText(Integer.toString(newQty));
                }
            });

            //Functionality for '-' Button
            itemView.findViewById(R.id.minusQty).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentQty = qty.getText().toString();
                    int newQty = Integer.parseInt(currentQty) - 1;

                    if (newQty < 0){
                        newQty = 0;
                    }
                    qty.setText(Integer.toString(newQty));
                }
            });*/

            //total = Integer.parseInt(qty.getText().toString()) * Double.parseDouble(price.getText().toString());
        }
    }

    /*public static double getSubTotal(){
       return total;
    }*/
}
