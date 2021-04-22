package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

public class PracticeDone extends  LinkingFunctions  {

    private String exerciseComplete = "";
    private String exerciseIncomplete = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_done);
        MainActivity.exerciseDone = true;

    }

    public void toAchievements(View v){
        Intent i = new Intent(this, AchievementsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void toHelp(View v){
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}