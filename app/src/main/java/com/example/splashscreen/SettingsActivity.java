package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class SettingsActivity extends LinkingFunctions {

    private Switch switch1;
    private TextView alarm;
    private SharedPreferences sharedPref;
    private TextView tv;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switch1 = findViewById(R.id.switch1);
        alarm = findViewById(R.id.alarm);

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        Boolean notificationState = sharedPref.getBoolean(getString(R.string.notifaction_state), true);

        int hourLength = (sharedPref.getInt(getString(R.string.hour),20) + "").length();
        int minute_length = (sharedPref.getInt(getString(R.string.minute), 0) + "").length();
        System.out.println(minute_length);
        alarm.setText(((hourLength<2) ? "0" : "" ) + sharedPref.getInt(getString(R.string.hour),20) + ":" +
                      ((minute_length<2) ? "0" : "") + sharedPref.getInt(getString(R.string.minute),0));

        if(notificationState) {
            switch1.setChecked(true);
        } else {
            switch1.setChecked(false);
        }

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);

        ImageView logo = findViewById(R.id.imageView3);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("true")){
            Drawable myDrawable = getResources().getDrawable(R.drawable.logo_damn);
            logo.setImageDrawable(myDrawable);
        }
        else{
            Drawable myDrawable = getResources().getDrawable(R.drawable.green_owl_no_text);
            logo.setImageDrawable(myDrawable);
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

        String id = sharedPref.getString(getString(R.string.user_ID),"");
        tv = (TextView) findViewById(R.id.usernametest);
        tv.setText(id);

    }
    @SuppressLint("SetTextI18n")
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePicker(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}