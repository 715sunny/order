package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PickorDelivery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickupordelivery);
        Button delivery_button = (Button) findViewById(R.id.delivery);
        Button pickup_button = (Button) findViewById(R.id.pickup);

        pickup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent t= new Intent();
                t.setClass(PickorDelivery.this, Order.class);
                t.putExtra("flag", 0);
                Bundle extras = getIntent().getExtras();
                String myText = extras.getString("myText");
                startActivity(t);
            }
        });

        delivery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent();
                t.setClass(PickorDelivery.this, Order.class);
                t.putExtra("flag", 1);
                startActivity(t);
            }
        });
    }

}


