package com.example.elva_yiwei.menu_order;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class Order extends TabActivity

{
    public  static TabHost myTabHost;
    public static ArrayList<String> array=new ArrayList<String>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        myTabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;
        intent = new Intent(this,FastOrder.class);
        spec= myTabHost.newTabSpec("FastOrder").setIndicator("快速点单").setContent(intent);
        myTabHost.addTab(spec);
        intent = new Intent(this,allorder.class);
        spec= myTabHost.newTabSpec("AllOrder").setIndicator("全部点单").setContent(intent);
        myTabHost.addTab(spec);

        myTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if(allorder.listview!=null&&allorder.adapter!=null&&tabId.equals("AllOrder")){
                    allorder.listview.setAdapter(allorder.adapter);
                }else if(FastOrder.listview!=null&&FastOrder.adapter!=null&&tabId.equals("FastOrder")){
                    FastOrder.listview.setAdapter(FastOrder.adapter);
                }
            }
        });
    }


}