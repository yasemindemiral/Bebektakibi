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
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebekodasi);
        final EditText editOnayla = (EditText) findViewById(R.id.editOnayla);
        final EditText editEposta = (EditText) findViewById(R.id.editEposta);
        final EditText invisible = (EditText) findViewById(R.id.editText);
        Button buttontamam = (Button) findViewById(R.id.buttonTamam);
        Button buttunOnayla = (Button) findViewById(R.id.buttonOnayla);
        buttontamam.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                final Random numGen = new Random(); //Random sayı oluşturmak için olan kısım
                final Integer rndnumber = numGen.nextInt(1000);
                //txt.setText(String.valueOf(rNumber));
                final String rNumber = rndnumber.toString();
                invisible.setText(String.valueOf(rndnumber));



                final String username = "applockermail@gmail.com";
                final String password = "app192837";
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

        buttunOnayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String randomsayı = invisible.getText().toString();
                final String onaykodu = editOnayla.getText().toString();

                if(randomsayı.equals(onaykodu)){
                    Toast.makeText(getApplicationContext(),
                            "Doğru", Toast.LENGTH_LONG).show();
                    Intent nextScreen = new Intent(getApplicationContext(), kamera.class);
                    startActivity(nextScreen);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Yanlış", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}