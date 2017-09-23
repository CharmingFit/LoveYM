package com.example.steven.loveym;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;

/**
 * Created by STEVEN on 2017/3/26.
 */

public class ItemQuantityDialog extends Dialog{


    public interface MyDialogEventListener{
        public void myDialogEvent(int back_quantity, double back_totalCost, int flag);
    }

    private MyDialogEventListener onMyDialogEventListener;

    EditText quantity;
    EditText total_cost;
    int pos;
    int inflag;
    //String[] costList;
    Context mContext;

    public ItemQuantityDialog(Context context,MyDialogEventListener onMyDialogEventListener, int inflag) { //尝试不传入theme的构造函数
        super(context,R.style.dialog);
        mContext = context;
        this.onMyDialogEventListener = onMyDialogEventListener;
        this.inflag = inflag;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = getLayoutInflater(); //两种写法无差别
        View layout = inflater.inflate(R.layout.item_quantity_add_dialog_custom, null);
        this.setContentView(layout);
        Button confirm_button = (Button) findViewById(R.id.dialog_confirm); //特别重要，在此不可以直接findviewbyid
        Button cancel_button = (Button) findViewById(R.id.dialog_cancel);
        quantity = (EditText) findViewById(R.id.quantity_alert);
        total_cost = (EditText) findViewById(R.id.total_cost_alert);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_cost);


        if (inflag == R.id.button_more_item || inflag == R.id.button_sell_item) {

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    pos = 0;

                }
            });


            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMyDialogEventListener.myDialogEvent(Integer.valueOf(quantity.getText().toString()),
                            Double.valueOf(total_cost.getText().toString()), pos);
                    dismiss();
                    if(inflag == R.id.button_sell_item) {
                        Tools.finishAll();
                        Tools.activities.clear();
                    }

                }
            });



        }else if(inflag == R.id.button_less_item){

            TableRow row = (TableRow) findViewById(R.id.alert_row);
            row.setVisibility(View.GONE);
            confirm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("Warning!");
                    dialog.setMessage("你确定要减少"+quantity.getText().toString()+"个吗？");
                    //dialog.setCancelable(false);
                    dialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onMyDialogEventListener.myDialogEvent(Integer.valueOf(quantity.getText().toString()),0,0);
                            dismiss();

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

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });






    }
}
