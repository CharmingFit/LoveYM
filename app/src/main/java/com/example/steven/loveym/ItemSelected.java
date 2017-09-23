package com.example.steven.loveym;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.math.BigDecimal;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ItemSelected extends AppCompatActivity {

    TextView name;
    TextView category;
    TextView weightU;
    TextView price;
    TextView quantity;
    TextView costU;
    TextView memo;
    Button more_items;
    Button less_items;
    int quantity_original;
    double average_cost_original;

    DbHelper dbHelper;
    SQLiteDatabase db;
    int selectedID;
    Button edit_button;
    Button sell_item_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);
        name = (TextView) findViewById(R.id.item_name);
        category = (TextView) findViewById(R.id.item_category);
        weightU =(TextView) findViewById(R.id.item_weight);
        price = (TextView) findViewById(R.id.item_price);
        quantity = (TextView) findViewById(R.id.item_quantity);
        costU = (TextView) findViewById(R.id.item_cost);
        memo = (TextView)findViewById(R.id.item_memo);
        more_items = (Button)findViewById(R.id.button_more_item);
        less_items = (Button)findViewById(R.id.button_less_item);
        sell_item_button = (Button)findViewById(R.id.button_sell_item);
        edit_button = (Button)findViewById(R.id.title_edit);
        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        Button delete_button = (Button)findViewById(R.id.item_delete_button);

        Intent intent = getIntent();
        selectedID = intent.getIntExtra("selectedID",0);


        more_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemQuantityDialog dialog = new ItemQuantityDialog(ItemSelected.this, new ItemQuantityDialog.MyDialogEventListener() {
                    @Override
                    public void myDialogEvent(int back_quantity, double back_totalCost, int flag) {
                        if (flag ==1) {//1为总价，0为单价
                            average_cost_original = (average_cost_original * quantity_original + back_totalCost) / (quantity_original + back_quantity);

                        }else{
                            average_cost_original = (average_cost_original * quantity_original + back_totalCost * back_quantity) / (quantity_original + back_quantity);
                        }

                        quantity_original = quantity_original + back_quantity;
                        quantity.setText(String.valueOf(quantity_original));
                        costU.setText(String.valueOf(Tools.formatDouble1(average_cost_original)));
                        saveChange(quantity_original,average_cost_original,selectedID );

                    }
                }, R.id.button_more_item);
                dialog.show();
            }
        });

        less_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemQuantityDialog dialog = new ItemQuantityDialog(ItemSelected.this, new ItemQuantityDialog.MyDialogEventListener() {
                    @Override
                    public void myDialogEvent(int back_quantity, double back_totalCost, int flag) {
                        quantity_original = quantity_original - back_quantity;
                        quantity.setText(String.valueOf(quantity_original));
                        costU.setText(String.valueOf(Tools.formatDouble1(average_cost_original)));
                        saveChange(quantity_original,average_cost_original,selectedID );
                    }
                },R.id.button_less_item);
                dialog.show();
            }
        });

        Intent inIntent = getIntent();
        if (inIntent.hasExtra("flag")){
            sell_item_button.setVisibility(View.VISIBLE);
        }

        sell_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addActivity(ItemSelected.this);
                ItemQuantityDialog dialog = new ItemQuantityDialog(ItemSelected.this, new ItemQuantityDialog.MyDialogEventListener() {
                    @Override
                    public void myDialogEvent(int back_quantity, double back_totalCost, int flag) {
                        double price;
                        double weight = Double.parseDouble( weightU.getText().toString());

                        if (flag ==1) {//1为总价，0为单价
                            price = back_totalCost / back_quantity;

                        }else{
                            price = back_totalCost;
                        }

                        ItemOneLine addItem = new ItemOneLine();
                        addItem.setItem_ID(selectedID);
                        addItem.setItem_Name(name.getText().toString());
                        addItem.setItem_quantity(back_quantity);
                        addItem.setItem_average_price(price);
                        addItem.setItem_weight(weight);
                        addItem.setItem_cost(average_cost_original);
                        NewOrder.itemList.add(addItem);
                        quantity_original = quantity_original - back_quantity;
                        saveChange(quantity_original,average_cost_original,selectedID );

                    }


                },R.id.button_sell_item);
                dialog.show();


            }
        });



















        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemSelected.this,ItemNew.class);
                intent.putExtra("selectedID",selectedID);
                startActivity(intent);

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                AlertDialog.Builder dialog = new AlertDialog.Builder(ItemSelected.this);
                dialog.setTitle("Warning!");
                dialog.setMessage("你确定要删除" + name.getText() + "吗？");
                //dialog.setCancelable(false);
                dialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(selectedID);
                        finish();

                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        showItem(selectedID);
    }

    private void showItem(int id){

        Cursor cursor = db.query(DatabaseContract.ProductTable.TABLE_NAME,null,
                DatabaseContract.ProductTable._ID + " = ?",new String[]{String.valueOf(id)},null,null,null,null);

        cursor.moveToNext();

        name.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_NAME)));
        category.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_CATEGORY)));
        weightU.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_WEIGHT)));
        price.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_NORMAL_PRICE)));
        quantity.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY)));
        costU.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE)));
        memo.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_MEMO)));
        quantity_original = cursor.getInt(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY));
        average_cost_original = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE));
        cursor.close();



    }
/*
    public double formatDouble1(double d) {
        return (double)Math.round(d*100)/100;
    }
*/
    protected void saveChange(int changed_quantity, double changed_cost, int id){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY,changed_quantity);
        values.put(DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE,changed_cost);
        db.update(DatabaseContract.ProductTable.TABLE_NAME,values,
                DatabaseContract.ProductTable._ID + " = ?",new String[]{String.valueOf(id)});



    }


    private void deleteItem(int id){
        db.delete(DatabaseContract.ProductTable.TABLE_NAME,
                DatabaseContract.ProductTable._ID + " = ?",new String[]{String.valueOf(id)});



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
