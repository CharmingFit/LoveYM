package com.example.steven.loveym;

/**
 * Created by STEVEN on 2017/4/23.
 */

public class DeliveryCompanyOneLine {

    private int companyID;
    private String companyName;
    private double deliveryRate;
    private String companyMemo;
    private int comPosition;

    public int getComPosition() {
        return comPosition;
    }

    public void setComPosition(int comPosition) {
        this.comPosition = comPosition;
    }

    public double getDeliveryRate() {
        return deliveryRate;
    }

    public void setDeliveryRate(double deliveryRate) {
        this.deliveryRate = deliveryRate;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getCompanyMemo() {
        return companyMemo;
    }

    public void setCompanyMemo(String companyMemo) {
        this.companyMemo = companyMemo;
    }
}
