package com.example.jefersonlopes.backgroundservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyBackgroundService extends Service {

    private static final String TAG = MyBackgroundService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate, Thread name:"+Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand, Thread name:"+Thread.currentThread().getName());

        //Perform tasks: Dummy long operation
        new MyAsyncTask().execute(); //Background Thread

        //START_STICKY Automatic restart background service and intent is null
        //START_REDELIVER_INTENT Automatic restart background service and resend intent data
        //START_NOT_STICKY Not restart background service

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind, Thread name:"+Thread.currentThread().getName());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy, Thread name:"+Thread.currentThread().getName());
        super.onDestroy();
    }

    class MyAsyncTask extends AsyncTask<Void, String, Void>{

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute, Thread name:"+Thread.currentThread().getName());
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground, Thread name:"+Thread.currentThread().getName());

            //Dummy long operation
            int ctr = 1;
            while (ctr <= 12){

                publishProgress("Time elapsed: "+ctr+" secs");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ctr++;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            Log.i(TAG, "onProgressUpdate, Counted value:"+values[0]+", Thread name:"+Thread.currentThread().getName());

            Toast.makeText(MyBackgroundService.this, values[0], Toast.LENGTH_SHORT);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute, Thread name:"+Thread.currentThread().getName());
            super.onPostExecute(aVoid);
            stopSelf();
        }
    }

}
