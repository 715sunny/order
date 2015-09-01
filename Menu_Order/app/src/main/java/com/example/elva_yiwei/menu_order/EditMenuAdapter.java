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
public class EditMenuAdapter extends BaseAdapter{
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public EditMenuAdapter(Context context, List<Map<String, Object>> data){
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
            convertView = layoutInflater.inflate(R.layout.activity_editmenuslist,null);
            if(component!=null){
                component.title = (TextView)convertView.findViewById(R.id.title);
                component.edit = (Button)convertView.findViewById(R.id.view);
                component.delete = (Button)convertView.findViewById(R.id.view1);
                component.delete.setOnClickListener(new View.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(View v) {
                                                           String id = data.get(position).get("id").toString();
                                                            data.remove(position);
                                                            EditMenuAdapter.this.notifyDataSetChanged();
                                                            OrderMenuDB orderMenuDB =new OrderMenuDB(context);
                                                            orderMenuDB.open();
                                                            orderMenuDB.deleteCategory(id);
                                                        }
                                                    }


                );
                component.edit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                    }
                });
                convertView.setTag(component);
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
