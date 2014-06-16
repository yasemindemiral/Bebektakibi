package com.bebek.takip;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yasemin on 4/30/14.
 */


class GmailAuthenticator extends Authenticator{
    String user;
    String pw;
    public GmailAuthenticator (String username, String password){
        super();
        this.user= username;
        this.pw = password;

    }
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(user, pw);
    }

}

public class bebekodasi extends Activity {
    private static final String TAG = "Bebektakibi";
    public static final String PREF_NAME = "com.bebek.takip.bebekodasi";
    // private static int smsLength = 124;

    String smsContent = "Bebeğiniz uyanabilir";
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebekodasi);
        final EditText editOnayla = (EditText) findViewById(R.id.editOnayla);
        final EditText editEposta = (EditText) findViewById(R.id.editEposta);
        final EditText invisible = (EditText) findViewById(R.id.editText);

        Button buttoniptal =(Button) findViewById(R.id.buttonIptal);
        final Button buttontamam = (Button) findViewById(R.id.buttonTamam);
        final Button buttonOnayla = (Button) findViewById(R.id.buttonOnayla);
        final TextView textView = (TextView) findViewById(R.id.textOnayla);

        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);

       // ((EditText) findViewById(R.id.editTelNo)).setText(settings.getString("phoneNumber", ""));

        String phoneNumber = ((EditText) findViewById(R.id.editTelNo)).getText().toString();
        //  String smsContent = ((EditText)findViewById(R.id.sms_content)).getText().toString();
        if (phoneNumber.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Lütfen telefon no'su giriniz", Toast.LENGTH_LONG).show();

        } else {

            SharedPreferences.Editor editor = settings.edit();
            editor.putString("phoneNumber", phoneNumber);
            editor.putString("SMSContent", smsContent);
            editor.commit();


        }



        buttontamam.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                final Random numGen = new Random(); //Random sayı oluşturmak için olan kısım
                final Integer rndnumber = numGen.nextInt(1000);
                //txt.setText(String.valueOf(rNumber));
                final String rNumber = rndnumber.toString();
                invisible.setText(String.valueOf(rndnumber));
                textView.setVisibility(View.VISIBLE);
                editOnayla.setVisibility(View.VISIBLE);
                buttonOnayla.setVisibility(View.VISIBLE);




                final String username = "bebektakibi@gmail.com";
                final String password = "bebektakibi2014";
                final String posta = editEposta.getText().toString();

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new GmailAuthenticator(username, password));
                final Message message = new MimeMessage(session);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {

                        try {
                            // message.setFrom(new InternetAddress("yasmindemiral@gmail.com"));
                            message.setFrom(new InternetAddress(posta));
                            message.setRecipients(Message.RecipientType.TO,
                                    //  InternetAddress.parse("yasmindemiral@gmail.com"));
                                    InternetAddress.parse(posta));
                            message.setSubject("Kullanıcı Kodu");
                            // message.setText("Test");
                            message.setText(rNumber);
                            Transport.send(message);



                        } catch (AddressException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                };
                Thread t = new Thread(r);
                t.start();
                Toast.makeText(getApplicationContext(),
                        "Lütfen Gelen Kutunuzu Kontrol Ediniz", Toast.LENGTH_LONG).show();


            }
        });

        buttonOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String randomsayı = invisible.getText().toString();
                    final String onaykodu = editOnayla.getText().toString();

                    if (randomsayı.equals(onaykodu)) {
                        Toast.makeText(getApplicationContext(),
                                "Doğru", Toast.LENGTH_LONG).show();
                        Intent startMotionDetection = new Intent("com.bebek.takip.hareket.monitor.MONITOR");
                        startActivity(startMotionDetection);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Yanlış", Toast.LENGTH_LONG).show();
                    }

            }

        });
        buttoniptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}