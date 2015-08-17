package com.example.elva_yiwei.menu_order;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class orderlist2 extends ListActivity implements SwipeActionAdapter.SwipeActionListener{

    protected SwipeActionAdapter myAdapter;
    protected  ArrayList<Map<String, Object>> a=new ArrayList<>();
    protected ArrayList<Map<String, Object>> b=new ArrayList<>();
    protected ArrayList<Map<String, Object>> bc;
    protected OrderMenuDB orderMenuDB;
    protected Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        cursor =  orderMenuDB.fetchFinishOrder();
        a= (ArrayList<Map<String, Object>>) getData();
        AdapterOrder Stringadapter = new AdapterOrder(this, a);
        myAdapter= new SwipeActionAdapter(Stringadapter);
        myAdapter.setListView(getListView());
        myAdapter.setSwipeActionListener(this);
        setListAdapter(myAdapter);
       
    }

    @Override
    public void onResume(){
        super.onResume();
        a= (ArrayList<Map<String, Object>>) getData();
        myAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean hasActions(int i) {
        return true;
    }

    @Override
    public boolean shouldDismiss(int i, int i1) {
        return (i1 == SwipeDirections.DIRECTION_NORMAL_LEFT)||(i1 == SwipeDirections.DIRECTION_FAR_LEFT)||(i1 == SwipeDirections.DIRECTION_NORMAL_RIGHT)||(i1 == SwipeDirections.DIRECTION_FAR_RIGHT);
    }

    @Override
    public void onSwipe(int[] positionList, int[] directionList) {
        for(int i=0;i<positionList.length;i++) {
            int direction = directionList[i];
            int position = positionList[i];
            switch (direction) {
                case SwipeDirections.DIRECTION_FAR_LEFT:
                    b.add(a.get(position));
                    break;
                case SwipeDirections.DIRECTION_NORMAL_LEFT:
                    b.add(a.get(position));
                    break;
                case SwipeDirections.DIRECTION_FAR_RIGHT:
                    break;
                case SwipeDirections.DIRECTION_NORMAL_RIGHT:
                    break;
            }
            this.a.remove(position);
            myAdapter.notifyDataSetChanged();
        }
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        cursor =  orderMenuDB.fetchFinishOrder();
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                Map<String, Object> map=new HashMap<String, Object>();
                String tempStr = " 时间:  " + cursor.getString(cursor.getColumnIndex("date"));
                if(!cursor.getString(cursor.getColumnIndex("address")).equals("home")){
                    tempStr = tempStr +"  地址:  "+cursor.getString(cursor.getColumnIndex("address"));
                }
                if(!cursor.getString(cursor.getColumnIndex("phoneNum")).equals("-1")){
                    tempStr = tempStr +"  电话:  "+cursor.getString(cursor.getColumnIndex("phoneNum"));
                }
                map.put("title", tempStr);
                map.put("info", cursor.getString(cursor.getColumnIndex("menusList")));
                list.add(map);
            }
        }
        return list;
    }

}
