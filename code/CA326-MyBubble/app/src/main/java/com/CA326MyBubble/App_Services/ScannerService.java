package com.CA326MyBubble.App_Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.CA326MyBubble.App_Activities.MainActivity;
import com.CA326MyBubble.App_Constructors.AddrsArray;
import com.CA326MyBubble.R;
import com.CA326MyBubble.App_Constructors.BubbleArray;

public class ScannerService extends Service {
    BluetoothManager mBluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothScanner;
    private NotificationManager notificationManager = null;

    public static boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();

        mBluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter= mBluetoothManager.getAdapter();
        mBluetoothScanner = mBluetoothAdapter.getBluetoothLeScanner();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Toast.makeText(getApplicationContext(),"Initialising Scanner", Toast.LENGTH_LONG).show();
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
        public void onScanResult(int CALLBACK_TYPE_ALL_MATCHES, ScanResult result) {
            // Distance derived from RSSI, measured power and environmental factor. MP and EV fixed constant.
            double distance = Math.pow(10, (1)*((-69.0 - (result.getRssi())) / (20.0)));
            // Checking whether device being scanned is a MyBubble User.
            if(AddrsArray.btAddrs.contains(result.getDevice().getAddress()) && !BubbleArray.bubbleAddrs.contains(result.getDevice().getAddress())) {
                // If distance less than 2m send notification, Distance under 0.5 often returned in error.
                if (distance < 2.0 && distance > 0.5) {
                    createNotification("Social Distancing Breach", "Please maintain a distance of at least 2 metres from those not in your bubble.");
                }
            }
        }
    };

    private Context mContext = this;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public void createNotification(String title, String message)
    {
        /**Creates an explicit intent for an Activity in your app**/
        Intent resultIntent = new Intent(mContext , MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.ic_bubble_breach);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }
}