package com.damn.splashscreen;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataStorer {


    public int writeFile(String fileName, String message, String ID, String sessionType, String photoName, Context context) throws IOException {
        try {
            if (!message.equals( "-1")) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_APPEND));
                outputStreamWriter.append(message + " ; " + ID + " ; " + sessionType+ " ; " + photoName + "\n");
                outputStreamWriter.close();
            }

            String fileText = readFromFile(context, fileName);
            String[] sentences = fileText.split("\n");

            System.out.println("file");
            System.out.println(fileText);

            return sentences.length;

        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return -1;
        }
    }

    public String readFromFile(Context context, String filename) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    System.out.println(receiveString);
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            return ret;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
