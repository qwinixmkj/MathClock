package com.qwinix.mathclock;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeService extends Service {
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.qwinix.mathclock.displaytime";
    private final Handler handler = new Handler();
    Intent intent;
    int counter = 0;
    Calendar cal;
    SimpleDateFormat sdf;
    @Override
    public void onCreate() {
        super.onCreate();
      //  cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss a");
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 1000); // 10 seconds
        }
    };

    private void DisplayLoggingInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");
       /* Calendar cal = Calendar.getInstance();
        cal.getTime();
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
        String mTstring = sdf.format(cal.getTime());
        intent.putExtra("time",mTstring);*/
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }
}
