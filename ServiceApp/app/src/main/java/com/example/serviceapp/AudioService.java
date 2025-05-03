package com.example.serviceapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AudioService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=MediaPlayer.create(this,R.raw.sample_audio);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AudioBinder();
    }

    public void playAudio(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    public void pauseAudio(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public  class AudioBinder extends Binder{
        public AudioService getService(){
            return AudioService.this;
        }
    }
}
