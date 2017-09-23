package com.example.steven.loveym;

/**
 * Created by STEVEN on 2017/3/12.
 * 单行对象
 */

public class PersonOneLine {
    private int customer_ID;
    private String name;
    private String wechatName;
    private int Telephone;
    private String address;

    public PersonOneLine (String name){
        this.name = name;
    }

    public PersonOneLine() {
    }

    public void setName (String name){
        this.name =name;

    }

    public String getName(){
        return name;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }


    public int getTelephone() {
        return Telephone;
    }

    public void setTelephone(int telephone) {
        this.Telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }
}
