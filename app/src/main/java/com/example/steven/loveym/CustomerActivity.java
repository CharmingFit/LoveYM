package com.example.steven.loveym;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity{

    private ArrayList<PersonOneLine> personList = new ArrayList<PersonOneLine>();
    private DbHelper dbHelper;
    SearchView search_button;
    ListView listView;
    PersonListAdapter adapter;
    public static PersonOneLine savedPerson = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        savedPerson = new PersonOneLine();

        //删除做上边的标题
       //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //设定标题
        TextView title = (TextView) findViewById(R.id.title_title);
        title.setText("Customers");
/*
        Button back_button = (Button) findViewById(R.id.title_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
*/
        //转到添加新用户界面
        Button newCustomer = (Button) findViewById(R.id.button_new_customer);
        newCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddCustomer.class);
                startActivityForResult(intent,1);
                personList.clear();


            }
        });

        search_button = (SearchView) findViewById(R.id.title_searcher);
        listView = (ListView) findViewById(R.id.cutomer_list);


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadCustomer();


        search_button.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);

                }else{
                    listView.clearTextFilter();

                }
                return false;
            }
        });









    }

    private void loadCustomer(){

        personList.clear();
        //加载数据库
        dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =db.query(DatabaseContract.CutormerTable.TABLE_NAME,null,null,null,null,null,null);

        while(cursor.moveToNext()){
            String thisName = cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_NAME));
            PersonOneLine somePerson = new PersonOneLine(thisName);
            somePerson.setWechatName(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_WECHATNAME)));
            somePerson.setTelephone(cursor.getInt(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_TEL)));
            somePerson.setAddress(cursor.getString(cursor.getColumnIndex(DatabaseContract.CutormerTable.COLUMN_NAME_ADDRESS)));
            somePerson.setCustomer_ID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.CutormerTable._ID)));
            personList.add(somePerson);

        }
        cursor.close();


        //添加LISTVIEW适配器
        adapter = new PersonListAdapter(CustomerActivity.this,R.layout.person_one_line,personList);

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //当点击某个item转到其详情
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//点击某顾客事件
                PersonOneLine selectedPerson = adapter.getResultList().get(position);

                //PersonOneLine selectedPerson = personList.get(position);
                Intent intent = new Intent(CustomerActivity.this,CustomerInfo.class);
                intent.putExtra("selectedName",selectedPerson.getName());
                intent.putExtra("selectedWechat",selectedPerson.getWechatName());
                intent.putExtra("selectedTel",selectedPerson.getTelephone());
                intent.putExtra("selectedAddress",selectedPerson.getAddress());
                intent.putExtra("selectedid",selectedPerson.getCustomer_ID());

                adapter.getResultList().clear();
                personList.clear();
                if (getIntent().hasExtra("flag") ){
                    savedPerson.setCustomer_ID(selectedPerson.getCustomer_ID());
                    savedPerson.setName(selectedPerson.getName());
                    savedPerson.setTelephone(selectedPerson.getTelephone());
                    savedPerson.setAddress(selectedPerson.getAddress());
                    finish();

                }else {

                    startActivity(intent);
                }

            }
        });



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String newName = data.getStringExtra("name_new_customer");
                    Toast.makeText(this,newName + "has been added",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }


    }
}
