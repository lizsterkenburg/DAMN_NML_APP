package com.example.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;

public class Example_practice2 extends LinkingFunctions {
    private Button quickPractice;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_practice2);
        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        quickPractice = findViewById(R.id.button7);
        quickPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(getString(R.string.number_of_practices), 2);//6);
                editor.putString(getString(R.string.which_practice), "example");
                editor.apply();
                Intent i = new Intent(getApplicationContext(), PrimeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}