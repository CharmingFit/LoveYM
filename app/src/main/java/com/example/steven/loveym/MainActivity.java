package com.example.steven.loveym;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_customers = (Button) findViewById(R.id.customers_button);
        Button button_orders = (Button)findViewById(R.id.Orders_button) ;
        Button button_Inventory = (Button)findViewById(R.id.Inventory_button) ;
        Button button_Management = (Button) findViewById(R.id.Managemnt_button);

        //转到Customer界面
        button_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CustomerActivity.class);
                startActivity(intent);
            }
        });


        button_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OrderHome.class);
                startActivity(intent);
            }
        });

        button_Inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InventoryAll.class);
                startActivity(intent);
            }
        });

        button_Management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ManagementHome.class);
                startActivity(intent);
            }
        });



    }
}
