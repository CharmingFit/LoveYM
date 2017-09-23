package com.example.steven.loveym;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class OrderFinished extends AppCompatActivity {

    ArrayList<TransactionOneLine> list;
    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_finished);
        ListView listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        loadList();


        TransactionFinishedAdapter adapter = new TransactionFinishedAdapter(this,R.layout.processing_order_one_line,list);
        listView.setAdapter(adapter);




    }





    private void loadList(){
        Cursor cursor = db.query(DatabaseContract.TransactionTable.TABLE_NAME,
                new String[]{DatabaseContract.TransactionTable._ID,DatabaseContract.TransactionTable.COLUMN_NAME_NAME,
                DatabaseContract.TransactionTable.COLUMN_NAME_REVENUE,DatabaseContract.TransactionTable.COLUMN_NAME_Cost,
                        DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COST, DatabaseContract.TransactionTable.COLUMN_NAME_TIME},null,null,null,null,null);

        while(cursor.moveToNext()){
            TransactionOneLine transaction = new TransactionOneLine();
            transaction.setTransactionID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable._ID)));
            transaction.setPerson(Tools.getCustomer(cursor
                    .getInt(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_NAME)),this));


            transaction.setTotal_revenue(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_REVENUE)));
            transaction.setTotal_cost(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_Cost)));
            transaction.setDelivery_cost(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COST)));



            transaction.setTrancationTime
                    (cursor.getLong(cursor.getColumnIndex(DatabaseContract.TransactionTable.COLUMN_NAME_TIME)));

            list.add(transaction);
        }
        cursor.close();

    }



}
