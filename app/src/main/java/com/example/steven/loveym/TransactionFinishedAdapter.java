package com.example.steven.loveym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by STEVEN on 2017/5/16.
 */

public class TransactionFinishedAdapter extends ArrayAdapter<TransactionOneLine>{

    int resourceId;



    public TransactionFinishedAdapter(Context context, int resource, List<TransactionOneLine> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TransactionOneLine transaction = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }

        TextView transactionId = (TextView) convertView.findViewById(R.id.transactionID);
        TextView customerName = (TextView)convertView.findViewById(R.id.customer_name);
        ImageView image1 = (ImageView)convertView.findViewById(R.id.paid_image);
        ImageView image2 = (ImageView)convertView.findViewById(R.id.dilivery_image);
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        TextView time = (TextView)convertView.findViewById(R.id.transaction_date);
        TextView profitRatio = (TextView) convertView.findViewById(R.id.profitRatio);

        transactionId.setText(String.valueOf(transaction.getTransactionID()));
        customerName.setText(transaction.getPerson().getName());
        Double totalCost = transaction.getTotal_cost()+transaction.getDelivery_cost();

        profitRatio.setText(Tools.formatDouble1((transaction.getTotal_revenue()-totalCost)/totalCost)*100 + "%");


        time.setText(Tools.showTime(transaction.getTrancationTime()));







        return convertView;
    }
}
