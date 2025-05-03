package com.example.sensorsapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends Activity {

    private SensorManager mgr;
    private TextView txtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        txtList = (TextView) findViewById(R.id.tv);
        List<Sensor> sensorList = mgr.getSensorList(Sensor.TYPE_ALL);
        StringBuilder strBuilder = new StringBuilder();
        for (Sensor s : sensorList) {
            strBuilder.append(s.getName() + "\n");
        }
        txtList.setVisibility(TextView.VISIBLE);
        txtList.setText(strBuilder);


    }
}



