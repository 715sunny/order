package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import java.util.ArrayList;


public class allorder extends TabActivity {

    private Context context;
    private Activity activity;
    public static ListView listview;
    private OrderMenuDB orderMenuDB;
    private Cursor cursor;
    private ArrayList<MenuEntity> chineseList;
    private ArrayList<MenuEntity> sichuanList;
    private ArrayList<MenuEntity> hongkongList;
    private ArrayList<MenuEntity> japaneseList;
    private ArrayList<MenuEntity> koreaList;


    public static Adapter adapter;


    Button Xihuangjiao;
    Button Chezaimian;
    Button Boluobao;
    Button Yumitang;
    Button Chashaorou;
    Button Gulaorou;
    Button Youbaoxia;
    Button Guzhifengzhua;
    Button Changfen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorder);
        TabHost tabHost = getTabHost();
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();

        chineseList = new ArrayList<>();
        sichuanList = new ArrayList<>();
        japaneseList = new ArrayList<>();
        koreaList = new ArrayList<>();
        hongkongList = new ArrayList<>();

        tabHost.addTab(tabHost.newTabSpec("tab01").setIndicator("美式中餐").setContent(R.id.chinese));

        tabHost.addTab(tabHost.newTabSpec("tab02").setIndicator("川菜").setContent(R.id.sichuan));

        tabHost.addTab(tabHost.newTabSpec("tab03").setIndicator("粤菜").setContent(R.id.hongkong));

        tabHost.addTab(tabHost.newTabSpec("tab04").setIndicator("日本料理").setContent(R.id.japanese));

        tabHost.addTab(tabHost.newTabSpec("tab05").setIndicator("韩式烧烤").setContent(R.id.korea));

        context = this;
        activity = this;

        cursor = orderMenuDB.fetchAllMenus();
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()) {
              MenuEntity menu =  new MenuEntity();
                menu.setId(cursor.getString(cursor.getColumnIndex("id")));
                menu.setName(cursor.getString(cursor.getColumnIndex("name")));
                menu.setImage(cursor.getString(cursor.getColumnIndex("image")));
                menu.setShortcut(cursor.getString(cursor.getColumnIndex("shortcutsKey")));
                menu.setType(cursor.getString(cursor.getColumnIndex("type")));
                if(Integer.valueOf(menu.getType())==0){
                    chineseList.add(menu);
                }
                else if(Integer.valueOf(menu.getType())==1){
                    sichuanList.add(menu);
                }
                else if(Integer.valueOf(menu.getType())==2){
                    hongkongList.add(menu);
                }else  if(Integer.valueOf(menu.getType())==3){
                    japaneseList.add(menu);
                }else if(Integer.valueOf(menu.getType())==4){
                    koreaList.add(menu);
                }

            }
        }
        if(!chineseList.isEmpty()){
            creatButton(chineseList,R.id.chinese);
        }
        if(!sichuanList.isEmpty()){
            creatButton(sichuanList,R.id.sichuan);
        }
        if(!hongkongList.isEmpty()){
            creatButton(hongkongList,R.id.hongkong);
        }
        if(!japaneseList.isEmpty()){
            creatButton(japaneseList,R.id.japanese);
        }
        if(!koreaList.isEmpty()){
            creatButton(koreaList,R.id.korea);
        }


        listview = (ListView) findViewById(R.id.listView2);

        adapter = new Adapter(this,Order.arrayView);

    }
    public void Submit(View view)
    {
        Order.array.clear();
        Intent intent;
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    private void creatButton(ArrayList<MenuEntity> munes, int id){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        RelativeLayout ralativeLayout = (RelativeLayout) findViewById(id);
        final Button Btn[] = new Button[munes.size()];
        int j = -1;
        for  (int i=0; i<munes.size(); i++) {
            Btn[i]=new Button(this);
            Btn[i].setId(Integer.valueOf(munes.get(i).getId()));
            Btn[i].setText(munes.get(i).getName());
            RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams ((width-700)/4,65);  //设置按钮的宽度和高度
            if (i%3 == 0) {
                j++;
            }
            btParams.leftMargin = 10+ ((width-600)/4+10)*(i%3);   //横坐标定位
            btParams.topMargin = 20 + 70*j;   //纵坐标定位
            ralativeLayout.addView(Btn[i],btParams);   //将按钮放入layout组件
        }
        for (int k = 0; k <= Btn.length-1; k++) {
            Btn[k].setTag(k);
            final String name = (String) Btn[k].getText();
            Btn[k].setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.array.add(name);
                    Order.refresh();
                    listview.setAdapter(adapter);
                }
            });
        }
    }


}
