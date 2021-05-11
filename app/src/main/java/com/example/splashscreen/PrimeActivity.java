package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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


public class PrimeActivity extends LinkingFunctions {
    private ImageView prime;
//    private EditText speechText;
    private TextView verbPrime;
    private DataStorer dataStorer;
    private Context context;
    private Handler handlerTransition;
    private MediaPlayer player;
    private static final int I = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);
        context = getApplicationContext();
        prime = (ImageView) findViewById(R.id.imageView_show_prime);
        verbPrime = findViewById(R.id.verb_prime);
//        speechText = findViewById(R.id.editText);
        dataStorer = new DataStorer();

        // randomly select audio and corresponding image
        String soundName = "";
        if (Math.random() < 0.25){
            soundName = getSound("int");
        } else {
            if (Math.random() > 0.5) {
                soundName = getSound("active");
            } else {
                soundName = getSound("passive");
            }
        }

        System.out.println(soundName);
        String[] words = soundName.split("_");
        String verb = words[1];
        verbPrime.setText("Verb: to " + verb);
        prime.setImageDrawable(getImage(soundName));

        // play sound corresponding to image
        int sound_id = context.getResources().getIdentifier(soundName, "raw", context.getPackageName());
        player = MediaPlayer.create(context, sound_id);
        player.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    handlerTransition = new Handler();
                    handlerTransition.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(PrimeActivity.this, LoadBetweenPrimeAndTest.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // delays the playing of the audio.
        Handler handlerSound = new Handler();
        handlerSound.postDelayed(new Runnable() {
            @Override
            public void run() {
                player.start();
            }
        }, 1000);
    }
    @Override
    public void onBackPressed() {
        System.out.println("cancel");
        player.stop();
        if(handlerTransition!=null){
            handlerTransition.removeCallbacksAndMessages(null);
        } else {
            handlerTransition = null;
        }

        super.onBackPressed();
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
        } else if (voice.equals("int")){
            for (Field field : fields) {
                if (field.getName().contains("int")) {
                    recource_names.add(field.getName());
                }
            }
            int index = (int) (Math.random() * recource_names.size());
            return recource_names.get(index);
        } else {
            return "NaN";
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == I && resultCode == RESULT_OK) {
//            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            speechText.setText(matches.get(0).toString());
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }


//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void toSplash(View v) throws IOException {
//        String inputText = speechText.getText().toString();
//        if (inputText.isEmpty()) {
//            // TODO: add popup
//            return;
//        } else {
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
//            DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");
//
//            String filename = "Results analysis on " + date.format(now);
//            String message = date2.format(now) + inputText;
//            dataStorer.writeFile(filename, message, context);
//        }
//
//        //Intent i = new Intent(this, SplashActivity.class);
//        //startActivity(i);
//        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//    }

}