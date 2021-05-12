package com.example.splashscreen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

//import com.google.cloud.language.v1.Document;

public class PracticeActivity extends LinkingFunctions {
    private ImageView speechButton;
    private ImageView test;
    private DataStorer dataStorer;
    private TextView verbText;
    private TextView emptyBackground;
    private Context context;
    private SendMail sendMail;
    private Handler handler;
    private SpeechRecognizer mSpeechRecognizer;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final int I = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_practice_speak);
        context = getApplicationContext();
        speechButton = (ImageView) findViewById(R.id.button);
        test = (ImageView) findViewById(R.id.imageView_show_test);
//        speechText = (EditText) findViewById(R.id.editText);
        dataStorer = new DataStorer();
        verbText = findViewById(R.id.verb_test);
        emptyBackground = findViewById(R.id.emptyTextForBackground);
        sendMail = new SendMail();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(new recListener());
        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // 12 second timer to give response.
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(PracticeActivity.this, LoadBetweenTestAndPrime.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }, 12000);

        speechButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(handler!=null){
                    handler.removeCallbacksAndMessages(null);
                }
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        emptyBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_completed));
                        break;

                    case MotionEvent.ACTION_DOWN:
                        emptyBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.colored_background));
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                        mSpeechRecognizer.startListening(intent);
                        break;
                }
                return false;
            }
        });

        // randomly select audio and corresponding image
        String soundName;
        if (Math.random() > 0.5) {
            soundName = getSound("active");

        } else {
            soundName = getSound("passive");
        }
        System.out.println("practice "+soundName);
        String[] words = soundName.split("_");
        String verb = words[1];
        String verb_text = "Verb: " + verb;
        verbText.setText(verb_text);
        String[] verbArray = {verb};

        Set<String> sn = new HashSet<String>(Arrays.asList(verbArray));
        Set<String> newSet = combineSets(sharedPref.getStringSet(getString(R.string.used_names), null), sn);
        editor.putStringSet(getString(R.string.used_names), newSet);
        editor.apply();

        // set image corresponding to sound
        test.setImageDrawable(getImage(soundName));
    }

    // recognitionListener
    class recListener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            System.out.println("ready");
        }
        public void onBeginningOfSpeech()
        {
            System.out.println("beginning");
        }
        public void onRmsChanged(float rmsdB)
        {

        }
        public void onBufferReceived(byte[] buffer)
        {

        }
        public void onEndOfSpeech()
        {
            System.out.println("end");
        }
        public void onError(int error)
        {
            System.out.println(error);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onResults(Bundle bundle)
        {
            //getting all the matches
            ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            // actual code
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
            DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");

            String filename = "Results analysis on " + date.format(now);
            String message = date2.format(now) + matches.get(0).toString();
            int counter = 0;
            try {
                counter = dataStorer.writeFile(filename, message, context);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(counter);
            // TODO fill in real number of trials for mail sending
            if(counter % sharedPref.getInt(getString(R.string.number_of_practices),21) == 0){
                editor.putStringSet(getString(R.string.used_names), null);
                editor.apply();
                System.out.println(context.getFileStreamPath(filename));
                sendMail.sendMail(context.getFileStreamPath(filename).toString(), filename);

                Intent i = new Intent(context,PracticeDone.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            else {
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
        }
        public void onPartialResults(Bundle partialResults)
        {

        }
        public void onEvent(int eventType, Bundle params)
        {

        }
    }


    @Override
    public void onBackPressed() {
        System.out.println("cancel");
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
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

        } else {
            return "NaN";
        }

        Set<String> used_names = sharedPref.getStringSet(getString(R.string.used_names), null);
        System.out.println(used_names);
        System.out.println(resource_names);
        ArrayList<String> toRemove = new ArrayList<>();
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
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    @Override
    public void toSettings(View v){
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}