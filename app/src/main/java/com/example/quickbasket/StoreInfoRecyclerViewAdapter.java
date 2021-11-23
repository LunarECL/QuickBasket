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

public class StoreInfoRecyclerViewAdapter extends RecyclerView.Adapter<StoreInfoRecyclerViewAdapter.ViewHolder_StoreInfo> {

    private ArrayList<String> mStoreNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mLocations = new ArrayList<>();
    private Context mContext;


    public StoreInfoRecyclerViewAdapter(Context mContext, ArrayList<String> mStoreName, ArrayList<String> mImages, ArrayList<String> mLocation) {
        this.mStoreNames = mStoreName;
        this.mImages = mImages;
        this.mLocations = mLocation;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder_StoreInfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_liststore, parent, false);
        ViewHolder_StoreInfo holder = new ViewHolder_StoreInfo(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder_StoreInfo holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.storeName.setText(mStoreNames.get(position));
        holder.location.setText(mLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public class ViewHolder_StoreInfo extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView storeName;
        TextView location;
        RelativeLayout parentLayout;

        public ViewHolder_StoreInfo(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.store_image);
            storeName = itemView.findViewById(R.id.store_name);
            location = itemView.findViewById(R.id.location);
            parentLayout = itemView.findViewById(R.id.parent_layout_store);

        }
    }
}


