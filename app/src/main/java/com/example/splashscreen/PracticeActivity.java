package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

//import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.AnalyzeSyntaxRequest;
import com.google.cloud.language.v1.AnalyzeSyntaxResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.EncodingType;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Token;

public class PracticeActivity extends LinkingFunctions {
    ImageView speechButton;
    ImageView prime;
    EditText speechText;
    DataStorer dataStorer;
    TextView passiveActive;
    Context context;
    private static final int I = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_start);
        context = getApplicationContext();
        speechButton = (ImageView) findViewById(R.id.button);
        prime = (ImageView) findViewById(R.id.imageView_show_prime);
        speechText = (EditText)findViewById(R.id.editText);
        dataStorer = new DataStorer();
        passiveActive = findViewById(R.id.passiveActiveText);

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speech to text");
                startActivityForResult(speechIntent,I);
            }
        });

        // get all images and select one randomly
        Field[] fields = R.drawable.class.getDeclaredFields();
        ArrayList<String> recource_names = new ArrayList<>();

        for(Field field : fields)
        {
            if (field.getName().contains("int") || field.getName().contains("tra")) {
                recource_names.add(field.getName());
            }
        }
        int index = (int)(Math.random() * recource_names.size());
        String name = recource_names.get(index);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getResources().getDrawable(getResources().getIdentifier(name,"drawable",getPackageName()));
        prime.setImageDrawable(res);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == I && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechText.setText(matches.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toSplash(View v) throws IOException {
        String inputText = speechText.getText().toString();
        if (inputText.isEmpty()) {
            // TODO: add popup
            return;
        }
        else
        {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
            DateTimeFormatter date2 = DateTimeFormatter.ofPattern("HH:mm:ss ; yyyy/MM/dd ; ");

            String filename = "Results analysis on " + date.format(now);
            String message = date2.format(now) + inputText;
            dataStorer.writeFile(filename, message,context);
            passiveActive.setText(inputText);
        }


        //Intent i = new Intent(this, SplashActivity.class);
        //startActivity(i);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}