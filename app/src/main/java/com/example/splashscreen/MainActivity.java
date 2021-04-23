package com.example.splashscreen;

import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends LinkingFunctions {

    public static boolean exerciseDone;
    public TextView tv1;

    private String complete = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old_layout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();

        tv1 = (TextView) findViewById(R.id.textViewExerciseComplete);
        if(exerciseDone == true) {
            complete = "Today's practice has been completed. ";
            //complete += new String(Character.toChars(2B1C));
        }
        else {
            complete += "Today's practice has not yet been completed.";
            //complete += new String(Character.toChars(2705));

        }
        tv1.setText(complete);

    }




}