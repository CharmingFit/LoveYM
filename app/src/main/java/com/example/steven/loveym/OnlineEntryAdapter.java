package com.example.steven.loveym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by STEVEN on 2017/4/24.
 */

public class OnlineEntryAdapter extends ArrayAdapter<DeliveryCompanyOneLine> implements SpinnerAdapter {

    private int resourceId;



    public OnlineEntryAdapter(Context context, int resource, List<DeliveryCompanyOneLine> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DeliveryCompanyOneLine company = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }
        TextView companyname = (TextView) convertView.findViewById(R.id.one_entry);
        companyname.setText(company.getCompanyName());

        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        DeliveryCompanyOneLine company = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }
        TextView companyname = (TextView) convertView.findViewById(R.id.one_entry);
        companyname.setTextSize(30);
        companyname.setText(company.getCompanyName());

        return convertView;
    }
}
