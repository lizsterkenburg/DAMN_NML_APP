package com.example.splashscreen;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends LinkingFunctions {

    ViewGroup tContainer;
    TextView ta1;
    Button Button1;
    TextView ta2;
    Button Button2;
    TextView ta3;
    Button Button3;
    TextView ta4;
    Button Button4;
    TextView ta5;
    Button Button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tContainer = findViewById(R.id.transitionContainer);
        ta1 = findViewById(R.id.a1);
        Button1 = findViewById(R.id.textViewQ1);
        ta2 = findViewById(R.id.a2);
        Button2 = findViewById(R.id.textViewQ2);
        ta3 = findViewById(R.id.a3);
        Button3 = findViewById(R.id.textViewQ3);
        ta4 = findViewById(R.id.a4);
        Button4 = findViewById(R.id.textViewQ4);
        ta5 = findViewById(R.id.a5);
        Button5 = findViewById(R.id.textViewQ5);

        Button1.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(tContainer);
                visible = !visible;
                ta1.setVisibility(visible ? View.VISIBLE: View.GONE);

            }
        });

        Button2.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(tContainer);
                visible = !visible;
                ta2.setVisibility(visible ? View.VISIBLE: View.GONE);

            }
        });

        Button3.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(tContainer);
                visible = !visible;
                ta3.setVisibility(visible ? View.VISIBLE: View.GONE);

            }
        });

        Button4.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(tContainer);
                visible = !visible;
                ta4.setVisibility(visible ? View.VISIBLE: View.GONE);

            }
        });

        Button5.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(tContainer);
                visible = !visible;
                ta5.setVisibility(visible ? View.VISIBLE: View.GONE);

            }
        });
        //TextView tv1 = (TextView)findViewById(R.id.tvq1);
    }

}