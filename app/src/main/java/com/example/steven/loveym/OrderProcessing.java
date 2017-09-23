package com.example.steven.loveym;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderProcessing extends AppCompatActivity {


    ExpandableListView E_listView;
    ArrayList<TransactionOneLine> transactionList;
    DbHelper dbHelper;
    SQLiteDatabase db;
    //SearchView searchView;
    Processing_Transaction_Adapter adapter;
    ArrayList<DeliveryCompanyOneLine> companyLists;
    Spinner paidSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_processing);

        E_listView = (ExpandableListView) findViewById(R.id.E_Listview);
        transactionList = new ArrayList<>();
        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        paidSpinner = (Spinner)findViewById(R.id.paid_spinner);
        Spinner deliveredSpinner = (Spinner)findViewById(R.id.delivery_spinner);
        //searchView = (SearchView) findViewById(R.id.title_searcher);

        companyLists = getCompanies();
        adapter = new Processing_Transaction_Adapter(OrderProcessing.this, R.layout.processing_order_one_line,
                R.layout.processing_transaction_childlist, transactionList, companyLists, new Processing_Transaction_Adapter.AdapterListener() {
            @Override
            public void changedEvent(TransactionOneLine backTransaction, int eventId) {

                if (eventId == 1) {
                    deliveryinfoChanged(backTransaction);
                }else if (eventId == 2){

                    //保存信息，并刷新

                    saveChangedTransaction(backTransaction);


                }

                Toast.makeText(OrderProcessing.this, ""+ backTransaction.getPerson().getName(), Toast.LENGTH_SHORT).show();
            }
        });










    }


    @Override
    protected void onStart() {
        super.onStart();

        makeList();
        E_listView.setAdapter(adapter);
        E_listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i=0;i<adapter.getGroupCount();i++){
                    if (groupPosition !=i){
                        E_listView.collapseGroup(i);
                    }
                }
            }
        });



        E_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // TransactionOneLine theTransaction = adapter.getResultList().get(position);


            }
        });


        paidSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    for (TransactionOneLine transaction : transactionList){
                        if (transaction.getPaidStatus()!=1){



                            //transactionList.remove(transaction);
                            //transactionList.clear();
                            //int pos = transactionList.indexOf(transaction);
                            //  transactionList.remove(pos);
                            Toast.makeText(OrderProcessing.this, "sssss", Toast.LENGTH_SHORT).show();
                        }
                    }

                     adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void makeList() { //TransactionTable
        transactionList.clear();
        Cursor cursor = db.query(DatabaseContract.TransactionTable.TABLE_NAME, null, null,null,null,null,
                DatabaseContract.TransactionTable.COLUMN_NAME_TIME+" desc");
        while (cursor.moveToNext()) {
            TransactionOneLine transactionOneLine = new TransactionOneLine();
            transactionOneLine.setTransactionID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable._ID)));


            ArrayList<ItemOneLine> items = parseItemArray(cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_DATA)));

            transactionOneLine.setItems(items);


            int customerId = cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_NAME));
            PersonOneLine person = GetCustomer(customerId);

            transactionOneLine.setPerson(person);

            transactionOneLine.setMemo(cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_MEMO)));
            transactionOneLine.setDeliveryStatus(cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_DELIVERY_STATUS)));
            transactionOneLine.setPaidStatus(cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_PAID)));
            transactionOneLine.setDeliveryNumber(cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TRACE_ID)));
/*
            if (cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_PAID)) != 0) {
                transactionOneLine.setPaidImage(R.drawable.paid_sign);
            }


            if (!cursor.getString(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TRACE_ID)).equals("unknown")) {
                transactionOneLine.setDeliveredImage(R.drawable.delivered_sign);

            }
*/
            transactionOneLine.setTrancationTime
                    (cursor.getLong(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TIME)));


            transactionOneLine.setTotal_revenue(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_REVENUE)));
            transactionOneLine.setTotal_cost(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_Cost)));
            transactionOneLine.setTotal_weight(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_WEIGHT)));

            int comId = cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COMPANY));
            DeliveryCompanyOneLine deliveryCompany = null;
            int comPosition;
            for (DeliveryCompanyOneLine company : companyLists){
                if (comId == company.getCompanyID()){
                    comPosition = companyLists.indexOf(company);
                    deliveryCompany = company;
                    deliveryCompany.setComPosition(comPosition);
                }
            }
            transactionOneLine.setDeliveryCompany(deliveryCompany);

            double thisDeliveryCost = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COST));

           // if (thisDeliveryCost != 0){
                transactionOneLine.setDelivery_cost(thisDeliveryCost);
           // }



            //double thiswWeight = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_WEIGHT));

           // if (thiswWeight <1000){
           //     transactionOneLine.setDelivery_cost(6.0);
          //  }else{
          //      transactionOneLine.setDelivery_cost(thiswWeight*6/1000);
          //  }






            transactionList.add(transactionOneLine);


        }

        cursor.close();


    }

    private PersonOneLine GetCustomer(int customerIDinqueue){//CustomerTable

        Cursor cursor =db.query(DatabaseContract.CutormerTable.TABLE_NAME,
                new String[]{DatabaseContract.CutormerTable.COLUMN_NAME_NAME,
                        DatabaseContract.CutormerTable.COLUMN_NAME_TEL,DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS},
                DatabaseContract.CutormerTable._ID + "= ?",new String[]{String.valueOf(customerIDinqueue)},null,null,null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_NAME));
        int telephone = cursor.getInt(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_TEL));
        String address = cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS));
        cursor.close();

        PersonOneLine person = new PersonOneLine();
        person.setCustomer_ID(customerIDinqueue);
        person.setName(name);
        person.setTelephone(telephone);
        person.setAddress(address);



        return person;
    }



//在这里添加一个transactionOneLine 的子包 放入transaction数据，将adapter里的解析拉出来到这里，以object形式放入List,再在adapter中得到，就如同封装放入的这个custemr

    private ArrayList<ItemOneLine> parseItemArray(String data){//ProductTable

        ArrayList<ItemOneLine> list = new ArrayList<>();
        String[] strings = data.split("#");

        for (int i=0;i<strings.length;i++){
            String[] transaction = strings[i].split("/");
            ItemOneLine item = new ItemOneLine();
            for (int a=0;a<transaction.length;a++){
                if (a == 0){
                    //item.setItem_Name("sddd");
                    item.setItem_ID(Integer.parseInt(transaction[0]));
                    item.setItem_Name(findItem(Integer.parseInt(transaction[0])).getItem_Name());
                }else if (a ==1){
                    item.setItem_quantity(Integer.parseInt(transaction[1]));
                }else{
                    item.setItem_average_price(Double.parseDouble(transaction[2]));
                }
            }
            list.add(item);
        }

        return list;

    }

    private ItemOneLine findItem(int id){

        Cursor cursor = db.query(DatabaseContract.ProductTable.TABLE_NAME,
                new String[]{DatabaseContract.ProductTable.COLUMN_NAME_NAME},DatabaseContract.ProductTable._ID + "=?",
                new String[]{String.valueOf(id)},null,null,null);

        cursor.moveToNext();

        String itemName = cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_NAME));

        cursor.close();

        ItemOneLine item = new ItemOneLine();
        item.setItem_Name(itemName);


        return item;
    }

    private ArrayList<DeliveryCompanyOneLine> getCompanies(){

        Cursor cursor = db.query(DatabaseContract.DeliveryCompanyTable.TABLE_NAME,null,null,null,null,null,null);
        ArrayList<DeliveryCompanyOneLine> list = new ArrayList<>();
        while (cursor.moveToNext()){
            DeliveryCompanyOneLine company = new DeliveryCompanyOneLine();
            company.setCompanyID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable._ID)));
            company.setCompanyName(cursor.getString(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_NAME)));
            company.setDeliveryRate(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_RATE)));
            company.setCompanyMemo(cursor.getString(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_MEMO)));
            list.add(company);

        }
        cursor.close();

        return list;

    }


    private void deliveryinfoChanged (TransactionOneLine inTransaction){

        for (TransactionOneLine transaction :transactionList){
            if (transaction.getTransactionID() == inTransaction.getTransactionID()){

                int position = transactionList.indexOf(transaction);
                transactionList.set(position,inTransaction);
                break;
            }
        }
        adapter.notifyDataSetChanged();

    }



    private void saveChangedTransaction(TransactionOneLine inTransaction){

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_WEIGHT,inTransaction.getTotal_weight());
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COST,inTransaction.getDelivery_cost());
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TRACE_ID,inTransaction.getDeliveryNumber());
        if (inTransaction.getDeliveryCompany()!=null){
            values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COMPANY,inTransaction.getDeliveryCompany().getCompanyID());
        }
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_PAID,inTransaction.getPaidStatus());
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_DELIVERY_STATUS,inTransaction.getDeliveryStatus());


        //存入快递公司id到database


        db.update(DatabaseContract.TransactionTable.TABLE_NAME,values,
                DatabaseContract.TransactionTable._ID + "=?",
                new String[]{String.valueOf(inTransaction.getTransactionID())});

        adapter.notifyDataSetChanged();
    }

}
