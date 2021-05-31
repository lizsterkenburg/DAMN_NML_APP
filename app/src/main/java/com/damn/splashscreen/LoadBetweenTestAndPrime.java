package com.damn.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoadBetweenTestAndPrime extends AppCompatActivity {

    private Handler handler;
    private SharedPreferences sharedPref;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        ImageView logo = findViewById(R.id.imageView6);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("true")){
            Drawable myDrawable = getResources().getDrawable(R.drawable.logo_damn);
            logo.setImageDrawable(myDrawable);
        }
        else{
            Drawable myDrawable = getResources().getDrawable(R.drawable.green_owl_no_text);
            logo.setImageDrawable(myDrawable);
        }

        handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(LoadBetweenTestAndPrime.this, PrimeActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 1500 + (int)(Math.random() * 500));

    }


    @Override
    public void onBackPressed() {
        System.out.println("cancel");
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onBackPressed();
    }


}