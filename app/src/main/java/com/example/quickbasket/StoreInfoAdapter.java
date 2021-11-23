package com.example.quickbasket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StoreInfoAdapter extends ArrayAdapter<StoreInformation>{

    private Context mContext;
    private int mResource;

    public StoreInfoAdapter(Context context, int resource, ArrayList<StoreInformation> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String birthday = getItem(position).getBirthday();
        String sex = getItem(position).getSex();

        StoreInformation person = new StoreInformation(name, birthday, sex);

        LayoutInflater inflator = LayoutInflater.from(mContext);
        convertView = inflator.inflate(mResource, parent, false);
        return convertView;
    }
}
