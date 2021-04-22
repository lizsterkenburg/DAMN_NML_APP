package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends LinkingFunctions {

    public static boolean exerciseDone;
    public TextView tv1;

    private String complete = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        tv1 = (TextView) findViewById(R.id.textViewExerciseComplete);
        if(exerciseDone == true) {
            complete = "Today's practice has been completed. ";
            //complete += new String(Character.toChars(2B1C));
        }
        else {
            complete += "Today's practice has yet to be completed.";
            //complete += new String(Character.toChars(2705));

        }
        tv1.setText(complete);

    }




}