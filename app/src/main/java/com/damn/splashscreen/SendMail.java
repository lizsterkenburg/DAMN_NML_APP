package com.damn.splashscreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

    String email_to = "damn.experiment@gmail.com";
    private SharedPreferences sharedPref;


    public void sendMail(String filePath, String name, Context context) {
        final String username = "damn.experiment";
        final String password = "NMLgroup4";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email_to));


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
            message.setSubject(messageId);
//            message.setText("Message : the results.");
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Message: the results.");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(name);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            new SendMailTask().execute(message);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }



    private class SendMailTask extends AsyncTask<Message, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Failed";
            }



        }
    }
}