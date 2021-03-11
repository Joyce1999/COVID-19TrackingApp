package com.CA326MyBubble.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.CA326MyBubble.R;

public class ScannerService extends Service {
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothScanner;
    private NotificationManager notificationManager = null;

    public static boolean isRunning = false;

    @Override
    public void onCreate() {
        mBluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter= mBluetoothManager.getAdapter();
        mBluetoothScanner = mBluetoothAdapter.getBluetoothLeScanner();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startID){
        isRunning = true;
        // Start Low Energy Callback
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mBluetoothScanner.startScan(leScanCallback);
            }
        });
        return START_NOT_STICKY;
    }

    public void onDestroy(){
        isRunning = false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mBluetoothScanner.stopScan(leScanCallback);
            }
        });
        super.onDestroy();
    }

    private ScanCallback leScanCallback = new ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            // Distance derived from RSSI, measured power and environmental factor. MP and EV fixed constant.
            double distance = Math.pow(10, (1)*((-69.0 - (result.getRssi())) / (20.0)));
            //if(addresses.contains(result.getDevice().getAddress()))
            // Test BLE MAC Address to be removed
            if(result.getDevice().getAddress().equals("BC:7E:8B:BE:7A:85"))
                // If distance less than 2m send notification, Distance under 0.1 often returned in error.
                if (distance < 2.0 && distance > 0.1) {
                    Notification n  = new Notification.Builder(ScannerService.this.getApplicationContext())
                            .setContentTitle("Social Distance Breach")
                            .setContentText("Social distance bubble breached.")
                            .setSmallIcon(R.drawable.ic_bubble_breach)
                            .setAutoCancel(true).build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(0, n);
                }
        }
    };
}