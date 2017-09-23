package com.example.steven.loveym;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class NewOrder extends AppCompatActivity {

    Button button_customer_name;
    TextView tel;
    TextView address;
    TextView customer_name;
    TextView total_Weight;
    TextView total_revenue;
    TextView total_cost;
    TextView total_profit;
    EditText memo;
    Button button_add_item;
    Button button_confirm;
    ListView listView;
    int item_position;
    public static ArrayList<ItemOneLine> itemList = null;
    DbHelper dbHelper;
    SQLiteDatabase db;
    int customerId;
    double totalRevenue;
    double totalCost;
    double totalWeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        button_customer_name = (Button) findViewById(R.id.button_new_order_name);
        tel = (TextView) findViewById(R.id.customer_tel);
        address = (TextView) findViewById(R.id.customer_address);
        customer_name = (TextView)findViewById(R.id.customer_name);
        listView = (ListView) findViewById(R.id.order_new_listview);
        button_add_item = (Button)findViewById(R.id.button_add_item);
        total_Weight = (TextView) findViewById(R.id.total_weight);
        total_revenue = (TextView) findViewById(R.id.total_amount);
        total_cost = (TextView)findViewById(R.id.total_cost);
        total_profit =(TextView)findViewById(R.id.total_profit);
        memo = (EditText)findViewById(R.id.order_memo);
        button_confirm = (Button)findViewById(R.id.button_new_order_confirm);
        itemList = new ArrayList<>();
        CustomerActivity.savedPerson = null;
        dbHelper = new DbHelper(NewOrder.this);
        db = dbHelper.getReadableDatabase();



        button_customer_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrder.this,CustomerActivity.class);
                intent.putExtra("flag","flag");
                startActivity(intent);


            }
        });

        button_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrder.this,InventoryAll.class);
                intent.putExtra("flag","flag");
                startActivity(intent);
            }
        });


        button_confirm.setEnabled(false);


        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (CustomerActivity.savedPerson != null) {
            customerId = CustomerActivity.savedPerson.getCustomer_ID();
            customer_name.setText(CustomerActivity.savedPerson.getName());
            tel.setText(String.valueOf(CustomerActivity.savedPerson.getTelephone()));
            address.setText(CustomerActivity.savedPerson.getAddress());

        }
        showItem();

        //OrderNewListAdapter adapter = new OrderNewListAdapter(NewOrder.this,R.layout.new_order_list,itemList);
        //listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_position = position;

                AlertDialog.Builder alert = new AlertDialog.Builder(NewOrder.this);
                alert.setTitle("Warning!");
                alert.setMessage("你确定要删除该商品吗？");
                alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ItemOneLine deleted_item = itemList.remove(item_position);
                        int deleted_item_ID = deleted_item.getItem_ID();
                        int deleted_item_quantity = deleted_item.getItem_quantity();


                        Cursor cursor = db.query(DatabaseContract.ProductTable.TABLE_NAME,
                                new String[]{DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY},
                                DatabaseContract.ProductTable._ID + " = ?",new String[]{String.valueOf(deleted_item_ID)},
                                null,null,null,null);
                        cursor.moveToNext();
                        int original_quantity =
                                cursor.getInt(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY));
                        cursor.close();

                        original_quantity = original_quantity + deleted_item_quantity;
                        ContentValues values = new ContentValues();
                        values.put(DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY,original_quantity);
                        db.update(DatabaseContract.ProductTable.TABLE_NAME,values,
                                DatabaseContract.ProductTable._ID + " = ?",new String[]{String.valueOf(deleted_item_ID)});

                        showItem();
                        calculateStatic();
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.show();




            }
        });
        calculateStatic();

        if (CustomerActivity.savedPerson != null && !itemList.isEmpty()){
            button_confirm.setEnabled(true);
        }



    }

    private void calculateStatic(){


            totalWeight = 0;
            totalRevenue = 0;
            totalCost = 0;
            double totalProfitRatio = 0;
        if(!itemList.isEmpty()) {
            for (ItemOneLine itemOneLine : itemList) {
                totalWeight = totalWeight + itemOneLine.getItem_weight() * itemOneLine.getItem_quantity();
                totalRevenue = totalRevenue + itemOneLine.getItem_average_price() * itemOneLine.getItem_quantity();
                totalCost = totalCost + itemOneLine.getItem_cost() * itemOneLine.getItem_quantity();

            }

            totalProfitRatio = (totalRevenue - totalCost) / totalCost;
        }
            total_Weight.setText(String.valueOf(Tools.formatDouble1(totalWeight)));
            total_revenue.setText(String.valueOf(Tools.formatDouble1(totalRevenue)));
            total_cost.setText(String.valueOf(Tools.formatDouble1(totalCost)));
            total_profit.setText(String.valueOf(Tools.formatDouble1(totalProfitRatio)*100) + "%");

    }


    private void showItem(){

        OrderNewListAdapter adapter = new OrderNewListAdapter(NewOrder.this,R.layout.new_order_list,itemList);
        listView.setAdapter(adapter);




    }


    private ContentValues loadValues(ContentValues values){

        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_NAME,customerId);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_DATA,makeTransaction());
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_REVENUE,totalRevenue);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_Cost,totalCost);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_WEIGHT,totalWeight);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COST,0);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TRACE_ID,"unknown");
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TRANSPORT_COMPANY,0);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_PAID,0);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_DELIVERY_STATUS,0);
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_STATUS,0);
        Date today = new Date();
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_TIME,today.getTime());
        values.put(DatabaseContract.TransactionTable.COLUMN_NAME_MEMO,memo.getText().toString());
        return values;
    }



    private String makeTransaction(){
        StringBuilder stringBuilder = new StringBuilder();

        for (ItemOneLine item : itemList){
            stringBuilder.append(item.getItem_ID());
            stringBuilder.append("/");
            stringBuilder.append(item.getItem_quantity());
            stringBuilder.append("/");
            stringBuilder.append(item.getItem_average_price());
            stringBuilder.append("#");
        }

        return stringBuilder.toString();
    }


    private void saveTransaction(){
        ContentValues values = new ContentValues();
       // values = loadValues(values);
        db.insert(DatabaseContract.TransactionTable.TABLE_NAME,null,loadValues(values));

    }






}




