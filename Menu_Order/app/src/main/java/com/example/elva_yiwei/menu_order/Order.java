package com.example.elva_yiwei.menu_order;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order extends TabActivity

{
    public  static TabHost myTabHost;
    public static ArrayList<String> array=new ArrayList<String>() ;
    public static ListView listview;
    static int flag;
    String cellphone = null;
    public static ArrayList<String> arrayView = new ArrayList();
    private OrderMenuDB orderMenuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        cellphone = intent.getStringExtra("cellphone");
        listview = (ListView) findViewById(R.id.listView2);

        myTabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();

        Intent intent1= new Intent(this,FastOrder.class);
        spec= myTabHost.newTabSpec("FastOrder").setIndicator("快速点单").setContent(intent1);
        myTabHost.addTab(spec);


        Intent intent2= new Intent(this,allorder.class);
        spec= myTabHost.newTabSpec("AllOrder").setIndicator("全部点单").setContent(intent2);
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
            arrayView.add(array_name.get(i)+"*" +array_count.get(i));
        }

    }

    public void Submit(View view)
    {

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
            SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd  kk:mm:ss ");
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