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
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by User on 1/26/2015.
 */
public class RepeatingAlarm extends BroadcastReceiver
{
    private NotificationManager mNotificationManager;
    private Notification notification;
    SharedPreferences mpref;
    private boolean isWeekDay = true;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Calendar calendar = Calendar.getInstance();
        int dow = calendar.get (Calendar.DAY_OF_WEEK);
        isWeekDay = ((dow >= Calendar.MONDAY) && (dow <= Calendar.FRIDAY));
        if (isWeekDay) {


            mpref = context.getSharedPreferences("mathclock",Context.MODE_PRIVATE);
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            //Toast.makeText(context, R.string.one_shot_received, Toast.LENGTH_SHORT).show();
            mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


            Intent in = new Intent(context, ClockActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

            Intent intentone = new Intent(context.getApplicationContext(), AlarmCancel.class);
            // intentone.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intentone.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentone);
        }
    }
}
