package com.example.quickbasket;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{

    private ArrayList<OrderListItem> orderList;
    private Context context;

    public OrderListAdapter(ArrayList<OrderListItem> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View orderListItem = layoutInflater.inflate(R.layout.order_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(orderListItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderListItem orderListItem = orderList.get(position);
        holder.textView.setText(orderListItem.getDescription());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop();
        Glide.with(context).load(orderListItem.getUrl()).apply(options).into(holder.imageView);
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ViewOrderOwner.class);
//                intent.putExtra("ownerID", ownerID);
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.orderListImageView);
            this.textView = (TextView) itemView.findViewById(R.id.orderListTextView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.orderListRelativeLayout);
        }
    }
}
