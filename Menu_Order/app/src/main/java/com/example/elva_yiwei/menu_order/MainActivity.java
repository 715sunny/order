package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
        cursor = orderMenuDB.fetchAllMenus();

        if(cursor.getCount()==0){
            XmlResourceParser xmlParser = this.getResources().getXml(R.xml.menulist);
            initMenus(xmlParser);
        }
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
    public void initMenus(XmlResourceParser xmlParser) {
        try{
            int eventType = xmlParser.next();
            String name = "";
            String img = "";
            String shortcut = "";
            String type = "";
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
                                 orderMenuDB.persist(name,img,shortcut,Integer.valueOf(type));
                                 name = "";
                                 img = "";
                                 shortcut = "";
                                 type = "";
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
}
