package com.example.steven.loveym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by STEVEN on 2017/4/2.
 */

public class OrderNewListAdapter extends ArrayAdapter<ItemOneLine>{


    private int resourceId;


    public OrderNewListAdapter(Context context, int resource, List<ItemOneLine> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemOneLine item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView quantity =(TextView) view.findViewById(R.id.item_quantity);
        TextView price =(TextView) view.findViewById(R.id.item_price);
        name.setText(item.getItem_Name());
        price.setText(String.valueOf(Tools.formatDouble1(item.getItem_average_price())));
        quantity.setText(String.valueOf(item.getItem_quantity()));

        return view;
    }
}
