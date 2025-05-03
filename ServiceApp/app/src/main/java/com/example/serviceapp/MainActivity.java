package com.example.serviceapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    AudioService audioService;
    Button play_btn;
    Button pause_btn;
    boolean isBind=false;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.AudioBinder binder=(AudioService.AudioBinder) service;
            audioService=binder.getService();
            isBind=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        play_btn=findViewById(R.id.btn_play);
        play_btn.setOnClickListener(v -> {
            onPlayButtonClick(v);
        });

        pause_btn=findViewById(R.id.btn_pause);
        pause_btn.setOnClickListener(v -> {
            onPauseButtonClick(v);
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,AudioService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBind){
            unbindService(serviceConnection);
            isBind=false;
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(isBind){
//            audioService.pauseAudio();
//        }
//
//    }

    public  void onPlayButtonClick(View view){
        if(isBind){
            audioService.playAudio();
            Log.d("IA4IOT", "onPlayButtonClick");
        }
    }

    public  void onPauseButtonClick(View view){
        if(isBind){
            audioService.pauseAudio();
            Log.d("IA4IOT", "onPauseButtonClick");

        }
    }
}