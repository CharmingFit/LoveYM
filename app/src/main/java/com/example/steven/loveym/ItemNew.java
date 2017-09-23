package com.example.steven.loveym;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ItemNew extends AppCompatActivity {

    EditText name_item;
    Spinner category_item;
    EditText weight_item;
    EditText memo_item;
    EditText price_item;
    String category;
    String[] item_category;
    Button confirm_button;
    DbHelper helper;
    SQLiteDatabase db;
    int i = 0;
    double originalCost =0;
    int originalQuantity = 0;
    Intent inIntent;
    int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_new);
        item_category = getResources().getStringArray(R.array.item_categories);//重要：获得资源里的category数组
        name_item = (EditText) findViewById(R.id.name_new_item);
        category_item = (Spinner) findViewById(R.id.category_new_item);
        weight_item = (EditText) findViewById(R.id.weight_new_item);
        memo_item = (EditText) findViewById(R.id.memo_new_item);
        confirm_button =(Button)findViewById(R.id.button_new_item_confirm);
        price_item = (EditText)findViewById(R.id.price_new_item);
        helper =new DbHelper(this);
        db = helper.getReadableDatabase();

        inIntent = getIntent();
        if(inIntent.hasExtra("selectedID")){
            itemID = inIntent.getIntExtra("selectedID",0);
            if (itemID != 0) {
                Cursor cursor = db.query(DatabaseContract.ProductTable.TABLE_NAME, null,
                        DatabaseContract.ProductTable._ID + " = ?", new String[]{String.valueOf(itemID)}, null, null, null, null);

                cursor.moveToNext();
                name_item.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_NAME)));
                String theCategory = cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_CATEGORY));

                for (String s:item_category){
                    if(theCategory.equals(s)){
                        break;
                    }
                    i++;
                }
                category_item.setSelection(i);
                weight_item.setText(String.valueOf(cursor.
                        getDouble(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_WEIGHT))));//检查
                price_item.setText(String.valueOf(cursor.
                        getDouble(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_NORMAL_PRICE))));
                memo_item.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_MEMO)));
                originalCost = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE));
                originalQuantity = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY));

            cursor.close();


            }

        }


        category_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = item_category[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = item_category[i];

            }
        });




        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
                //Intent intent = new Intent(ItemNew.this,InventoryAll.class);
               // startActivity(intent);
                finish();
            }
        });



    }

    protected void addNewItem(){


        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_NAME,name_item.getText().toString());
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY,originalQuantity);
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE,originalCost);
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_CATEGORY,category);
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_WEIGHT,Double.parseDouble(weight_item.getText().toString()));
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_NORMAL_PRICE,Double.parseDouble(price_item.getText().toString()));
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_MEMO,memo_item.getText().toString());

        if (!inIntent.hasExtra("selectedID")) {
            db.insert(DatabaseContract.ProductTable.TABLE_NAME, null, values);
        }else{
            db.update(DatabaseContract.ProductTable.TABLE_NAME,
                    values,DatabaseContract.ProductTable._ID + " = ?", new String[]{String.valueOf(itemID)});
        }
        values.clear();


    }








}
