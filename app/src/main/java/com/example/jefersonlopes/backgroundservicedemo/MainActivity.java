package com.example.jefersonlopes.backgroundservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult = findViewById(R.id.textResult);
    }

    public void startBackgroundService(View view) {
        //simple background service
        Intent intent = new Intent(this, MyBackgroundService.class);
        startService(intent);

    }

    public void stopBackgroundService(View view) {
        //stop background service
        Intent intent = new Intent(this, MyBackgroundService.class);
        stopService(intent);

    }

    public void startIntentService(View view) {
        //intent service is a advanced background service
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("sleepTime", 12);
        startService(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Using a broadcast receiver for get data of intent service
        IntentFilter filter = new IntentFilter("my.own.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcastReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocalBroadcastReceiver);
    }

    private BroadcastReceiver myLocalBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int result = intent.getIntExtra("result",-1);
            textResult.setText("Task is executed in: "+result+" seconds");

        }
    };
}
