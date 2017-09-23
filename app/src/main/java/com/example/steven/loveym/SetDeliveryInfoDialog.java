package com.example.steven.loveym;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by STEVEN on 2017/4/24.
 */

public class SetDeliveryInfoDialog extends Dialog {

    public interface DeliveryInfoListener{
        void event(DeliveryCompanyOneLine company, double totalweight,double deliveryCost, String traceId);
    }

    private DeliveryInfoListener infoListener;
    private Context mContext;
    private int resourceId;
    private double weight;
    ArrayList<DeliveryCompanyOneLine> list;
    DeliveryCompanyOneLine selectedCompany;
    EditText costText;
    EditText weightText;
    EditText traceIdText;
    private String deliveryNumber;
    private Double deliveryCost;


    public SetDeliveryInfoDialog(Context context, int layout, double weight, String deliveryNumber, double deliveryCost,
                                 DeliveryCompanyOneLine deliveryCompany, ArrayList<DeliveryCompanyOneLine> list,
            DeliveryInfoListener listener){

        super(context,R.style.dialog);
        this.mContext = context;
        this.infoListener = listener;
        this.resourceId = layout;
        this.weight = weight;
        this.list = list;
        this.deliveryNumber = deliveryNumber;
        this.deliveryCost = deliveryCost;
        this.selectedCompany = deliveryCompany;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(resourceId,null);
        this.setContentView(layout);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_delivery_company);
        weightText = (EditText)findViewById(R.id.weight_alert);
        costText = (EditText)findViewById(R.id.delivery_cost_alert);
        traceIdText = (EditText) findViewById(R.id.delivery_number_alert);
        Button confirm = (Button) findViewById(R.id.dialog_confirm);
        Button cancel = (Button) findViewById(R.id.dialog_cancel);



       // spinner.setPrompt("请选择快递公司");

        OnlineEntryAdapter adapter = new OnlineEntryAdapter(mContext,R.layout.online,list);
        spinner.setAdapter(adapter);

        if (selectedCompany != null){
            spinner.setSelection(selectedCompany.getComPosition());
        }




        //当选择好公司后，自动用单价x重量，并写在相应位置


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCompany = list.get(position);

                if (deliveryCost == 0) {

                    if (weight < 1000) {
                        costText.setText(String.valueOf(selectedCompany.getDeliveryRate()));
                    } else {
                        costText.setText(String.valueOf(Tools.formatDouble1(weight * selectedCompany.getDeliveryRate()) / 1000));
                    }
                }else{
                    costText.setText(String.valueOf(deliveryCost));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (selectedCompany == null) {
                    selectedCompany = list.get(0);
                }
            }
        });

        weightText.setText(String.valueOf(weight));
        traceIdText.setText(deliveryNumber);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoListener.event(selectedCompany,Double.parseDouble(weightText.getText().toString()),
                        Double.parseDouble(costText.getText().toString()),traceIdText.getText().toString());
                dismiss();
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
