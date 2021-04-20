package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//import com.google.cloud.language.v1.Document;

public class PracticeActivity extends LinkingFunctions {
    private ImageView speechButton;
    private ImageView test;
    private EditText speechText;
    private DataStorer dataStorer;
    private TextView verbTest;
    private Context context;
    private static final int I = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_start);
        context = getApplicationContext();
        speechButton = (ImageView) findViewById(R.id.button);
        test = (ImageView) findViewById(R.id.imageView_show_test);
        speechText = (EditText) findViewById(R.id.editText);
        dataStorer = new DataStorer();
        verbTest = findViewById(R.id.verb_test);

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speech to text");
                startActivityForResult(speechIntent, I);
            }
        });

        // randomly select audio and corresponding image
        String soundName;
        if (Math.random() > 0.5) {
            soundName = getSound("active");
        } else {
            soundName = getSound("passive");
        }

        String[] words = soundName.split("_");
        String verb = words[1];
        verbTest.setText(verb);

        // set image corresponding to sound
        test.setImageDrawable(getImage(soundName));
    }

    private Drawable getImage(String base) {
        if (base.contains("act")) {
            base = base.replace("_act", "");
        }

        if (base.contains("pass")) {
            base = base.replace("_pass", "");
        }

        Field[] fields = R.drawable.class.getDeclaredFields();
        ArrayList<String> recource_names = new ArrayList<>();

        for (Field field : fields) {
            if (field.getName().contains(base)) {
                recource_names.add(field.getName());
            }
        }

        String name = recource_names.get(0);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getResources().getDrawable(getResources().getIdentifier(name, "drawable", getPackageName()));
        return res;
    }

    private String getSound(String voice) {
        Field[] fields = R.raw.class.getDeclaredFields();
        ArrayList<String> recource_names = new ArrayList<>();

        if (voice.equals("active")) {
            for (Field field : fields) {
                if (field.getName().contains("tra") && field.getName().contains("act")) {
                    recource_names.add(field.getName());
                }
            }
            int index = (int) (Math.random() * recource_names.size());
            return recource_names.get(index);
        } else if (voice.equals("passive")) {
            for (Field field : fields) {
                if (field.getName().contains("tra") && field.getName().contains("pass")) {
                    recource_names.add(field.getName());
                }
            }
            int index = (int) (Math.random() * recource_names.size());
            return recource_names.get(index);
        } else {
            return "NaN";
        }
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == I && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechText.setText(matches.get(0).toString());

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
            DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");

            String filename = "Results analysis on " + date.format(now);
            String message = date2.format(now) + matches.get(0).toString();
            try {
                dataStorer.writeFile(filename, message, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Handler handlerTransition = new Handler();
            handlerTransition.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(PracticeActivity.this, LoadBetweenTestAndPrime.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }, 1000);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toSplash(View v) throws IOException {
        String inputText = speechText.getText().toString();
        if (inputText.isEmpty()) {
            // TODO: add popup
            return;
        } else {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
            DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");

            String filename = "Results analysis on " + date.format(now);
            String message = date2.format(now) + inputText;
            dataStorer.writeFile(filename, message, context);
        }

        //Intent i = new Intent(this, SplashActivity.class);
        //startActivity(i);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}