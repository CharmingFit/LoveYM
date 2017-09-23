package com.example.steven.loveym;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by STEVEN on 2017/4/23.
 */

public class NewDeliveryCompanyDialog extends Dialog {

    public interface EventListener{
        void event(String backName, double backRate, String backMemo);
    }


    private EventListener onEventListener;
    private int resourceId;
    private Context mContext;
    private EditText companyName;
    private EditText companyRate;
    private EditText companyMemo;
    private DeliveryCompanyOneLine company;


    public NewDeliveryCompanyDialog(Context context, int layoutId,EventListener eventListener,Object obj) {
        super(context, R.style.dialog);
        this.onEventListener = eventListener;
        this.resourceId = layoutId;
        this.mContext = context;
        if (obj != null) {
            this.company = (DeliveryCompanyOneLine) obj;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(resourceId,null);
        this.setContentView(layout);
        companyName = (EditText)findViewById(R.id.company_name_dialog);
        companyRate = (EditText)findViewById(R.id.delivery_company_rate_dialog);
        companyMemo = (EditText) findViewById(R.id.delivery_company_memo_dialog);
        Button confirmButton = (Button) findViewById(R.id.dialog_confirm);
        Button cancelButton = (Button) findViewById(R.id.dialog_cancel);


        if (company != null){
            companyName.setText(company.getCompanyName());
            companyRate.setText(String.valueOf(company.getDeliveryRate()));
            companyMemo.setText(company.getCompanyMemo());

        }



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (companyMemo.getText().toString().isEmpty()){
                    onEventListener.event(companyName.getText().toString(),
                            Double.parseDouble(companyRate.getText().toString()),"Unknown");
                }else {
                    onEventListener.event(companyName.getText().toString(),
                            Double.parseDouble(companyRate.getText().toString()), companyMemo.getText().toString());

                }
                dismiss();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });






    }
}
