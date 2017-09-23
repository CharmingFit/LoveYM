package com.example.steven.loveym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by STEVEN on 2017/4/9.
 */

public class TransactionListAdapter extends ArrayAdapter<TransactionOneLine>{


    private int resourceId;
    private ArrayList<TransactionOneLine> inList;
    private ArrayList<TransactionOneLine> resultList;
    private TransactionFilter filter;

    public TransactionListAdapter(Context context, int resource, List<TransactionOneLine> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.inList = (ArrayList<TransactionOneLine>) objects;
        this.resultList = (ArrayList<TransactionOneLine>) objects;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TransactionOneLine transaction = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView transactionId = (TextView) view.findViewById(R.id.transactionID);
        TextView customerName = (TextView) view.findViewById(R.id.customer_name);
        ImageView paidImage = (ImageView) view.findViewById(R.id.paid_image);
        ImageView deliveryImage = (ImageView) view.findViewById(R.id.dilivery_image);
        TextView transactionTime = (TextView) view.findViewById(R.id.transaction_date);


        transactionId.setText(String.valueOf(transaction.getTransactionID()));
        customerName.setText(transaction.getPerson().getName());
        paidImage.setImageResource(transaction.getPaidImage());
        deliveryImage.setImageResource(transaction.getDeliveredImage());//已废弃
        transactionTime.setText(showTime(transaction.getTrancationTime()));//已废弃


        return view;
    }


    private String showTime(long time){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        Date transactionDate = new Date(time);
        String theTime = dateFormat.format(transactionDate);
        return theTime;

    }


    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new TransactionFilter();
        }
        return filter;
    }

    @Nullable
    @Override
    public TransactionOneLine getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    public ArrayList<TransactionOneLine> getResultList() {
        return resultList;
    }

    private class TransactionFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null && constraint.length() == 0){
                results.values = inList;
                results.count = inList.size();
            }else {
                ArrayList<TransactionOneLine> filteredTransactionList = new ArrayList<>();
                for (TransactionOneLine transaction : inList){
                    if (transaction.getPerson().getName().contains(constraint.toString())){
                        filteredTransactionList.add(transaction);
                    }
                }

                results.values = filteredTransactionList;
                results.count = filteredTransactionList.size();

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0){
                notifyDataSetInvalidated();
            }else{
                resultList = (ArrayList<TransactionOneLine>) results.values;
                notifyDataSetChanged();
            }


        }
    }














}
