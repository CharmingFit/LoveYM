package com.example.steven.loveym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home);

        Button new_order = (Button)findViewById(R.id.button_order_new);
        new_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHome.this,NewOrder.class);
                startActivity(intent);
            }
        });

        Button processing_order = (Button)findViewById(R.id.button_order_processing);
        processing_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHome.this,OrderProcessing.class);
                startActivity(intent);
            }
        });

        Button finished_order = (Button)findViewById(R.id.button_order_finished);
        finished_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderHome.this,OrderFinished.class);
                startActivity(intent);
            }
        });


    }
}
