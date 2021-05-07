package com.example.splashscreen;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LinkingFunctions extends AppCompatActivity {
    public void toHome(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void toSettings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void toPractice(View v){
        Intent i = new Intent(this, PracticeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    public void toHTP(View v){
        Intent i = new Intent(this, HowToPlayActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void toHelp(View v){
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void toPrime(View v){
        Intent i = new Intent(this, PrimeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void toLoadBetweenPrimeAndTest(View v){
        Intent i = new Intent(this, LoadBetweenPrimeAndTest.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    public void toLoadBetweenTestAndPrime(View v){
        Intent i = new Intent(this, LoadBetweenTestAndPrime.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void toPracticeDone(View v){
        Intent i = new Intent(this, PracticeDone.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }

    public void toPracticeStartExplanation(View v){
        Intent i =  new Intent(this,PracticeActivityStartScreen.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    public void toPracticeExample(View v){
        Intent i =  new Intent(this,Example_practice.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

}
