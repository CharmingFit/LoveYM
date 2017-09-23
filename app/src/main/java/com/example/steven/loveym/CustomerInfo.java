package com.example.steven.loveym;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomerInfo extends AppCompatActivity implements View.OnClickListener{

    String theName;
    String theWeChat;
    int customer_ID;
    SQLiteDatabase db;
    TextView name;
    TextView tel ;
    TextView address;
    TextView wechatName;
    TextView wechatID;
    TextView ID;
    TextView memo;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        Button edit_button = (Button) findViewById(R.id.title_edit);
        Button delete_button = (Button)findViewById(R.id.button_customer_delete);




        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        name = (TextView) findViewById(R.id.name_new_customer);
        tel = (TextView) findViewById(R.id.Tel_new_customer);
        address = (TextView) findViewById(R.id.address_new_customer);
        wechatName = (TextView) findViewById(R.id.wechat_name_new_customer);
        wechatID = (TextView) findViewById(R.id.wechat_id_new_customer);
        ID = (TextView) findViewById(R.id.id_new_customer);
        memo= (TextView) findViewById(R.id.memo_new_customer);

        Intent intent = getIntent();
        theName = intent.getStringExtra("selectedName");
        theWeChat = intent.getStringExtra("selectedWechat");
        customer_ID = intent.getIntExtra("selectedid",0);

/*
        Cursor cursor = db.query(DatabaseContract.CutormerTable.TABLE_NAME,null,
                DatabaseContract.CutormerTable.COLUMN_NAME_NAME + "= ?" +" AND "
                + DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME + "= ?",new String[]{theName,theWeChat},
                null,null,null);
*/



        edit_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);




    }


    @Override
    protected void onStart() {
        super.onStart();
        drawCustomer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_edit:
                Intent intent = new Intent(CustomerInfo.this,AddCustomer.class);
                //intent.putExtra("selectedName",theName);
                //intent.putExtra("selectedWechat",theWeChat);
                intent.putExtra("selectedid",customer_ID);
                startActivity(intent);
                break;
            case R.id.button_customer_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(CustomerInfo.this);
                dialog.setTitle("Warning!");
                dialog.setMessage("你确定要删除" + name.getText() + "吗？");
                //dialog.setCancelable(false);
                dialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(DatabaseContract.CutormerTable.TABLE_NAME, DatabaseContract.CutormerTable.COLUMN_NAME_NAME + "= ?" +" AND "
                                + DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME + "= ?",new String[]{theName,theWeChat});
                        finish();

                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();
                break;
            default:
                break;


        }

    }

    private void drawCustomer(){

        Cursor cursor = db.query(DatabaseContract.CutormerTable.TABLE_NAME,null,DatabaseContract.CutormerTable._ID + "= ?",
                new String[]{String.valueOf(customer_ID)},null,null,null);


        cursor.moveToNext();
        name.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_NAME)));
        tel.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_TEL)));
        address.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS)));
        wechatName.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME)));
        wechatID.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATID)));
        ID.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ID)));
        memo.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_MEMO)));

        cursor.close();



    }




}
