package com.example.splashscreen;

import android.content.Intent;
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

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
    EditText speechText;
    DataStorer dataStorer;
    TextView passiveActive;
    private static final int I = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_start);

        speechButton = (ImageView) findViewById(R.id.button);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == I && resultCode == RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechText.setText(matches.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String isPassive(String m){
        String voice = "";
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            Document doc = Document.newBuilder().setContent(m).setType(Type.PLAIN_TEXT).build();
            AnalyzeSyntaxRequest request =
                    AnalyzeSyntaxRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();
            // analyze the syntax in the given text
            AnalyzeSyntaxResponse response = language.analyzeSyntax(request);
            // print the response
            voice = "try";
            for (Token token : response.getTokensList()) {
                System.out.printf("\tText: %s\n", token.getText().getContent());

                System.out.printf("\tVoice: %s\n", token.getPartOfSpeech().getVoice());
                voice = token.getPartOfSpeech().getVoice().toString();
            }
            //return response.getTokensList();
        } catch (IOException e) {
            voice = e.toString();
            e.printStackTrace();
        }
        return voice;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toSplash(View v){
        String inputText = speechText.getText().toString();
        if (inputText.isEmpty()) {
            // TODO: add popup
            return;
        }
        else
        {

            String result = isPassive(inputText);
            dataStorer.writeFile(java.time.LocalDate.now().toString(), inputText, result);
            passiveActive.setText(result);
        }

        //Intent i = new Intent(this, SplashActivity.class);
        //startActivity(i);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}