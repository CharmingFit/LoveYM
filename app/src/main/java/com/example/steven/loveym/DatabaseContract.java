package com.example.steven.loveym;

import android.provider.BaseColumns;

/**
 * Created by STEVEN on 2017/3/12.
 */

public final class DatabaseContract {

    private DatabaseContract(){}//防止被实例化出来

    public static class CutormerTable implements BaseColumns{
        public static final String TABLE_NAME = "Customers";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_TEL = "Tel";
        public static final String COLUMN_NAME_ADDRESS = "Address";
        public static final String COLUMN_NAME_WECHATNAME ="WechatName";
        public static final String COLUMN_NAME_WECHATID = "WechatID";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_MEMO = "Memo";
    }


    public static class ProductTable implements BaseColumns{
        public static final String TABLE_NAME = "Products";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_QUANTITY = "Quantity";
        public static final String COLUMN_NAME_AVERAGE_PRICE = "AvgCost";
        public static final String COLUMN_NAME_CATEGORY ="Category";
        public static final String COLUMN_NAME_WEIGHT = "Weight";
        public static final String COLUMN_NAME_NORMAL_PRICE = "Price";
        public static final String COLUMN_NAME_MEMO = "Memo";
    }

    public static class TransactionTable implements BaseColumns{
        public static final String TABLE_NAME = "Transactions";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_DATA = "DATA";
        public static final String COLUMN_NAME_REVENUE = "Revenue";
        public static final String COLUMN_NAME_Cost ="Cost";
        public static final String COLUMN_NAME_WEIGHT ="Weight";
        public static final String COLUMN_NAME_TRANSPORT_COST = "TransCost";
        public static final String COLUMN_NAME_TRACE_ID = "TraceID";
        public static final String COLUMN_NAME_TRANSPORT_COMPANY = "TransCo";
        public static final String COLUMN_NAME_PAID = "Paid";
        public static final String COLUMN_NAME_DELIVERY_STATUS = "delivered";
        public static final String COLUMN_NAME_STATUS = "finished";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_MEMO = "Memo";
    }


    public static class DeliveryCompanyTable implements BaseColumns{
        public static final String TABLE_NAME = "DeliveryCompanies";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_RATE = "Rate";
        public static final String COLUMN_NAME_MEMO = "Memo";
    }






}
