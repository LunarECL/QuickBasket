package com.example.quickbasket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class QtyRecylerViewAdapter extends RecyclerView.Adapter<QtyRecylerViewAdapter.ViewHolder> {
    private ArrayList<String> mProductNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mPrices = new ArrayList<>();
    private Context mContext;


    public QtyRecylerViewAdapter(Context mContext, ArrayList<String> mProductNames, ArrayList<String> mImages, ArrayList<String> mPrice) {
        this.mProductNames = mProductNames;
        this.mImages = mImages;
        this.mPrices = mPrice;
        this.mContext = mContext;
    }

    @Override
    public QtyRecylerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_liststore, parent, false);
        QtyRecylerViewAdapter.ViewHolder holder = new QtyRecylerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QtyRecylerViewAdapter.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.productName.setText(mProductNames.get(position));
        holder.location.setText(mPrices.get(position));
    }

    @Override
    public int getItemCount() {
        return mPrices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView productName;
        TextView location;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            location = itemView.findViewById(R.id.price);
            parentLayout = itemView.findViewById(R.id.parent_layout_store);

        }
    }
}
