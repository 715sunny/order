package com.example.elva_yiwei.menu_order;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class bluetooth extends ActionBarActivity {

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothPrintDriver mChatService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        Button bluetoothbutton=(Button)findViewById(R.id.bluetooth_link_button);


        Button printbutton=(Button)findViewById(R.id.bluetooth_print_button);
        bluetoothbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BluetoothPrintDriver.IsNoConnection()){
                    return;
                }
                BluetoothPrintDriver.Begin();

                String tmpContent = "Sunny!!";
                BluetoothPrintDriver.BT_Write(tmpContent);
                BluetoothPrintDriver.BT_Write("\r");

            }
        });



    }

    OnClickListener mBtnConnetBluetoothDeviceOnClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(bluetooth.this, DeviceListActivity.class);
            startActivityForResult(intent, REQUEST_CONNECT_DEVICE);
        }
    };




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);
                }
                break;
        }
    }

public void onClick_bt(View view){
                Toast.makeText(getApplicationContext(),"testpress",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(bluetooth.this, DeviceListActivity.class);
               startActivityForResult(intent, REQUEST_CONNECT_DEVICE);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
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
