package com.example.splashscreen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataStorer {


    public void writeFile(String fileName, String message, String result) {
        try {
            File file = new File(fileName+".txt");
            FileWriter myWriter = new FileWriter(fileName + ".txt", file.exists());

            myWriter.write(message);
            myWriter.write(" ; " + result + "\n");
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
