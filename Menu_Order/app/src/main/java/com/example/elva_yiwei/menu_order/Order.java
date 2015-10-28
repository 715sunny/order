package com.example.elva_yiwei.menu_order;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Order extends TabActivity

{
    public  static TabHost myTabHost;
    public static ArrayList<String> array=new ArrayList<String>() ;
    public static ListView listview;
    static int flag;
    String cellphone = null;
    public static ArrayList<String> arrayView = new ArrayList();
    public static HashMap<String,Double>MenuAprice = new HashMap<String,Double>();
    private OrderMenuDB orderMenuDB;
    private Cursor cursor;
    private static TextView totalpriceView;
    private static TextView taxView;
    private static TextView priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        cellphone = intent.getStringExtra("cellphone");
        listview = (ListView) findViewById(R.id.listView2);
        totalpriceView=(TextView)findViewById(R.id.totalprices);
        taxView = (TextView)findViewById(R.id.taxprices);
        priceView = (TextView)findViewById(R.id.prices);
        myTabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        cursor = orderMenuDB.fetchAllMenus();
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                MenuAprice.put(cursor.getString(cursor.getColumnIndex("name")), Double.valueOf(cursor.getString(cursor.getColumnIndex("price"))));
            }
        }

        Intent intent1= new Intent(this,FastOrder.class);
        spec= myTabHost.newTabSpec("快速点单").setIndicator("快速点单").setContent(intent1);
        myTabHost.addTab(spec);


        Intent intent2= new Intent(this,allorder.class);
        spec= myTabHost.newTabSpec("所有菜单").setIndicator("所有菜单").setContent(intent2);
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
    public static void refresh(){
        double totalprice = 0;
        ArrayList<String> array_name = new ArrayList();
        ArrayList<Integer> array_count = new ArrayList();
        arrayView.clear();
        array_count.clear();
        for(int i = 0;i<array.size();i++){
            if(array_name.contains(array.get(i))){
                int index = array_name.indexOf(array.get(i));
                array_count.set(index,array_count.get(index)+1);
            }else{
                array_name.add(array.get(i));
                array_count.add(1);
            }

        }

        for (int i = 0;i<array_name.size();i++){
            arrayView.add(array_name.get(i)+"*" +array_count.get(i)+"     "+MenuAprice.get(array_name.get(i))*array_count.get(i));
            totalprice = totalprice+MenuAprice.get(array_name.get(i))*array_count.get(i);
        }

        taxView.setText(String.valueOf(new java.text.DecimalFormat("#.00").format(totalprice * 0.07)));
        priceView.setText(String.valueOf(new java.text.DecimalFormat("#.00").format(totalprice)));
        totalpriceView.setText(String.valueOf(new java.text.DecimalFormat("#.00").format(totalprice * 1.07)));

    }
    public void freetax(View view){
        taxView.setText(new java.text.DecimalFormat("#.00").format(0));
        totalpriceView.setText(priceView.getText());
    }

    public void Submit(View view)
    {
        arrayView.add("price " + priceView.getText().toString());
        arrayView.add("tax " + taxView.getText().toString());
        arrayView.add("total "+totalpriceView.getText().toString());
        Intent intent;
        if (Order.flag>0){
            intent = new Intent(this,setadd.class);
            intent.putExtra("cellphone",cellphone);
            startActivity(intent);
        }
        else{
            String menusList = "";
            for (int i = 0; i<Order.arrayView.size();i++){
                if(i!=Order.arrayView.size()-1){
                    menusList = menusList + Order.arrayView.get(i)+",";
                }else{
                    menusList = menusList + Order.arrayView.get(i);
                }
            }

            Date nowTime = new Date(System.currentTimeMillis());
            SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            String retStrFormatNowDate = sdFormatter.format(nowTime);
            if(cellphone!=null){
                orderMenuDB.persistO(retStrFormatNowDate,menusList,"0",cellphone,"home");
            }else{
                orderMenuDB.persistO(retStrFormatNowDate,menusList,"0","-1","home");
            }


            Order.array.clear();
            Order.arrayView.clear();
            intent = new Intent(this,OrderActivity.class);
            startActivity(intent);
        }


    }

}