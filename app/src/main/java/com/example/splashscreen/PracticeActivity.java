package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import org.w3c.dom.Document;
import java.util.ArrayList;
import java.util.Date;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;


public class PracticeActivity extends LinkingFunctions {
    ImageView speechButton;
    EditText speechText;
    DataStorer dataStorer;
    private static final int I = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_start);

        speechButton = (ImageView) findViewById(R.id.button);
        speechText = (EditText)findViewById(R.id.editText);
        dataStorer = new DataStorer();

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

    public boolean isPassive(String m){
        boolean passive = true;
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();
            AnalyzeSyntaxRequest request =
                    AnalyzeSyntaxRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();
            // analyze the syntax in the given text
            AnalyzeSyntaxResponse response = language.analyzeSyntax(request);
            // print the response
            for (Token token : response.getTokensList()) {
                System.out.printf("\tText: %s\n", token.getText().getContent());

                System.out.printf("\tVoice: %s\n", token.getPartOfSpeech().getVoice());

            }
            return response.getTokensList();
        }
        return passive;
    }
    public void toSplash(View v){
        String inputText = speechText.getText().toString();
        if (inputText.isEmpty()) {
            // TODO: add popup
            return;
        }
        else
        {
            String result = "";

            dataStorer.writeFile(java.time.LocalDate.now().toString(), inputText, result);
        }

        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}