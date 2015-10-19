package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by boyu on 15/8/25.
 */
public class Tongji  extends Activity {
    private OrderMenuDB orderMenuDB;
    private Cursor cursor;
    private ListView categorysViewList;
    private ListView menusViewList;
    private static TimeAdapter Stringadapter = null;
    private static TimeAdapter Stringadapter1 = null;
    protected  ArrayList<Map<String, Object>> categorysList =new ArrayList<>();
    protected  ArrayList<Map<String, Object>> menusList =new ArrayList<>();
    protected Context context;
    private String categoryType="0";
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji);

        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        context = this ;


        categorysViewList = (ListView) findViewById(R.id.timelist);
        menusViewList = (ListView) findViewById(R.id.menuslist);

        categorysList = (ArrayList<Map<String, Object>>) getDataC();
        Stringadapter = new TimeAdapter(this, categorysList);

        categorysViewList.setAdapter(Stringadapter);
        //menusList = (ArrayList<Map<String, Object>>) getDataM(String.valueOf(categorysList.get(0).get("type")));
        menusList = (ArrayList<Map<String, Object>>) getData();
        Stringadapter1 = new TimeAdapter(context, menusList);
        menusViewList.setAdapter(Stringadapter1);

        TextView categoryname = (TextView) findViewById(R.id.categoryname);
        categoryname.setText((CharSequence) categorysList.get(0).get("title"));
        categoryType = (String) categorysList.get(0).get("type");

        categorysViewList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                menusList = (ArrayList<Map<String, Object>>) getData();
                Stringadapter1 = new TimeAdapter(context, menusList);
                menusViewList.setAdapter(Stringadapter1);
                TextView add=(TextView)findViewById(R.id.categoryname);
                add.setText(categorysList.get(arg2).get("title").toString());
                //categoryType = categorysList.get(arg2).get("type").toString();
            }

        });


    }

    public List<Map<String, Object>> getDataC(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        Map<String, Object> map1=new HashMap<String, Object>();
        map1.put("title", ("今日订单统计"));
        map1.put("type", "0");
        list.add(map1);
        Map<String, Object> map2=new HashMap<String, Object>();
        map2.put("title", ("本月订单统计"));
        map2.put("type", "1");
        list.add(map2);
        Map<String, Object> map3=new HashMap<String, Object>();
        map3.put("title", ("季度订单统计"));
        map3.put("type", "2");
        list.add(map3);
        Map<String, Object> map4=new HashMap<String, Object>();
        map4.put("title", ("详单查询"));
        map4.put("type", "3");
        list.add(map4);
        return list;
    }
    public List<Map<String, Object>> getDataM(String id){
//        Cursor result=db.rawQuery("SELECT ID, name, inventory FROM mytable");
//        result.moveToFirst();
//        while (!result.isAfterLast()) {
//            int id=result.getInt(0);
//            String name=result.getString(1);
//            int inventory=result.getInt(2);
//            // do something useful with these
//            result.moveToNext();
//        }
//        result.close();
//
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        cursor =  orderMenuDB.fetchMenusById(id);
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("title", cursor.getString(cursor.getColumnIndex("name")));
                list.add(map);
            }
        }
        return list;
    }


//    public List<Map<String, Object>> getData(){
//            List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
//
//            cursor =  orderMenuDB.fetchAllOrder();
//            if(cursor.getCount()!=0) {
//                //List<Info> info = new ArrayList<Info>();
//                while (cursor.moveToNext()) {
//                    Map<String, Object> map=new HashMap<String, Object>();
//                    String order = cursor.getString(cursor.getColumnIndex("menusList"));
//
////                    String[] tempStr = info.split(",");
////                    //map.put("title", tempStr);
////                    map.put("title", tempStr[2]);
//                     list1.add(0,map);
//                     list.add(0,order);
//                }
//            }
//            return list1;
//        }



    public List<Map<String, Object>> getData(){

        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd ");
        String retStrFormatNowDate = sdFormatter.format(nowTime);

        List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
        List<String> list = new ArrayList<String>();
        cursor =  orderMenuDB.fetchOrderByDate(retStrFormatNowDate+"00:00:00",retStrFormatNowDate+"12:59:59");
        int ordernum=0;

        if(cursor.getCount()!=0 ) {
            //List<Info> info = new ArrayList<Info>();
            while (cursor.moveToNext()) {
                Map<String, Object> map=new HashMap<String, Object>();
                String order = cursor.getString(cursor.getColumnIndex("menusList"));

//                    String[] tempStr = info.split(",");
//                    //map.put("title", tempStr);
//                    map.put("title", tempStr[2]);
                list.add(0,order);
                ordernum++;
            }
        }

        double taxTotal = 0.00;
        double totalAmunt = 0.00;

        Map<String, Info> map = new HashMap<String, Info>();
        Map<String, Integer> mapSort = new HashMap<String, Integer>();
        for (String str : list) {
            String tax = str.substring(str.lastIndexOf("tax ")+4,str.lastIndexOf(",total "));
            taxTotal = taxTotal + Double.valueOf(tax);

            String totalstr = str.substring(str.lastIndexOf("total ")+6,str.length());
            totalAmunt = totalAmunt + Double.valueOf(totalstr);

            String[] tempStr = str.split(",");
            for (int i = 0; i < tempStr.length - 3; i++) {
                String name = tempStr[i].substring(0, tempStr[i].lastIndexOf("*"));
                Integer quantity = Integer.valueOf(tempStr[i].substring(tempStr[i].lastIndexOf("*")+1, tempStr[i].lastIndexOf("     ")));
                Double total = Double.valueOf(tempStr[i].substring(tempStr[i].lastIndexOf("     ")+1, tempStr[i].length()));
                if(map.get(name) == null){
                    Info info = new Info();
                    info.setName(name);
                    info.setQuantity(quantity);
                    info.setTotal(total);
                    map.put(name, info);
                    mapSort.put(name, quantity);
                }else{
                    Info info = map.get(name);
                    info.setQuantity(info.getQuantity() + quantity);
                    info.setTotal(info.getTotal() + total);
                    mapSort.put(name,mapSort.get(name)+ quantity);
                }
            }
        }

        List<Map.Entry<String, Integer>> infoIds =  new ArrayList<Map.Entry<String, Integer>>(mapSort.entrySet());

        //排序前
//      System.out.println("排序前=====================start");
//      for (int i = 0; i < infoIds.size(); i++) {
//    	    String name = infoIds.get(i).toString();
//    	    System.out.println(name);
//      }
//      System.out.println("排序前=====================end");

        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //排序后
        List<Info> returnList= new ArrayList<Info>();
        for (int i = 0; i < infoIds.size(); i++) {
            String name = infoIds.get(i).getKey();
            returnList.add(map.get(name));
        }
//        for (Info info : returnList) {
//            System.out.println("name:" + info.getName() + "  Quantity:" + info.getQuantity() + "   Total:" + info.getTotal());
//        }
//
//        System.out.println("taxTotal:"+String.format("%.2f", taxTotal));
//        System.out.println("totalAmunt:"+String.format("%.2f", totalAmunt));
        for(Info info : returnList){
            Map<String, Object> map1=new HashMap<String, Object>();
            String string= "name:" + info.getName() + "  Quantity:" + info.getQuantity() + "   Total:" + info.getTotal();
            map1.put("title", string);
            list1.add(map1);

        }

        Map<String, Object> map1=new HashMap<String, Object>();
        String tax= "taxTotal:"+String.format("%.2f", taxTotal);
        map1.put("title",tax);
        list1.add(0,map1);
        Map<String, Object> map2=new HashMap<String, Object>();
        String total= "incomTotal:"+String.format("%.2f", totalAmunt);
        map2.put("title",total);
        list1.add(0,map2);
        Map<String, Object> map3=new HashMap<String, Object>();
        String ordernumstring= "order number:"+String.valueOf(ordernum);
        map3.put("title",ordernumstring);
        list1.add(0,map3);



        return list1;
    }





    public void Addmenus(View view)
    {

        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.addmenudialog,
                (ViewGroup) findViewById(R.id.dialog));

        new AlertDialog.Builder(this).setTitle("添加菜品").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText menuname=(EditText)layout.findViewById(R.id.etname);
                        EditText menuepresskey = (EditText)layout.findViewById(R.id.expresskey);
                        EditText menueprice = (EditText)layout.findViewById(R.id.price);
                        orderMenuDB.persistM(menuname.getText().toString(),"000",menuepresskey.getText().toString(),Integer.valueOf(categoryType),menueprice.getText().toString());
                        menusList = (ArrayList<Map<String, Object>>) getDataM(categoryType);
                        Stringadapter1 = new TimeAdapter(context, menusList);
                        menusViewList.setAdapter(Stringadapter1);
                    }
                })
                .setNegativeButton("取消", null).show();

    }
}
