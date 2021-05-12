package com.example.splashscreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainActivity extends LinkingFunctions {

    public static boolean exerciseDone;
    public TextView tv1;
    private String complete = "";
    private SharedPreferences sharedPref;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        DataStorer dataStorer = new DataStorer();
        setContentView(R.layout.activity_main);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
//        getSupportActionBar().hide();

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.number_of_practices), 21);
        editor.apply();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
        DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");

        String filename = "Results analysis on " + date.format(now);

        int counter = 0;
        try {
            counter = dataStorer.writeFile(filename, "-1", getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
        // TODO fill in real number of trials for mail sending
        tv1 = (TextView) findViewById(R.id.textViewExerciseComplete);
        if (counter == sharedPref.getInt(getString(R.string.number_of_practices), 21)) {
            complete = "Today's practice has been completed. ";
            //complete += new String(Character.toChars(2B1C));
        } else {
            complete += "Today's practice has not yet been completed.";
            //complete += new String(Character.toChars(2705));
        }
        tv1.setText(complete);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void toPracticeStartExplanation(View v) {
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (sharedPref.getBoolean(getString(R.string.first_practice), true)) {

            editor.putBoolean(getString(R.string.first_practice), false);
            editor.apply();

            Intent i = new Intent(this, HowToPlayActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            Intent i = new Intent(this, PracticeActivityStartScreen.class);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

}