package com.example.steven.loveym;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DeliveryCompanies extends AppCompatActivity {

    ListView listView;
    ArrayList<DeliveryCompanyOneLine> companylist;
    DbHelper dbHelper;
    SQLiteDatabase db;
    DeliveryCompanyOneLine selectedcompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_companies);

        listView = (ListView) findViewById(R.id.listView);
        companylist = new ArrayList<>();
        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();


        Button new_company = (Button) findViewById(R.id.new_delivery_company);
        new_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewDeliveryCompanyDialog dialog = new NewDeliveryCompanyDialog
                        (DeliveryCompanies.this, R.layout.new_delivery_company_dialog, new NewDeliveryCompanyDialog.EventListener() {
                            @Override
                            public void event(String backName, double backRate, String backMemo) {

                                addCompany(backName,backRate,backMemo);

                            }
                        },null);
                dialog.show();

            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();


        loadList();
        DeliveryCompaniesListAdapter adapter = new DeliveryCompaniesListAdapter
                (DeliveryCompanies.this,R.layout.delivery_company_one_line,companylist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedcompany = companylist.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryCompanies.this);
                builder.setTitle(selectedcompany.getCompanyName());
                //builder.setMessage(company.getCompanyName() + String.valueOf(company.getDeliveryRate()));

                builder.setNegativeButton("编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        NewDeliveryCompanyDialog editdialog = new NewDeliveryCompanyDialog
                                (DeliveryCompanies.this, R.layout.new_delivery_company_dialog,
                                        new NewDeliveryCompanyDialog.EventListener() {
                                    @Override
                                    public void event(String backName, double backRate, String backMemo) {
                                        updateCompany(selectedcompany.getCompanyID(),backName,backRate,backMemo);

                                    }
                                },selectedcompany);
                        editdialog.show();

                    }
                });

                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DeliveryCompanies.this);
                        builder1.setTitle("小宝贝注意啦 -3-");
                        builder1.setMessage("确定要删除"+ selectedcompany.getCompanyName() + "吗？");
                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCompany(selectedcompany.getCompanyID());
                            }
                        });

                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder1.show();




                    }
                });

                builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });


    }


    private void addCompany(String name, double rate, String memo){


        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_NAME,name);
        values.put(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_RATE,rate);
        values.put(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_MEMO,memo);
        db.insert(DatabaseContract.DeliveryCompanyTable.TABLE_NAME,null,values);
        values.clear();

        loadList();
        DeliveryCompaniesListAdapter adapter = new DeliveryCompaniesListAdapter
                (DeliveryCompanies.this,R.layout.delivery_company_one_line,companylist);
        listView.setAdapter(adapter);

    }


    private void updateCompany(int id,String name, double rate, String memo){

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_NAME,name);
        values.put(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_RATE,rate);
        values.put(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_MEMO,memo);


        db.update(DatabaseContract.DeliveryCompanyTable.TABLE_NAME,values,
                DatabaseContract.DeliveryCompanyTable._ID + "=?",new String[]{String.valueOf(id)});

        values.clear();
        loadList();
        DeliveryCompaniesListAdapter adapter = new DeliveryCompaniesListAdapter
                (DeliveryCompanies.this,R.layout.delivery_company_one_line,companylist);
        listView.setAdapter(adapter);


    }



    private void loadList(){
        companylist.clear();

        Cursor cursor = db.query(DatabaseContract.DeliveryCompanyTable.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            DeliveryCompanyOneLine company = new DeliveryCompanyOneLine();
            company.setCompanyID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable._ID)));
            company.setCompanyName(cursor.getString(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_NAME)));
            company.setDeliveryRate(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_RATE)));
            company.setCompanyMemo(cursor.getString(cursor.getColumnIndex(DatabaseContract.DeliveryCompanyTable.COLUMN_NAME_MEMO)));
            companylist.add(company);
        }
        cursor.close();

    }

    private void deleteCompany(int id){

        db.delete(DatabaseContract.DeliveryCompanyTable.TABLE_NAME,
                DatabaseContract.DeliveryCompanyTable._ID + "=?",new String[]{String.valueOf(id)});

        loadList();
        DeliveryCompaniesListAdapter adapter = new DeliveryCompaniesListAdapter
                (DeliveryCompanies.this,R.layout.delivery_company_one_line,companylist);
        listView.setAdapter(adapter);

    }


}
