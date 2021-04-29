package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class SettingsActivity extends LinkingFunctions {

    private Switch switch1;
    private TextView alarm;
    private SharedPreferences sharedPref;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switch1 = findViewById(R.id.switch1);
        alarm = findViewById(R.id.alarm);

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        Boolean notificationState = sharedPref.getBoolean(getString(R.string.notifaction_state), true);

        alarm.setText(sharedPref.getInt(getString(R.string.hour),20) + ":" + sharedPref.getInt(getString(R.string.minute),0));

        if(notificationState) {
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }

        switch1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                Boolean state = switch1.isChecked();
                editor.putBoolean(getString(R.string.notifaction_state), state);
                editor.apply();
            }
        });

    }
    @SuppressLint("SetTextI18n")
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePicker(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}