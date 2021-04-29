package com.example.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.RequiresApi;

public class SettingsActivity extends LinkingFunctions {

    private Switch switch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switch1 = findViewById(R.id.switch1);


        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        Boolean notificationState = sharedPref.getBoolean(getString(R.string.notifaction_state), true);

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
}