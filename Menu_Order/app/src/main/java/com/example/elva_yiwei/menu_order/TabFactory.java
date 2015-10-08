package com.example.elva_yiwei.menu_order;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;

/**
 * Created by boyu on 15/8/20.
 */
public class TabFactory  implements TabHost.TabContentFactory {

    private Context con;
    private OrderMenuDB orderMenuDB;
    private Cursor cursor;
    public Drawable drawable;
    public TabFactory(Context c){
        con=c;
    }
    @Override
    public View createTabContent(String tag) {
        orderMenuDB = new OrderMenuDB(con);
        orderMenuDB.open();
        RelativeLayout ralativeLayout = new RelativeLayout(con);

        String [] strArray = tag.split("_");
        cursor = orderMenuDB.fetchMenusById(strArray[1]);
        final Button Btn[] = new Button[cursor.getCount()];
        int j = -1;
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()) {
                Btn[cursor.getPosition()]=new Button(con);
                Btn[cursor.getPosition()].setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
                Btn[cursor.getPosition()].setText(cursor.getString(cursor.getColumnIndex("name")));
                Btn[cursor.getPosition()].setBackgroundResource(R.drawable.button);
                RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams (97,62);
                if (cursor.getPosition()%4 == 0) {
                    j++;
                }
                btParams.leftMargin = 30+ ((400)/3+10)*(cursor.getPosition()%4);
                btParams.topMargin = 30 + 70*j;
                ralativeLayout.addView(Btn[cursor.getPosition()],btParams);
            }
        }
        for (int k = 0; k <= Btn.length-1; k++) {
            Btn[k].setTag(k);
            final String name = (String) Btn[k].getText();
            Btn[k].setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order.array.add(name);
                    Order.refresh();
                    allorder.listview.setAdapter(allorder.adapter);
                }
            });
        }
        return ralativeLayout;
    }
}
