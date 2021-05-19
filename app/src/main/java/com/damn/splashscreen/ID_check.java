package com.damn.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ID_check extends LinkingFunctions {
    private SharedPreferences sharedPref;
    EditText idInput;
    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_d_check);

        sharedPref = this.getSharedPreferences(getString(R.string.notifaction), Context.MODE_PRIVATE);

        idInput = (EditText) findViewById(R.id.inputFieldUSerID);
        okButton = (Button) findViewById(R.id.usernameDoneButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                if(idInput.getText().length() > 0) {
                    editor.putString(getString(R.string.user_ID),idInput.getText().toString());
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please submit your user ID from Prolific in the text field.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}