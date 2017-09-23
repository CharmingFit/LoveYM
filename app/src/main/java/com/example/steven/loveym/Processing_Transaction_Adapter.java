package com.example.steven.loveym;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by STEVEN on 2017/4/14.
 */

public class Processing_Transaction_Adapter extends BaseExpandableListAdapter {


    private Context mContext;
    private int parent_resource;
    private int child_resource;
    private ArrayList<TransactionOneLine> inList;
    private ArrayList<DeliveryCompanyOneLine> comList;
    private double theWeight;
    private TransactionOneLine theTransaction;
    private String theDeliveryNumber;
    private double theDeliveryCost;
    private RadioGroup radioGroup;
    private RadioGroup radioGroup_delivery;



    //private DbHelper dbHelper;
    //private SQLiteDatabase db;

    public interface AdapterListener{
        void changedEvent(TransactionOneLine backTransaction, int eventId);
    }


    private AdapterListener adapterListener;



    public Processing_Transaction_Adapter(Context context,int parent_resource, int child_resource,
                                          ArrayList<TransactionOneLine> objects,
                                          ArrayList<DeliveryCompanyOneLine> comList,AdapterListener adapterListener){

        this.mContext = context;
        this.parent_resource = parent_resource;
        this.child_resource = child_resource;
        this.inList = objects;
        this.comList = comList;
        this.adapterListener = adapterListener;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TransactionOneLine transaction = getGroup(groupPosition);

        //if (convertView == null) {//取消此if，即取消缓存，可解决group view上飞机图标问题，但降低优化

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //两种写法无差别
            convertView = inflater.inflate(parent_resource, null);
        //}

        TextView transactionId = (TextView) convertView.findViewById(R.id.transactionID);
        TextView customerName = (TextView) convertView.findViewById(R.id.customer_name);
        ImageView paidImage = (ImageView) convertView.findViewById(R.id.paid_image);
        ImageView deliveryImage = (ImageView) convertView.findViewById(R.id.dilivery_image);
        TextView transactionTime = (TextView) convertView.findViewById(R.id.transaction_date);
        TextView profitratio = (TextView) convertView.findViewById(R.id.profitRatio);
        TextView profitratio1 = (TextView) convertView.findViewById(R.id.profitRatioText);
        profitratio.setVisibility(View.GONE);
        profitratio1.setVisibility(View.GONE);

        transactionId.setText(String.valueOf(transaction.getTransactionID()));
        customerName.setText(transaction.getPerson().getName());


        if (transaction.getPaidStatus()!= 0){
            paidImage.setImageResource(R.drawable.paid_sign);
        }


        if (transaction.getDeliveryStatus()!= 0){
            deliveryImage.setImageResource(R.drawable.delivered_sign);
        }

        transactionTime.setText(showTime(transaction.getTrancationTime()));


        return convertView;


    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        TransactionOneLine transaction = getChild(groupPosition,childPosition);
        theTransaction = transaction;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(child_resource, null);

        }

        TextView customerName = (TextView) convertView.findViewById(R.id.customer_name);
        TextView memo = (TextView)convertView.findViewById(R.id.order_memo);
        TextView tele =(TextView) convertView.findViewById(R.id.customer_tel);
        TextView address = (TextView) convertView.findViewById(R.id.customer_address);
        TextView total_revenue = (TextView)convertView.findViewById(R.id.total_amount);
        TextView total_cost = (TextView)convertView.findViewById(R.id.total_cost);
        TextView total_weight = (TextView)convertView.findViewById(R.id.total_weight);
        TextView profit_ratio = (TextView)convertView.findViewById(R.id.total_profit);
        TextView delivery_Number = (TextView) convertView.findViewById(R.id.delivery_number);
        Button delivery_button = (Button) convertView.findViewById(R.id.button_delivery_number);
        Button save_button = (Button) convertView.findViewById(R.id.button_save);




        ListView listView = (ListView) convertView.findViewById(R.id.transaction_info_listview);
        radioGroup = (RadioGroup) convertView.findViewById(R.id.radio_group);

        RadioButton paid = (RadioButton) convertView.findViewById(R.id.transaction_paid_radio);
        RadioButton unpaid = (RadioButton) convertView.findViewById(R.id.transaction_unpaid_radio);



        radioGroup_delivery = (RadioGroup)convertView.findViewById(R.id.radio_group_delivery);
        RadioButton deliveredRadio = (RadioButton) convertView.findViewById(R.id.transaction_delivery_radio);
        RadioButton undeliveredRadio = (RadioButton) convertView.findViewById(R.id.transaction_undelivery_radio);





        ArrayList<ItemOneLine> itemList = transaction.getItems();


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = itemList.size() * 42;

        listView.setLayoutParams(params);

        OrderNewListAdapter adapter = new OrderNewListAdapter(mContext,R.layout.new_order_list,itemList);

        listView.setAdapter(adapter);


        delivery_Number.setText(transaction.getDeliveryNumber());
        customerName.setText(transaction.getPerson().getName());
        tele.setText(String.valueOf(transaction.getPerson().getTelephone()));
        address.setText(transaction.getPerson().getAddress());
        memo.setText(transaction.getMemo());



        if (transaction.getPaidStatus()!= 0){
            radioGroup.check(paid.getId());
        }else{
            radioGroup.check(unpaid.getId());
        }


        if (transaction.getDeliveryStatus()!= 0){
            radioGroup_delivery.check(deliveredRadio.getId());
        }else{
            radioGroup_delivery.check(undeliveredRadio.getId());
        }




        theWeight = transaction.getTotal_weight();
        theDeliveryNumber = transaction.getDeliveryNumber();
        theDeliveryCost = transaction.getDelivery_cost();

        double totalCost = transaction.getTotal_cost()+ transaction.getDelivery_cost();

        if (transaction.getDelivery_cost() == 0){
            if (theWeight <1000){
                totalCost = transaction.getTotal_cost()+6;//以后改为乘以6
            }else{
                totalCost = transaction.getTotal_cost() + theWeight*6/1000;
            }
        }


        total_revenue.setText(String.valueOf(transaction.getTotal_revenue()));
        total_cost.setText(String.valueOf(totalCost));
        total_weight.setText(String.valueOf(transaction.getTotal_weight()));
        profit_ratio.setText(Tools.formatDouble1((transaction.getTotal_revenue()-totalCost)/totalCost)*100 + "%");


        delivery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   SetDeliveryInfoDialog dialog = new SetDeliveryInfoDialog(mContext,
                        R.layout.delivery_info_dialog, theWeight,theDeliveryNumber,theDeliveryCost,
           theTransaction.getDeliveryCompany(), comList, new SetDeliveryInfoDialog.DeliveryInfoListener() {
                    @Override
                    public void event(DeliveryCompanyOneLine company, double totalweight, double deliveryCost, String traceId) {
                        theTransaction.setDeliveryCompany(company);
                        theTransaction.setDeliveryNumber(traceId);
                        theTransaction.setTotal_weight(totalweight);
                        theTransaction.setDelivery_cost(deliveryCost);
                        adapterListener.changedEvent(theTransaction,1);

                    }
                });

                dialog.show();


            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.transaction_paid_radio){
                    theTransaction.setPaidStatus(1);
                }else{
                    theTransaction.setPaidStatus(0);
                }


                if (radioGroup_delivery.getCheckedRadioButtonId() == R.id.transaction_delivery_radio){
                    theTransaction.setDeliveryStatus(1);
                }else{
                    theTransaction.setDeliveryStatus(0);
                }

                adapterListener.changedEvent(theTransaction,2);
            }
        });


        return convertView;
    }







    @Override
    public TransactionOneLine getGroup(int groupPosition) {
        return this.inList.get(groupPosition);
    }

    @Override
    public TransactionOneLine getChild(int groupPosition, int childPosition) {
        return this.inList.get(groupPosition);
    }





    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getGroupCount() {
        return inList.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }


    private String showTime(long time){

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        Date transactionDate = new Date(time);
        return dateFormat.format(transactionDate);

    }






}
