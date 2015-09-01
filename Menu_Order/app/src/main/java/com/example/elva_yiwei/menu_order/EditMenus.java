package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by boyu on 15/8/25.
 */
public class EditMenus  extends Activity {
    private OrderMenuDB orderMenuDB;
    private Cursor cursor;
    private ListView categorysViewList;
    private ListView menusViewList;
    private static EditMenuAdapter Stringadapter = null;
    private static EditMenuAdapter Stringadapter1 = null;
    protected  ArrayList<Map<String, Object>> categorysList =new ArrayList<>();
    protected  ArrayList<Map<String, Object>> menusList =new ArrayList<>();
    protected Context context;
    private String categoryType="0";
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmenu);

        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        context = this ;


        categorysViewList = (ListView) findViewById(R.id.categorylist);
        menusViewList = (ListView) findViewById(R.id.menuslist);

        categorysList = (ArrayList<Map<String, Object>>) getDataC();
        Stringadapter = new EditMenuAdapter(this, categorysList);

        categorysViewList.setAdapter(Stringadapter);
        menusList = (ArrayList<Map<String, Object>>) getDataM(String.valueOf(categorysList.get(0).get("type")));
        Stringadapter1 = new EditMenuAdapter(context, menusList);
        menusViewList.setAdapter(Stringadapter1);

        TextView categoryname = (TextView) findViewById(R.id.categoryname);
        categoryname.setText((CharSequence) categorysList.get(0).get("title"));
        categoryType = (String) categorysList.get(0).get("id");

       categorysViewList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                menusList = (ArrayList<Map<String, Object>>) getDataM(String.valueOf(categorysList.get(arg2).get("type")));
                Stringadapter1 = new EditMenuAdapter(context, menusList);
                menusViewList.setAdapter(Stringadapter1);
                TextView add=(TextView)findViewById(R.id.categoryname);
                add.setText(categorysList.get(arg2).get("title").toString());
                categoryType = categorysList.get(arg2).get("type").toString();
            }

        });


    }

    public List<Map<String, Object>> getDataC(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        cursor = orderMenuDB.fetchAllCategory();
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("title", cursor.getString(cursor.getColumnIndex("name")));
                map.put("id", cursor.getString(cursor.getColumnIndex("id")));
                map.put("type", cursor.getString(cursor.getColumnIndex("type")));
                list.add(map);
            }
        }
        return list;
    }
    public List<Map<String, Object>> getDataM(String id){
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
    public void Addcategory(View view)
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.addcategorydialog,
                (ViewGroup) findViewById(R.id.dialog));

        new AlertDialog.Builder(this).setTitle("添加类别").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText add=(EditText)layout.findViewById(R.id.etname);
                        String categoryName = add.getText().toString();
                        Cursor cursor = orderMenuDB.fetchAllCategory();
                        cursor.moveToLast();
                        orderMenuDB.persistC(categoryName, String.valueOf(Integer.valueOf(cursor.getString(cursor.getColumnIndex("type"))) + 1));
                        categorysList = (ArrayList<Map<String, Object>>) getDataC();
                        Stringadapter = new EditMenuAdapter(context, categorysList);
                        categorysViewList.setAdapter(Stringadapter);
                    }
                })
        .setNegativeButton("取消", null).show();
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
                        Stringadapter1 = new EditMenuAdapter(context, menusList);
                        menusViewList.setAdapter(Stringadapter1);
                    }
                })
                .setNegativeButton("取消", null).show();

    }
}
