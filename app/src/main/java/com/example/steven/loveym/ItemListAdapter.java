package com.example.steven.loveym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by STEVEN on 2017/3/23.
 */

public class ItemListAdapter extends ArrayAdapter<ItemOneLine> implements Filterable{

    private int resourceId;
    private ArrayList<ItemOneLine> inList;
    private ArrayList<ItemOneLine> resultList;
    private MyFilter filter;

    public ItemListAdapter(Context context, int resource, List<ItemOneLine> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.inList = (ArrayList<ItemOneLine>) objects;
        this.resultList = (ArrayList<ItemOneLine>) objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemOneLine item = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        }else{
            view = convertView;
        }
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        TextView itemPrice = (TextView) view.findViewById(R.id.average_price);
        TextView itemQuantity = (TextView)view.findViewById(R.id.quantity);
        itemName.setText(item.getItem_Name());
        itemPrice.setText(String.valueOf(Tools.formatDouble1(item.getItem_average_price())));
        itemQuantity.setText(String.valueOf(item.getItem_quantity()));
        return view;

    }

    @NonNull
    @Override
    public Filter getFilter() {

        if (filter == null){
            filter = new MyFilter();
        }
        return filter;
    }

    @Nullable
    @Override
    public ItemOneLine getItem(int position) {
        return resultList.get(position);
    }


    @Override
    public int getCount() {
        return resultList.size();
    }

    public ArrayList<ItemOneLine> getResultList(){
        return resultList;
    }












    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null && constraint.length() == 0){
                results.values = inList;
                results.count = inList.size();
            }else {
                ArrayList<ItemOneLine> filteredItems = new ArrayList<>();
                for (ItemOneLine item : inList){
                    String item_name = item.getItem_Name();
                    if (item_name.contains(constraint.toString())){
                        filteredItems.add(item);
                    }
                }
                results.values = filteredItems;
                results.count = filteredItems.size();
            }



            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0){
                notifyDataSetInvalidated();
            }else {
                resultList = (ArrayList<ItemOneLine>) results.values;
                notifyDataSetChanged();
            }



        }
    }




















}
