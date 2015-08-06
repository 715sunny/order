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

                Intent t= new Intent(PickorDelivery.this, Order.class);
                startActivity(t);
            }
        });

        delivery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(PickorDelivery.this, Order.class);
                startActivity(t);
            }
        });
    }

}


