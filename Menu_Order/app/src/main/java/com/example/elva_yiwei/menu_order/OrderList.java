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


public class OrderList extends ListActivity implements SwipeActionAdapter.SwipeActionListener{

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
        cursor =  orderMenuDB.fetchUnfinishOrder();

        a= (ArrayList<Map<String, Object>>) getData();
        ((OrderActivity) getParent()).setb(OrderList.this.b);
        AdapterOrder Stringadapter = new AdapterOrder(this, a);

        // ArrayAdapter<String> Stringadapter=new ArrayAdapter<>(this,R.layout.content,R.id.textview,a);
        myAdapter= new SwipeActionAdapter(Stringadapter);
        myAdapter.setListView(getListView());
        myAdapter.setSwipeActionListener(this);
        setListAdapter(myAdapter);

    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        cursor =  orderMenuDB.fetchUnfinishOrder();
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                Map<String, Object> map=new HashMap<String, Object>();
                String tempStr = "Time: " + cursor.getString(cursor.getColumnIndex("date"));
                if(!cursor.getString(cursor.getColumnIndex("address")).equals("home")){
                    tempStr = tempStr +" Add: "+cursor.getString(cursor.getColumnIndex("address"));
                }
                if(!cursor.getString(cursor.getColumnIndex("phoneNum")).equals("-1")){
                    tempStr = tempStr +"  Tel: "+cursor.getString(cursor.getColumnIndex("phoneNum"));
                }
                map.put("title", tempStr);
                map.put("info", cursor.getString(cursor.getColumnIndex("menusList")));
                list.add(0,map);
            }
        }
        return list;
    }

    @Override
    public void onResume(){
        bc=((OrderActivity)getParent()).getbs();
        if(bc!=null){
            a.addAll(bc);
            bc.clear();
        }
        myAdapter.notifyDataSetChanged();
        super.onResume();
    }


    @Override
    public boolean hasActions(int i) {
        return true;
    }

    @Override
    public boolean shouldDismiss(int i, int i1) {
         return (i1 == SwipeDirections.DIRECTION_NORMAL_RIGHT)||(i1 == SwipeDirections.DIRECTION_FAR_RIGHT);
    }

    @Override
    public void onSwipe(int[] positionList, int[] directionList) {

        for(int i=0;i<positionList.length;i++) {
            int direction = directionList[i];
            int position = positionList[i];

            switch (direction) {
                case SwipeDirections.DIRECTION_FAR_LEFT:
                    break;
                case SwipeDirections.DIRECTION_NORMAL_LEFT:
                    break;
                case SwipeDirections.DIRECTION_FAR_RIGHT:
                    Map<String, Object> p = a.get(position);
                    String str = (String) p.get("title");
                    updataDb(str.substring(6,26));
                    this.a.remove(position);
                break;
                case SwipeDirections.DIRECTION_NORMAL_RIGHT:
                    Map<String, Object> p1 = a.get(position);
                    String str1= (String) p1.get("title");
                    updataDb(str1.substring(6,26));
                    this.a.remove(position);
                    break;
            }
            myAdapter.notifyDataSetChanged();
        }
    }

    private void updataDb(String s) {
        orderMenuDB.updateDbbytime(s);
    }
}
