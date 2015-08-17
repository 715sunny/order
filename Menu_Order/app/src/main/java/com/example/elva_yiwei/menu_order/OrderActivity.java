package com.example.elva_yiwei.menu_order;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Map;


public class OrderActivity extends TabActivity {
    private TabHost mTabHost;
    protected ArrayList<Map<String, Object>> b;
    protected ArrayList<Map<String, Object>> bs;
    public void setb(ArrayList<Map<String, Object>> b){
        OrderActivity.this.b=b;
    }
    public  ArrayList<Map<String, Object>>  getb(){
        return OrderActivity.this.b;
    }
    public void setbs(ArrayList<Map<String, Object>> b){
        OrderActivity.this.bs=b;
    }
    public ArrayList<Map<String, Object>> getbs(){
        return OrderActivity.this.bs;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_ordergrid);
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
