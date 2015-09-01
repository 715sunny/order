package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;


public class allorder extends TabActivity {

    private Context context;
    private Activity activity;
    public static ListView listview;
    private OrderMenuDB orderMenuDB;
    private Cursor cursor;


    public static Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorder);
        TabHost tabHost = getTabHost();
        tabHost.setup();
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        cursor = orderMenuDB.fetchAllCategory();
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()) {
                tabHost.addTab(tabHost.newTabSpec("tab_"+cursor.getString(cursor.getColumnIndex("type")))
                        .setContent(new TabFactory(this))
                        .setIndicator(cursor.getString(cursor.getColumnIndex("name"))));
            }
        }

        tabHost.setCurrentTab(0);

        context = this;
        activity = this;

        cursor = orderMenuDB.fetchAllMenus();

        listview = Order.listview;

        adapter = new Adapter(this,Order.arrayView);

    }



}
