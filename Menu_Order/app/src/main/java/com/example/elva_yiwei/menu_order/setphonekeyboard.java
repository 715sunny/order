package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.view.View;

/**
 * Created by jonh on 2015/7/16.
 */
public class setphonekeyboard {
    private KeyboardView keyboardView;
    private android.inputmethodservice.Keyboard k;



    public setphonekeyboard(Activity act, Context ctx){

        k = new android.inputmethodservice.Keyboard(ctx, R.xml.setphone);
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
