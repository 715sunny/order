package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.app.TabActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver;

import java.util.ArrayList;
import java.util.Map;


public class OrderActivity extends TabActivity {
    private TabHost mTabHost;
    protected ArrayList<Map<String, Object>> b;
    protected ArrayList<Map<String, Object>> bs;
    public void setb(ArrayList<Map<String, Object>> b){
        OrderActivity.this.b=b;
    }
    public  ArrayList<Map<String, Object>>  getb(){
        return OrderActivity.this.b;
    }
    public void setbs(ArrayList<Map<String, Object>> b){
        OrderActivity.this.bs=b;
    }
    public ArrayList<Map<String, Object>> getbs(){
        return OrderActivity.this.bs;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_ordergrid);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mTabHost=(TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;
        intent= new Intent(this,OrderList.class);
        spec= mTabHost.newTabSpec("orderlist1")
                .setIndicator("未完成订单")
                .setContent(intent);
        mTabHost.addTab(spec);
        intent= new Intent(this,orderlist2.class);
        spec= mTabHost.newTabSpec("orderlist2")
                .setIndicator("已完成订单")
                .setContent(intent);
        mTabHost.addTab(spec);

    }

    private static final String TAG = "BloothPrinterActivity";
    private static final boolean D = true;
    //
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothPrintDriver mChatService = null;



    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    /** Called when the activity is first created. */
    private Button mBtnConnetBluetoothDevice = null;
    private Button mBtnQuit = null;
    private Button mBtnPrint = null;
    private Button mBtnPrintOption = null;
    private Button mBtnTest = null;
    private Button mBtnInquiry = null;
    private EditText mPrintContent = null;
    private CheckBox mBeiKuan = null;
    private CheckBox mUnderline = null;
    private CheckBox mBold = null;
    private CheckBox mBeiGao = null;
    private CheckBox mMinifont = null;
    private CheckBox mHightlight = null;



    @Override
    public void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if(mBluetoothAdapter!=null){
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                // Otherwise, setup the chat session
            } else {
                if (mChatService == null) setupChat();
            }
        }

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(setting.bt.getAddress());
        // Attempt to connect to the device
        mChatService.connect(device);


    }

    private void setupChat() {
        mChatService = new BluetoothPrintDriver(this, mHandler);
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothPrintDriver.STATE_CONNECTED:
                            break;
                        case BluetoothPrintDriver.STATE_CONNECTING:
                            break;
                        case BluetoothPrintDriver.STATE_LISTEN:
                        case BluetoothPrintDriver.STATE_NONE:
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    break;
                case MESSAGE_READ:
                    String ErrorMsg = null;
                    byte[] readBuf = (byte[]) msg.obj;
                    float Voltage = 0;
                    if(readBuf[2]==0)
                        ErrorMsg = "NO ERROR!         ";
                    else
                    {
                        if((readBuf[2] & 0x02) != 0)
                            ErrorMsg = "ERROR: No printer connected!";
                        if((readBuf[2] & 0x04) != 0)
                            ErrorMsg = "ERROR: No paper!  ";
                        if((readBuf[2] & 0x08) != 0)
                            ErrorMsg = "ERROR: Voltage is too low!  ";
                        if((readBuf[2] & 0x40) != 0)
                            ErrorMsg = "ERROR: Printer Over Heat!  ";
                    }
                    Voltage = (float) ((readBuf[0]*256 + readBuf[1])/10.0);
                    //if(D) Log.i(TAG, "Voltage: "+Voltage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    break;
                case MESSAGE_TOAST:
                    break;
            }
        }
    };

    View.OnClickListener mBtnPrintOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(BluetoothPrintDriver.IsNoConnection()){
                return;
            }
            BluetoothPrintDriver.Begin();

            String tmpContent = mPrintContent.getText().toString();
            BluetoothPrintDriver.BT_Write(tmpContent);
            BluetoothPrintDriver.BT_Write("\r");
        }
    };

    @Override
    public synchronized void onResume() {
        super.onResume();

        if (mChatService != null) {
            if (mChatService.getState() == BluetoothPrintDriver.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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

}
