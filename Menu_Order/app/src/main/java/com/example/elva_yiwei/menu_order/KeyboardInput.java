package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.*;
import android.inputmethodservice.Keyboard;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Elva_yiwei on 2015/7/13.
 */
public class KeyboardInput extends InputMethodService {
    private KeyboardView keyboardView;
    private android.inputmethodservice.Keyboard k;



    public KeyboardInput(Activity act, Context ctx){

        k = new android.inputmethodservice.Keyboard(ctx, R.xml.activity_keyboard_input);
        keyboardView = (KeyboardView) act.findViewById(R.id.fast_order_keyboard);
        keyboardView.setKeyboard(k);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setVisibility(View.VISIBLE);

    }

    public KeyboardView getKeyboardView(){
        return this.keyboardView;
    }



}