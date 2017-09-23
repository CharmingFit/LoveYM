package com.example.steven.loveym;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class InventoryAll extends AppCompatActivity {


    ArrayList<ItemOneLine> itemList;
    ListView listView;
    Button button_new_item;
    DbHelper dbHelper;
    SQLiteDatabase db;
    SearchView searchView;
    ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_all);


        listView =(ListView) findViewById(R.id.inventory_list);
        button_new_item = (Button) findViewById(R.id.button_new_item);
        searchView =(SearchView)findViewById(R.id.title_searcher);


        dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();

        itemList = new ArrayList<>();
        adapter = new ItemListAdapter(InventoryAll.this,R.layout.item_one_line,itemList);

        button_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryAll.this,ItemNew.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllItems(itemList);

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemOneLine selectedItem = adapter.getResultList().get(position);
                Intent inIntent = getIntent();
                Intent intent = new Intent(InventoryAll.this,ItemSelected.class);
                if(inIntent.hasExtra("flag")){
                    intent.putExtra("flag","flag");
                    intent.putExtra("selectedID",selectedItem.getItem_ID());
                    startActivity(intent);
                    Tools.addActivity(InventoryAll.this);
                }else {
                    intent.putExtra("selectedID", selectedItem.getItem_ID());
                    startActivity(intent);
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }else{
                    listView.setFilterText(newText);
                }
                return false;
            }
        });
    }

    private void loadAllItems (ArrayList<ItemOneLine> list){
        list.clear();
        Cursor cursor = db.query(DatabaseContract.ProductTable.TABLE_NAME,null,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            ItemOneLine aItem = new ItemOneLine();
            aItem.setItem_ID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.ProductTable._ID)));
            aItem.setItem_Name(cursor.getString(cursor.getColumnIndex(DatabaseContract.ProductTable.COLUMN_NAME_NAME)));
            aItem.setItem_average_price(cursor.getDouble(cursor.getColumnIndex
                    (DatabaseContract.ProductTable.COLUMN_NAME_AVERAGE_PRICE)));
            aItem.setItem_quantity(cursor.getInt(cursor.getColumnIndex
                    (DatabaseContract.ProductTable.COLUMN_NAME_QUANTITY)));
            list.add(aItem);

        }
        cursor.close();

    }













}
