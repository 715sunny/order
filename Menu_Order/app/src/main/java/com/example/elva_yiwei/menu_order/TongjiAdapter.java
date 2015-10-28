package com.example.elva_yiwei.menu_order;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by boyu on 15/8/27.
 */
public class TongjiAdapter extends BaseAdapter{
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public TongjiAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public final class Component{
        public TextView title;
        public Button edit;
        public Button delete;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Component component = null;

        if(convertView==null){
            component = new Component();
            convertView = layoutInflater.inflate(R.layout.tongjilist,null);
            if(component!=null){
                component.title = (TextView)convertView.findViewById(R.id.title);
            }
        }
        else {
            component = (Component)convertView.getTag();
        }
        if(component!=null){
            component.title.setText((String)data.get(position).get("title"));
        }
        return convertView;
    }

}
