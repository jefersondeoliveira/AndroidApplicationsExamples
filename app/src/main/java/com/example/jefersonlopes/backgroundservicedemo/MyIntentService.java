package com.example.jefersonlopes.backgroundservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyBackgroundThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate, Thread name:"+Thread.currentThread().getName());
        Toast.makeText(this, "StartedIntentService", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent, Thread name:"+Thread.currentThread().getName());

        int duration = intent.getIntExtra("sleepTime", -1);

        //Dummy long operation
        int ctr = 1;
        while (ctr <= duration){

            Log.i(TAG, "Time elapsed: "+ctr+" secs");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ctr++;
        }

        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result", ctr);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy, Thread name:"+Thread.currentThread().getName());
        Toast.makeText(this, "FinishedIntentService", Toast.LENGTH_SHORT).show();
    }
}
