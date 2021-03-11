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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.CA326MyBubble.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ScannerService extends Service {
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothScanner;
    private NotificationManager notificationManager = null;

    public static boolean isRunning = false;

    private FirebaseFirestore firebaseFirestore;
    private DatabaseReference databaseReference;
    private ArrayList<String> btAddrs = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();

        mBluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter= mBluetoothManager.getAdapter();
        mBluetoothScanner = mBluetoothAdapter.getBluetoothLeScanner();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Adding all bluetooth addresses in Firestore to ArrayList
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference usersRef = rootRef.collection("Emails");
        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        btAddrs.add(document.get("BT_Add").toString());
                    }
                }
            }
        });
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
            // Checking whether device being scanned is a MyBubble User.
            if(btAddrs.contains(result.getDevice().getAddress())) {
                // If distance less than 2m send notification, Distance under 0.5 often returned in error.
                if (distance < 2.0 && distance > 0.5) {
                    Notification n = new Notification.Builder(ScannerService.this.getApplicationContext())
                            .setContentTitle("Social Distance Breach")
                            .setContentText("Contact within" + (Math.round(distance * 100.0) / 100.0) + "metres.")
                            .setSmallIcon(R.drawable.ic_bubble_breach)
                            .setAutoCancel(true).build();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(0, n);
                }
            }
        }
    };
}