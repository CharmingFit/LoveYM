package com.example.steven.loveym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by STEVEN on 2017/3/12.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_CUSTOMERS = "create table " + DatabaseContract.CutormerTable.TABLE_NAME+
            " (" + DatabaseContract.CutormerTable._ID +" integer primary key,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_NAME + " text,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_TEL + " integer,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS + " text,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME +" text,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_WECHATID + " text,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_ID + " integer,"
            + DatabaseContract.CutormerTable.COLUMN_NAME_MEMO +" text)";

    private static final String SQL_CREATE_PRODUCTS = "create table " + DatabaseContract.ProductTable.TABLE_NAME+
            " (" + DatabaseContract.CutormerTable._ID +" integer primary key,"
            + DatabaseContract.ProductTable.COLUMN_NAME_NAME + " text,"
            + DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY + " integer,"
            + DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE + " real,"
            + DatabaseContract.ProductTable.COLUMN_NAME_CATEGORY +" text,"
            + DatabaseContract.ProductTable.COLUMN_NAME_WEIGHT + " real,"
            + DatabaseContract.ProductTable.COLUMN_NAME_NORMAL_PRICE + " real,"
            + DatabaseContract.ProductTable.COLUMN_NAME_MEMO +" text)";


    private static final String SQL_CREATE_TRANSACTIONS = "create table " + DatabaseContract.TransactionTable.TABLE_NAME+
            " (" + DatabaseContract.CutormerTable._ID +" integer primary key,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_NAME + " integer,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_DATA + " text,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_REVENUE + " real,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_Cost +" real,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_WEIGHT +" real,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COST + " real,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_TRACE_ID + " text,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COMPANY + " integer,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_PAID + " integer,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_DELIVERY_STATUS + " integer,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_STATUS + " integer,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_TIME + " numeric,"
            + DatabaseContract.TransactionTable.COLUMN_NAME_MEMO +" text)";


    private static final String SQL_CREATE_DELIVERY_COMPANIES = "create table " + DatabaseContract.DeliveryCompanyTable.TABLE_NAME+
            " (" + DatabaseContract.CutormerTable._ID +" integer primary key,"
            + DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_NAME + " text,"
            + DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_RATE + " real,"
            + DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_MEMO +" text)";





    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="yuanbao.db";
    private Context mcontext;

    //写一个构造器使得只需传入context就可以
    public DbHelper (Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        mcontext = context;

    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CUSTOMERS);
        db.execSQL(SQL_CREATE_PRODUCTS);
        db.execSQL(SQL_CREATE_TRANSACTIONS);
        db.execSQL(SQL_CREATE_DELIVERY_COMPANIES);
        Toast.makeText(mcontext, "DATABASE CREATED",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


}
