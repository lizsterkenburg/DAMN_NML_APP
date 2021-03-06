package com.damn.splashscreen;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("WHOOP WHOOP");

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.notifaction), Context.MODE_PRIVATE);
        Boolean notificationState = sharedPref.getBoolean(context.getString(R.string.notifaction_state), true);
        if(notificationState) {
            makeNotification(context);
        } else {
            System.out.println("alarm is canceled");
        }
    }

    public void makeNotification(Context context){
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i,0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.logo_damn___small)
                .setContentTitle("Reminder")
                .setContentText("Have you completed today's practice yet?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        createNotificationChannel(context);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }
    public void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notifaction);
            String description = context.getString(R.string.notifaction);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
