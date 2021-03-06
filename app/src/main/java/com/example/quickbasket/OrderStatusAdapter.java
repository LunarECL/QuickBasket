package com.example.quickbasket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder_Status> {
    //private static double total;
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<Double> mPrices = new ArrayList<>();
    private ArrayList<Integer> mQtys = new ArrayList<>();
    private Context mContext;

    public OrderStatusAdapter(Context mContext, ArrayList<String> mProductName, ArrayList<String> mImages, ArrayList<Double> mPrice, ArrayList<Integer> mQty) {
        this.mProductNames = mProductName;
        this.mImages = mImages;
        this.mPrices = mPrice;
        this.mQtys = mQty;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder_Status onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_layout_listproducts_order, parent, false);
        ViewHolder_Status holder = new ViewHolder_Status(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Status holder, int position) {
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

    public class ViewHolder_Status extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView productName;
        TextView price;
        TextView qty;
        ConstraintLayout parentLayout;

        public ViewHolder_Status(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            parentLayout = itemView.findViewById(R.id.parent_layout_store);
        }
    }
}


