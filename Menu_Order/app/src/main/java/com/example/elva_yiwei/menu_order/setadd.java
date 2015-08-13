package com.example.elva_yiwei.menu_order;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;


public class setadd extends ActionBarActivity {
    private OrderMenuDB orderMenuDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setadd);
        orderMenuDB = new OrderMenuDB(this);
        orderMenuDB.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setadd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void Submit(View view)
    {

        EditText editText1 =(EditText)findViewById(R.id.editText);
        String menusList = "";
        for (int i = 0; i<Order.array.size();i++){
            if(i!=Order.array.size()-1){
                menusList = menusList + Order.array.get(i)+",";
            }else{
                menusList = menusList + Order.array.get(i);
            }
        }

        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd  kk:mm:ss ");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        orderMenuDB.persistO(retStrFormatNowDate,menusList,"0","-1",editText1.getText().toString());

        Order.array.clear();
        Order.arrayView.clear();

        Intent intent;
        intent = new Intent(this,OrderActivity.class);
        startActivity(intent);

    }
}
