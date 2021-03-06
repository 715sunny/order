package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends Activity {

    private OrderMenuDB orderMenuDB;
    private Cursor cursor;
    private BT bt;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppManager.getAppManager().addActivity(this);

        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        cursor = orderMenuDB.fetchAllMenus();

        if(cursor.getCount()==0){
            XmlResourceParser xmlParser = this.getResources().getXml(R.xml.menulist);
            initMenus(xmlParser);
            interGATEGORY();
        }

        View v = findViewById(R.id.button4);//找到你要设透明背景的layout 的id
        v.getBackground().setAlpha(10);//0~255透明度值

    }
    public void onClick1(View view)
    {
        Intent intent;
        intent = new Intent(this,setphonenum.class);
        startActivity(intent);
    }

    public void onClick2(View view)
    {
        Intent intent;
        intent = new Intent(this,Order.class);
        startActivity(intent);
    }
    public void onClick3(View view)
    {
        Intent intent;
        intent = new Intent(this,OrderActivity.class);
        startActivity(intent);
    }

    public void onClick4(View view)
    {
        Intent intent;
        intent = new Intent(this,setting.class);
        startActivity(intent);
    }

    public void initMenus(XmlResourceParser xmlParser) {
        try{
            int eventType = xmlParser.next();
            String name = "";
            String img = "";
            String shortcut = "";
            String type = "";
            String price = "";
            while (true){
                if(eventType == XmlPullParser.START_DOCUMENT){

                }else if(eventType == XmlPullParser.START_TAG){
                    switch (xmlParser.getDepth()){
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:

                            if(xmlParser.getName().equals("name")){
                                 name = xmlParser.nextText();
                            }
                            else if(xmlParser.getName().equals("img")){
                                 img = xmlParser.nextText();
                            }
                            else if(xmlParser.getName().equals("shortcut")){
                                 shortcut = xmlParser.nextText();
                            }else if(xmlParser.getName().equals("type")){
                                 type = xmlParser.nextText();
                            }else if(xmlParser.getName().equals("price")){
                                price = xmlParser.nextText();
                                orderMenuDB.persistM(name,img,shortcut,Integer.valueOf(type),price);
                                name = "";
                                img = "";
                                shortcut = "";
                                type = "";
                                price = "";
                            }

                            break;
                    }

                }else if(eventType == XmlPullParser. END_TAG){

                } else if (eventType == XmlPullParser. END_DOCUMENT ) {
                    break ;
                }
                eventType = xmlParser.next();
            }
        }catch (Exception e){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            new AlertDialog.Builder(this)
                    .setTitle("quit?")
                    .setMessage("sure to quit？")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        //AppManager.getAppManager().AppExit(this);
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("no", null)
                    .show();
        return true;
    }else
            return super.onKeyDown(keyCode, event);
    }

    private void interGATEGORY() {
        orderMenuDB.persistC("Chinese", "0");
        orderMenuDB.persistC("Mexician", "1");
        orderMenuDB.persistC("Italitan", "2");
        orderMenuDB.persistC("Japanese", "3");
        orderMenuDB.persistC("Korean","4");
    }

}
