package com.example.teacher.db051301;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = BluetoothAdapter.getDefaultAdapter();

        if (! mAdapter.isEnabled())
        {
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enabler, 123);
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    public void clickScan(View v)
    {
        Log.v("BLUE1", "Start Scan!!");
        mAdapter.startDiscovery();
    }
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//找到設備
            Log.d("BLUE1", "mReceiver:" + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Log.v("BLUE1", "find device:" + device.getName()
                            + device.getAddress());
                }
            }
        }
    };
}
