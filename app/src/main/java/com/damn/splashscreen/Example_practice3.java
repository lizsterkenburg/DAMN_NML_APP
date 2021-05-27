package com.damn.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class Example_practice3 extends LinkingFunctions {

    private SharedPreferences sharedPref;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_practice3);
        Context context = getApplicationContext();

        sharedPref = context.getSharedPreferences(context.getString(R.string.notifaction), Context.MODE_PRIVATE);
        String logo = sharedPref.getString(context.getString(R.string.which_practice),"none"); //true = logo, false = owl
        String logoName = "";
        if(logo.equals("true")) {
            logoName = "logo";
        } else {
            logoName = "owl";
        }
        String user = sharedPref.getString(context.getString(R.string.user_ID),"not submitted");
        String sessionType = sharedPref.getString(context.getString(R.string.which_practice), "null");
        String messageId = "Sent from DAMN App by " + user + " - Logo = " + logoName + " - session = "+ sessionType;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ofPattern(" yyyy-MM-dd ");
        String filename = "Results analysis on " + sharedPref.getString(getString(R.string.which_practice), "null") + " " + date.format(now);
        String filePath = context.getFileStreamPath(filename).toString();
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);

        Button sendResults = findViewById(R.id.mail);
        sendResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:damn.experiment@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, messageId);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent, "Send email..."),12);
                }
            }
        });

    }
}