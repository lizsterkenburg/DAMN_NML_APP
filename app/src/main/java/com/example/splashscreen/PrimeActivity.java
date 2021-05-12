package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class PrimeActivity extends LinkingFunctions {
    private ImageView prime;
//    private EditText speechText;
    private TextView verbPrime;
    private DataStorer dataStorer;
    private Context context;
    private Handler handlerTransition;
    private MediaPlayer player;
    private SharedPreferences sharedPref;
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
        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

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
        String[] verbArray = {verb};
        String[] emptySet = {};

        Set<String> sn = new HashSet<String>(Arrays.asList(verbArray));
        Set<String> es = new HashSet<String>(Arrays.asList(verbArray));
        Set<String> newSet = combineSets(sharedPref.getStringSet(getString(R.string.used_names), es), sn);
        editor.putStringSet(getString(R.string.used_names), newSet);
        editor.apply();

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

    public Set<String> combineSets(Set<String> a, Set<String> b){
        // Creating an empty set
        Set<String> mergedSet = new HashSet<String>();

        // add the two sets to be merged
        // into the new set
        mergedSet.addAll(a);
        mergedSet.addAll(b);

        // returning the merged set
        return mergedSet;
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
        ArrayList<String> resource_names = new ArrayList<>();

        if (voice.equals("active")) {
            for (Field field : fields) {
                if (field.getName().contains("tra") && field.getName().contains("act")) {
                    resource_names.add(field.getName());
                }
            }

        } else if (voice.equals("passive")) {
            for (Field field : fields) {
                if (field.getName().contains("tra") && field.getName().contains("pass")) {
                    resource_names.add(field.getName());
                }
            }
        } else if (voice.equals("int")){
            for (Field field : fields) {
                if (field.getName().contains("int")) {
                    resource_names.add(field.getName());
                }
            }
        } else {
            return "NaN";
        }


        Set<String> used_names = sharedPref.getStringSet(getString(R.string.used_names), null);
        ArrayList<String> toRemove = new ArrayList<>();
        System.out.println(used_names);
        System.out.println(resource_names);
        if(used_names!=null) {
            for (String name : resource_names) {
                String[] words = name.split("_");
                String verb = words[1];
                if (used_names.contains(verb)) {
                    toRemove.add(name);
                }
            }
        }
        resource_names.removeAll(toRemove);
        System.out.println(resource_names);

        int index = (int) (Math.random() * resource_names.size());
        return resource_names.get(index);
    }

    @Override
    public void toHome(View v){
        player.stop();
        if(handlerTransition!=null){
            handlerTransition.removeCallbacksAndMessages(null);
        } else {
            handlerTransition = null;
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void toSettings(View v){
        player.stop();
        if(handlerTransition!=null){
            handlerTransition.removeCallbacksAndMessages(null);
        } else {
            handlerTransition = null;
        }
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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