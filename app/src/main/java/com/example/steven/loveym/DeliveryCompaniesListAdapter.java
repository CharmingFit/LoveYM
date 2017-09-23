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
 * Created by STEVEN on 2017/4/23.
 */

public class DeliveryCompaniesListAdapter extends ArrayAdapter<DeliveryCompanyOneLine> {

    private int resourceId;
    private Context mContext;



    public DeliveryCompaniesListAdapter(Context context, int resource, List<DeliveryCompanyOneLine> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DeliveryCompanyOneLine company = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, null);
        }

        TextView companyName = (TextView) convertView.findViewById(R.id.delivery_company_name);
        TextView companyRate = (TextView) convertView.findViewById(R.id.delivery_company_rate);
        TextView companyMemo = (TextView) convertView.findViewById(R.id.delivery_company_memo);
        companyName.setText(company.getCompanyName());
        companyRate.setText(String.valueOf(company.getDeliveryRate()));

        if (company.getCompanyMemo().equals("Unknown")){
            companyMemo.setVisibility(View.INVISIBLE);
        }else{
            companyMemo.setText(company.getCompanyMemo());
        }

       // companyMemo.setText(company.getCompanyMemo());

        return convertView;
    }












}
