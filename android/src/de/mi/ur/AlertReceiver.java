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
    private static PendingIntent notificationIntent;

    /*
     *This method is called when the BroadcastReceiver is receiving an Intent broadcast. If the boolean isAlarmManagerActive
     * (access over StartActivity.getAlarmManagerActive() ) form the StartActivity is true, the createNotification method is called.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if(StartActivity.getAlarmManagerActive()) {
            createNotification(context, Constants.NOTIFICATION_TITLE,Constants.NOTIFICATION_MESSAGE);
        }
    }

    /*
     * This method creates a new push notification. In this case the notification reminds the user to use the app.
     */
    public void createNotification(Context context, String title, String message) {
        System.out.println("Notification wird erstellt");
        notificationIntent = PendingIntent.getActivity(context,0,new Intent(context, StartActivity.class),0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(message);
        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
