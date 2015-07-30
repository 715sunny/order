package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;


public class setphonenum extends Activity implements KeyboardView.OnKeyboardActionListener
{

    private Context context;
    private ArrayList<String> list;
    private Activity activity;
    private AutoCompleteTextView text;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent=new Intent(this,setaddress.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setphonenum);
        context=this;
        activity=this;
        setphonekeyboard keyboardInput = new setphonekeyboard(activity,context);
        keyboardInput.getKeyboardView().setOnKeyboardActionListener(this);
        list=new ArrayList<>();
        list.add("2012089647");
        list.add("2012089648");
        list.add("2012089649");
        list.add("2012089650");
        list.add("2012089651");
        list.add("2012089652");
        list.add("2012089653");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        text=(AutoCompleteTextView)findViewById(R.id.Autotext);
        text.setInputType(InputType.TYPE_NULL);
        text.setAdapter(adapter);
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Editable editable=text.getText();
        int start = text.getSelectionStart();
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
        {
            editable.clear();
        }
        else if(primaryCode== KeyEvent.ACTION_DOWN)
        {

            startActivity(intent);

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
