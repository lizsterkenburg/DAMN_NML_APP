package com.example.splashscreen;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {

    //used for register alarm manager
    private PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    private AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        RegisterAlarmBroadcast();
        setAlarm();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 2500);
    }

    public void setAlarm()
    {
        //Get the current time and set alarm after 10 seconds from current time
        // so here we get
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.SECOND, 5);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        System.out.println(calendar.getTimeInMillis());
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 0, pendingIntent);

        Context context = this;

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.setAction("Broadcast");
        pendingIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, 0);

        // Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 35);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTimeInMillis()-System.currentTimeMillis() );
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 * 60 * 24, pendingIntent);
    }

    private void RegisterAlarmBroadcast()
    {
        pendingIntent = PendingIntent.getBroadcast( this, 1, new Intent(this, Receiver.class),0 );
        alarmManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }




}