package com.example.splashscreen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    String email_to = "damn.experiment@gmail.com";

    public void sendMail() {
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
            message.setSubject("Sent from DAMN App");
            message.setText("Message : the results.");

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
            } catch (SendFailedException ee) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                return "error1";
            } catch (MessagingException e) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                return "error2";
            }

        }
    }
}
