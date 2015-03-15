package com.qwinix.mathclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.net.URI;

/**
 * Created by User on 1/26/2015.
 */
public class OneShotAlarm extends BroadcastReceiver
{
    private NotificationManager mNotificationManager;
    private  Notification notification;
    SharedPreferences mpref;
    private final Handler handler = new Handler();

    public static final String BROADCAST_ACTION_SINGLE = "com.qwinix.mathclock.oneshortalarm";

    Intent intent1;



    @Override
    public void onReceive(Context context, Intent intent)
    {
        Uri alert;
        intent1 = new Intent(BROADCAST_ACTION_SINGLE);
        context.sendBroadcast(intent1);
        mpref = context.getSharedPreferences("mathclock",Context.MODE_PRIVATE);
        if (mpref.contains("uri")) {
            alert = Uri.parse(mpref.getString("uri",""));
        }else
         alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        //Toast.makeText(context, R.string.one_shot_received, Toast.LENGTH_SHORT).show();
        mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent in = new Intent(context, ClockActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                in, 0);
       /* notification = new Notification(R.drawable.logo, "Wake up alarm", System.currentTimeMillis());
        notification.setLatestEventInfo(context, "Hanuman Chalisa", "Wake Up...", contentIntent);
        notification.flags = Notification.FLAG_INSISTENT;
        notification.sound = alert;
         mNotificationManager.notify(1, notification);*/

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Wake Up...")
                        .setAutoCancel(true);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alert);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
        mpref.edit().putString("alarmtime","").commit();
      /*  Intent intent3 = new Intent(context,AlarmCancel.class);
        context.startActivity(intent3);*/

        Intent intentone = new Intent(context.getApplicationContext(), AlarmCancel.class);
       // intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intentone.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentone);

    }
}