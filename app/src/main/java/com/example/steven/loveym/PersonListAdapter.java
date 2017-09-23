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
 * Created by STEVEN on 2017/3/12.
 */

public class PersonListAdapter extends ArrayAdapter<PersonOneLine> implements Filterable {
    private int personID;
    private CustomerFilter customerFilter;
    private ArrayList<PersonOneLine> customerList;
    private ArrayList<PersonOneLine> resultList;



    //构造函数，将传入的单行layout记录在全局变量
    public PersonListAdapter(Context context, int resource, ArrayList<PersonOneLine> objects) {
        super(context, resource, objects);
        personID = resource;
        this.customerList = objects;
        this.resultList = objects;
    }

    @NonNull
    @Override
    //此方法将自动执行，
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonOneLine person = getItem(position);//得到position id对应的list中的实例
        View view;
        if (convertView == null) { //查看缓存里是否有这个view，若有则直接用，否则得到单行layout对应的View实例
            view = LayoutInflater.from(getContext()).inflate(personID, null); //得到单行layout对应的View实例
        }else{
            view = convertView;
        }

        TextView personName = (TextView) view.findViewById(R.id.customer_name);//得到该实例中的TextView控件实例
        personName.setText(person.getName());
        return view;
    }


    @Nullable
    @Override
    public PersonOneLine getItem(int position) {
        return resultList.get(position);
    }


    @Override
    public int getCount() {
        return resultList.size();
    }
/*
    @Override
    public long getItemId(int position) {
        return resultList.indexOf(getItem(position));
    }*/

    @NonNull
    @Override
    public Filter getFilter() {
        if (customerFilter == null){
            customerFilter = new CustomerFilter();

        }

        return customerFilter; //返回自定义的Filter的实例
    }

    public ArrayList<PersonOneLine> getResultList(){

        return resultList;
    }






    private class CustomerFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0){ //当有东西输入时
                ArrayList<PersonOneLine> filteredCustomers = new ArrayList<>();
                for (PersonOneLine person:customerList){
                    String thePersonName = person.getName();
                    String thePersonWechatName = person.getWechatName();
                    if (thePersonName.contains(constraint.toString()) || thePersonWechatName.contains(constraint.toString())) {
                        filteredCustomers.add(person);

                    }
                }
                results.values = filteredCustomers;
                results.count = filteredCustomers.size();

            }else{
                results.values = customerList;
                results.count = customerList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results.count == 0){
                notifyDataSetInvalidated();
            }else {
                resultList = (ArrayList<PersonOneLine>) results.values;
                notifyDataSetChanged();
            }
        }
    }




}
