package com.example.steven.loveym;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by STEVEN on 2017/3/12.
 * 用于实现带EDIT的标题栏，以便用于各个不同的Activity中
 */

public class TitleLayout extends LinearLayout{
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.over_my_head, this);//将对应的layout布局放给这个class
        Button button_back = (Button) findViewById(R.id.title_back);
        Button button_edit = (Button) findViewById(R.id.title_edit);
        button_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

        button_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something hahhahaha
            }
        });








    }
}
