package com.example.steven.loveym;

import java.util.ArrayList;

/**
 * Created by STEVEN on 2017/4/9.
 */

public class TransactionOneLine {


    private int transactionID;
    private int paidImage;
    private int DeliveredImage;
    private  long trancationTime;

    private int paidStatus;
    private String deliveryNumber;
    private int deliveryStatus;


    private String memo;
    private PersonOneLine person;
    private ArrayList<ItemOneLine> items;
    private DeliveryCompanyOneLine deliveryCompany;


    private double total_revenue;
    private double total_cost;
    private double total_weight;
    private double delivery_cost;


    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public DeliveryCompanyOneLine getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(DeliveryCompanyOneLine deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public double getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(double delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public long getTrancationTime() {
        return trancationTime;
    }

    public void setTrancationTime(long trancationTime) {
        this.trancationTime = trancationTime;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getPaidImage() {
        return paidImage;
    }

    public void setPaidImage(int paidImage) {
        this.paidImage = paidImage;
    }

    public int getDeliveredImage() {
        return DeliveredImage;
    }

    public void setDeliveredImage(int deliveredImage) {
        DeliveredImage = deliveredImage;
    }

    public PersonOneLine getPerson() {
        return person;
    }

    public void setPerson(PersonOneLine person) {
        this.person = person;
    }

    public ArrayList<ItemOneLine> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemOneLine> items) {
        this.items = items;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public double getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(double total_weight) {
        this.total_weight = total_weight;
    }
}
