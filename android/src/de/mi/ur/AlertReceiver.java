package de.mi.ur;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import de.mi.ur.Activities.StartActivity;

/**
 * Created by Lydia on 28.09.2016.
 */

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (StartActivity.getAlarmManagerActive()) {
            createNotification(context, StartActivity.getNotificationTitle(), StartActivity.getNotificationMessage());
        }
    }

    public void createNotification(Context context, String msg, String msgText) {
        System.out.println("Notification wird erstellt");
        PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context, StartActivity.class),0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(msg)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        System.out.println("Notification sollte erschienen sein");

    }
}
