package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LoadBetweenTestAndPrime extends AppCompatActivity {

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(LoadBetweenTestAndPrime.this, PrimeActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 1500);
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