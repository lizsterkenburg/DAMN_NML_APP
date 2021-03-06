package com.damn.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Sound_check extends LinkingFunctions {
    private Button soundButton;
    private Button baselineButton;
    private Button practiceButton;
    private SharedPreferences sharedPref;
    private final int NUMBER_OF_PRACTICES_BASELINE = 21; //21
    private final int NUMBER_OF_PRACTICES_PRACTICE = 53; //53

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_check);
        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();

        ImageView logo = findViewById(R.id.imageView3);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("true")){
            Drawable myDrawable = getResources().getDrawable(R.drawable.logo_damn);
            logo.setImageDrawable(myDrawable);
        }
        else{
            Drawable myDrawable = getResources().getDrawable(R.drawable.green_owl_no_text);
            logo.setImageDrawable(myDrawable);
        }
        soundButton = findViewById(R.id.sound_check);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 450);
                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
            }
        });

        practiceButton = (Button)  findViewById(R.id.testNewNameToActualPractice);
        practiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(getString(R.string.exercise_number), 1);
                editor.putStringSet(getString(R.string.used_names), null);
                editor.putString(getString(R.string.which_practice), "practice");
                editor.putInt(getString(R.string.number_of_practices), NUMBER_OF_PRACTICES_PRACTICE);
                editor.apply();
                Intent i = new Intent(getApplicationContext(), PrimeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        baselineButton = (Button) findViewById(R.id.baselineButton);
        baselineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(getString(R.string.exercise_number), 1);
                editor.putStringSet(getString(R.string.used_names), null);
                editor.putString(getString(R.string.which_practice), "baseline");
                editor.putInt(getString(R.string.number_of_practices), NUMBER_OF_PRACTICES_BASELINE);
                editor.apply();
                Intent i = new Intent(getApplicationContext(), PrimeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void toPrime(View v){
        Intent i = new Intent(this, PrimeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}