package com.example.steven.loveym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_home);

        Button delivery_companies = (Button) findViewById(R.id.delivery_company_button);
        delivery_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagementHome.this,DeliveryCompanies.class);
                startActivity(intent);
            }
        });

    }
}
