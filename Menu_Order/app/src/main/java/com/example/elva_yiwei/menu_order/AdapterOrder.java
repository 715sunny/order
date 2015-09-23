package com.example.elva_yiwei.menu_order;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by boyu on 15/8/13.
 */
public class AdapterOrder extends BaseAdapter {
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;



    public AdapterOrder(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
 //       mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        mChatService = new BluetoothPrintDriver(context, mHandler);
    }

    public final class Component{
        public TextView title;
        public Button view;
        public TextView info;
        public TextView price;
    }

    //private static final String TAG = "BloothPrinterActivity";


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
        final Component component;

        if(convertView==null){
            component = new Component();
            convertView = layoutInflater.inflate(R.layout.customlist,null);
            if(component!=null){
                component.title = (TextView)convertView.findViewById(R.id.title);
                component.view = (Button)convertView.findViewById(R.id.view);
                component.info = (TextView)convertView.findViewById(R.id.info);
                component.price = (TextView)convertView.findViewById(R.id.price);

                component.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){


                            if(BluetoothPrintDriver.IsNoConnection()){
                                return;
                            }

                            BluetoothPrintDriver.Begin();
                            String cai = component.info.getText().toString();
                            cai= cai.replaceAll(",", "\r");
                            String title = component.title.getText().toString();
                            title = title.replaceAll("  ","\r");


                        BluetoothPrintDriver.SetFontEnlarge((byte) 0x01);
                        BluetoothPrintDriver.BT_Write("            Receipt");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.SetFontEnlarge((byte) 0x00);

                        BluetoothPrintDriver.BT_Write(title);
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.SetFontEnlarge((byte) 0x01);
                        BluetoothPrintDriver.BT_Write(cai);
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("\r");
                        BluetoothPrintDriver.BT_Write("--------------------------------");


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
            component.info.setText((String)data.get(position).get("info"));
        }

        return convertView;
    }
}
