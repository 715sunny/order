package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
