package com.example.foregroundservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForegroundService extends Service {
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final int NOTIFICATION_ID = 1;
    private static final long UPDATE_INTERVAL = 60000; // 1 minute in milliseconds

    private Handler handler;
    private Runnable updateNotificationTask;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        createNotificationChannel();
        startPeriodicUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start as foreground service immediately
        startForeground(NOTIFICATION_ID, createNotification());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPeriodicUpdates();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW // Using LOW to be less intrusive
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
                Log.d("ForegroundService", "Notification channel created");
            }
        }
    }

    private Notification createNotification() {
        String timeStamp = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(new Date());


        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Last updated: " + timeStamp)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .build();
    }

    private void startPeriodicUpdates() {
        updateNotificationTask = new Runnable() {
            @Override
            public void run() {
                updateNotification();
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
        handler.post(updateNotificationTask);
    }

    private void stopPeriodicUpdates() {
        if (handler != null && updateNotificationTask != null) {
            handler.removeCallbacks(updateNotificationTask);
        }
    }

    private void updateNotification() {
        Notification notification = createNotification();
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }
}