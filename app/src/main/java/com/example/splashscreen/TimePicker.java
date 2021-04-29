package com.example.splashscreen;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    //used for register alarm manager
    private PendingIntent pendingIntent;
    //used to store running alarmmanager instance
    private AlarmManager alarmManager;
    //Callback function for Alarmmanager event
    private BroadcastReceiver mReceiver;
    private Context context;

    public TimePicker(Context context){
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        setAlarm(hourOfDay, minute);
    }

    public void setAlarm(int hour, int minute)
    {
        //Get the current time and set alarm after 10 seconds from current time
        //so here we get
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.SECOND, 5);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        System.out.println(calendar.getTimeInMillis());
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 0, pendingIntent);

        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.hour), hour);
        editor.putInt(getString(R.string.minute), minute);
        editor.apply();

        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Receiver.class);
        intent.setAction("Broadcast");
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        System.out.println("alarm set for "+sharedPref.getInt(getString(R.string.hour),20)+":"+sharedPref.getInt(getString(R.string.minute),0));
        // Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, sharedPref.getInt(getString(R.string.hour),20));
        calendar.set(Calendar.MINUTE, sharedPref.getInt(getString(R.string.minute),0));
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTimeInMillis()-System.currentTimeMillis() );
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 * 60 * 24, pendingIntent);
    }
}