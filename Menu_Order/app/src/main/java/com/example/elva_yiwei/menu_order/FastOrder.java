package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FastOrder extends Activity implements KeyboardView.OnKeyboardActionListener
{
    private Context context;
    private Activity activity;
    private EditText edit;
    private ListView listview;
    final ArrayList<String> array=new ArrayList<String>();
    private HashMap<String,String> menu=new HashMap<String,String>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        context=this;
        activity=this;
        listview=(ListView) findViewById(R.id.listView);
        edit=(EditText) findViewById(R.id.editText);

        edit.setInputType(InputType.TYPE_NULL);
        KeyboardInput keyboardInput =new KeyboardInput(activity,context);


        menu.put("A1","宫爆鸡丁");
        menu.put("A2","回锅肉");
        menu.put("A3","青椒牛柳");
        menu.put("B1","虾仁炒蛋");
        menu.put("B2","土豆牛肉");
        menu.put("B3","三椒鸡");
        menu.put("C1","鱼香肉丝");
        menu.put("C2","麻婆豆腐");
        menu.put("C3","水煮鱼");




        keyboardInput.getKeyboardView().setOnKeyboardActionListener(this);
        adapter = new Adapter(this,array);
        listview.setAdapter(adapter);

    }


    @Override
    public void onPress(int primaryCode)
    {

    }

    @Override
    public void onRelease(int primaryCode)
    {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes)
    {
        Editable editable = edit.getText();
        int start = edit.getSelectionStart();
        if (primaryCode == android.inputmethodservice.Keyboard.KEYCODE_DELETE)
        {//回退
            if (editable != null && editable.length() > 0)
            {
                if (start > 0)
                {
                    editable.delete(start - 1, start);
                }
            }
        }
        else if (primaryCode == 4896)
        { //���
            editable.clear();
        }
        else if(primaryCode== KeyEvent.ACTION_DOWN)
        {//done

            String temp=edit.getText().toString();
            if(menu.get(temp)!=null)
            {
                adapter.add(menu.get(temp));
            }
            else
            {
                adapter.add(edit.getText().toString());
            }
            editable.clear();

        }
        else
        {
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}