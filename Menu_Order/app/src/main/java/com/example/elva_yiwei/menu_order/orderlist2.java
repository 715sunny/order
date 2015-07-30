package com.example.elva_yiwei.menu_order;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirections;

import java.util.ArrayList;


public class orderlist2 extends ListActivity implements SwipeActionAdapter.SwipeActionListener{

    protected SwipeActionAdapter myAdapter;
    protected ArrayList<String> a=new ArrayList<>();
    protected ArrayList<String> b=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=((OrderActivity)getParent()).getb();
        ((OrderActivity)getParent()).setbs(orderlist2.this.b);
        ArrayAdapter<String> Stringadapter = new ArrayAdapter<>(this, R.layout.content, R.id.textview, a);
        myAdapter = new SwipeActionAdapter(Stringadapter);
        myAdapter.setListView(getListView());
        myAdapter.setSwipeActionListener(this);
        setListAdapter(myAdapter);
        myAdapter.addBackground(SwipeDirections.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirections.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);
    }

    @Override
    public void onResume(){
        super.onResume();
        myAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean hasActions(int i) {
        return true;
    }

    @Override
    public boolean shouldDismiss(int i, int i1) {
        return (i1 == SwipeDirections.DIRECTION_NORMAL_LEFT)||(i1 == SwipeDirections.DIRECTION_FAR_LEFT)||(i1 == SwipeDirections.DIRECTION_NORMAL_RIGHT)||(i1 == SwipeDirections.DIRECTION_FAR_RIGHT);
    }

    @Override
    public void onSwipe(int[] positionList, int[] directionList) {
        for(int i=0;i<positionList.length;i++) {
            int direction = directionList[i];
            int position = positionList[i];
            switch (direction) {
                case SwipeDirections.DIRECTION_FAR_LEFT:
                    b.add(a.get(position));
                    break;
                case SwipeDirections.DIRECTION_NORMAL_LEFT:
                    b.add(a.get(position));
                    break;
                case SwipeDirections.DIRECTION_FAR_RIGHT:
                    break;
                case SwipeDirections.DIRECTION_NORMAL_RIGHT:
                    break;
            }
            this.a.remove(position);
            myAdapter.notifyDataSetChanged();
        }
    }
}
