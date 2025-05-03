package com.example.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class DownloadService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DownloadService", "Service cree");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadFile("url");
                stopSelf();
            }
        }).start();
        return START_STICKY;
    }

    private void downloadFile(String lien){
        try{
            Thread.sleep(5000);
            Log.d("DownloadService", "Telechargement termine");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DownloadService", "Service Detruit");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

