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
 */

public class TitleLayoutSearcher extends LinearLayout{

    public TitleLayoutSearcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.over_my_head_searcher, this);//将对应的layout布局放给这个class
        Button button_back = (Button) findViewById(R.id.title_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });


    }
}
