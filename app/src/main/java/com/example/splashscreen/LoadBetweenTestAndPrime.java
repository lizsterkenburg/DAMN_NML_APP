package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LoadBetweenTestAndPrime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(LoadBetweenTestAndPrime.this, PrimeActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 1500);
    }


}