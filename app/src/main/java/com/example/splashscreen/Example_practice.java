package com.example.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class Example_practice extends LinkingFunctions {

    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);

        ImageView logo = findViewById(R.id.imageView3);
        if(sharedPref.getString(getString(R.string.which_logo), "None").equals("true")){
            Drawable myDrawable = getResources().getDrawable(R.drawable.logo_damn);
            logo.setImageDrawable(myDrawable);
        }
        else{
            Drawable myDrawable = getResources().getDrawable(R.drawable.green_owl_no_text);
            logo.setImageDrawable(myDrawable);
        }

        setContentView(R.layout.activity_example_practice);
    }
}