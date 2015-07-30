package com.example.elva_yiwei.menu_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
public class Adapter extends BaseAdapter {
    private ArrayList<String> array=new ArrayList<String>();
    private Context mContext;
    private Button curDel_btn;
    public Adapter(Context mContext,ArrayList<String> array) {
        this.mContext = mContext;
        this.array = array;
    }
    public int getCount() {
        return this.array.size();
    }
    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.btnDel = (Button) view.findViewById(R.id.del);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvTitle.setText(this.array.get(position));

        viewHolder.btnDel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                array.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public void add(String s) {
        array.add(s);
        notifyDataSetChanged();
    }

    final static class ViewHolder {
        TextView tvTitle;
        Button btnDel;
    }
}