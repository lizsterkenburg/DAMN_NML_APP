package com.damn.splashscreen;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sharedPref;

    //used for register alarm manager
    private PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    private AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    private BroadcastReceiver mReceiver;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        ImageView logo = findViewById(R.id.imageView6);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("true")){
            Drawable myDrawable = getResources().getDrawable(R.drawable.logo_damn);
            logo.setImageDrawable(myDrawable);
        }
        else{
            Drawable myDrawable = getResources().getDrawable(R.drawable.green_owl_no_text);
            logo.setImageDrawable(myDrawable);
        }

        setAlarm();

    }
    @Override
    public void onBackPressed() {
        System.out.println("cancel");
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onBackPressed();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)||
                    !(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)||
                    !(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) ||
                    !(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)||
                    !(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,},1);
            }
            else {
                handler = new Handler();
                handler.postDelayed(() -> {
                    if(!sharedPref.getString(getString(R.string.user_ID),"not submitted").equals("not submitted") ) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, ID_check.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                }, 2500);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            checkPermission();

        } else {
            checkPermission();
        }
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
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.setAction("Broadcast");
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        System.out.println("alarm set for "+sharedPref.getInt(getString(R.string.hour),20)+":"+sharedPref.getInt(getString(R.string.minute),0));
        // Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 27);
        calendar.set(Calendar.HOUR_OF_DAY, sharedPref.getInt(getString(R.string.hour),20));
        calendar.set(Calendar.MINUTE, sharedPref.getInt(getString(R.string.minute),0));
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTimeInMillis()-System.currentTimeMillis() );
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 * 60 * 24 * 6, pendingIntent);
    }

    private void RegisterAlarmBroadcast()
    {
        pendingIntent = PendingIntent.getBroadcast( this, 1, new Intent(this, Receiver.class),0 );
        alarmManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }




}