package com.example.steven.loveym;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddCustomer extends Activity {
    DbHelper dbHelper = new DbHelper(this); //这里this可以么？？？？？？？ A：可以
    SQLiteDatabase db;
    EditText name;
    EditText tel;
    EditText address;
    EditText wechatName;
    EditText wechatID;
    EditText ID;
    EditText memo;
    Button confirm_button;
    int customerid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        db = dbHelper.getReadableDatabase();
        name = (EditText) findViewById(R.id.name_new_customer);
        tel = (EditText) findViewById(R.id.Tel_new_customer);
        address = (EditText) findViewById(R.id.address_new_customer);
        wechatName = (EditText) findViewById(R.id.wechat_name_new_customer);
        wechatID = (EditText) findViewById(R.id.wechat_id_new_customer);
        ID = (EditText) findViewById(R.id.id_new_customer);
        memo= (EditText) findViewById(R.id.memo_new_customer);
        confirm_button = (Button) findViewById(R.id.button_new_customer_confirm);

        //取出来时intent中所含的信息
        Intent inIntent = getIntent();
       // theName = inIntent.getStringExtra("selectedName");
       // theWeChat = inIntent.getStringExtra("selectedWechat");
        customerid = inIntent.getIntExtra("selectedid",0);


        //若来时的Intent没有这种信息，说明来自于new customer按钮
        if (!inIntent.hasExtra("selectedid")) {

            //设置标题
            TextView title = (TextView) findViewById(R.id.title_title);
            title.setText("New Customer");

            //此时不用编辑功能，将顶部编辑按钮不可见
            Button edit_button = (Button) findViewById(R.id.title_edit);
            edit_button.setVisibility(View.INVISIBLE);



            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String aName = addCustomer();
                    Intent intent = new Intent(AddCustomer.this, CustomerActivity.class);
                    intent.putExtra("name_new_customer", aName);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });


        }else { // 否则来自于view customer的edit按钮
            TextView title = (TextView) findViewById(R.id.title_title);
            title.setText("编辑");

            Button edit_button = (Button) findViewById(R.id.title_edit);
            edit_button.setVisibility(View.INVISIBLE);



            //查询语句，得到唯一customer信息，

            Cursor cursor = db.query(DatabaseContract.CutormerTable.TABLE_NAME,null,DatabaseContract.CutormerTable._ID + "=?",
                    new String[]{String.valueOf(customerid)},null,null,null);






            //因为只要唯一的一个customer所以只需要让cursor看一行就行，而不用循环
            cursor.moveToNext();
            name.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_NAME)));
            tel.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_TEL)));
            address.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS)));
            wechatName.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME)));
            wechatID.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATID)));
            ID.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ID)));
            memo.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_MEMO)));



            cursor.close();

            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCustomer();
                    finish();
                }
            });


        }
    }

    protected String addCustomer(){
        db.insert(DatabaseContract.CutormerTable.TABLE_NAME,null,putThemIn());
        return name.getText().toString();
    }

    private void updateCustomer(){
        db.update(DatabaseContract.CutormerTable.TABLE_NAME,putThemIn(),DatabaseContract.CutormerTable._ID + "=?",
                new String[]{String.valueOf(customerid)});
    }

    //将无聊的重复代码写一个方法
    private ContentValues putThemIn(){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_NAME,name.getText().toString());
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_TEL,Integer.parseInt(tel.getText().toString()));
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS,address.getText().toString());
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME,wechatName.getText().toString());
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATID,wechatID.getText().toString());
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_ID,Integer.parseInt(ID.getText().toString()));
        contentValues.put(DatabaseContract.CutormerTable.COLUMN_NAME_MEMO,memo.getText().toString());

        return contentValues;

    }




}
