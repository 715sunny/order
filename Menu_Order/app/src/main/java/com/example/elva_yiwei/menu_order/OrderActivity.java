package com.example.elva_yiwei.menu_order;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;


public class OrderActivity extends TabActivity {
    private TabHost mTabHost;
    protected ArrayList<String> b;
    protected ArrayList<String> bs;
    public void setb(ArrayList<String> b){
        OrderActivity.this.b=b;
    }
    public  ArrayList<String>  getb(){
        return OrderActivity.this.b;
    }
    public void setbs(ArrayList<String> b){
        OrderActivity.this.bs=b;
    }
    public  ArrayList<String>  getbs(){
        return OrderActivity.this.bs;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mTabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;
        intent= new Intent(this,OrderList.class);
        spec= mTabHost.newTabSpec("orderlist1")
                .setIndicator("未完成订单")
                .setContent(intent);
        mTabHost.addTab(spec);
        intent= new Intent(this,orderlist2.class);
        spec= mTabHost.newTabSpec("orderlist2")
                .setIndicator("已完成订单")
                .setContent(intent);
        mTabHost.addTab(spec);

    }

}
