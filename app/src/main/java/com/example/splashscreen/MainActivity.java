package com.example.splashscreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
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
        int number_trials = 21;
        DataStorer dataStorer = new DataStorer();
        setContentView(R.layout.activity_main);

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.number_of_practices), number_trials);//1);//21);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("None")){
            if(Math.random() > 0.5){
                editor.putString(getString(R.string.which_logo), "true");
            }
            else{
                editor.putString(getString(R.string.which_logo), "false");
            }
        }
        editor.apply();

        ImageView logo = findViewById(R.id.logo_home);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("true")){
            Drawable myDrawable = getResources().getDrawable(R.drawable.logo_damn);
            logo.setImageDrawable(myDrawable);
        }
        else{
            Drawable myDrawable = getResources().getDrawable(R.drawable.green_owl_no_text);
            logo.setImageDrawable(myDrawable);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
        DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");

        String filename = "Results analysis on "+ "baseline " + date.format(now);

        int counter = 0;
        try {
            counter = dataStorer.writeFile(filename, "-1", getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
        // TODO fill in real number of trials for mail sending
        tv1 = (TextView) findViewById(R.id.textViewExerciseComplete);
        if (counter >= 3){//21) { //here
            complete = "Today's practice has been completed. ";
            //complete += new String(Character.toChars(2B1C));
        } else {
            complete += "Today's practice has not yet been completed.";
            //complete += new String(Character.toChars(2705));
        }
        tv1.setText(complete);
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