package com.example.jefersonlopes.backgroundservicedemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

//this intentService work for 26+
public class MyJobIntentService extends JobIntentService {

    private static final String TAG = MyJobIntentService.class.getSimpleName();

    public static void enqueueWork(Context context, Intent intent){
        enqueueWork(context, MyJobIntentService.class, 17, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate, Thread name:"+Thread.currentThread().getName());
        Toast.makeText(this, "StartedIntentService", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(TAG, "onHandleWork, Thread name:"+Thread.currentThread().getName());

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy, Thread name:"+Thread.currentThread().getName());
        Toast.makeText(this, "FinishedIntentService", Toast.LENGTH_SHORT).show();
    }

}
