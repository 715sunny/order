package com.example.elva_yiwei.menu_order;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class Order extends TabActivity

{
    private TabHost myTabHost;

    static int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);


        myTabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;


        Intent intent1= new Intent(this,FastOrder.class);
        spec= myTabHost.newTabSpec("FastOrder").setIndicator("快速点单").setContent(intent1);
        myTabHost.addTab(spec);




        Intent intent2= new Intent(this,allorder.class);
        spec= myTabHost.newTabSpec("AllOrder").setIndicator("全部点单").setContent(intent2);
        myTabHost.addTab(spec);




    }

}