package com.bebek.takip;

import android.service.textservice.SpellCheckerService;

import java.net.PasswordAuthentication;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by yasemin on 4/30/14.
 */
public class GMailSender extends Authenticator {
    private String mailhost = "smtp.gmail.com" ;
    private  String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new provider.JSSEProvider());
    }
    public GMailSender(String user , String password){
        this.user = user;
        this.password=password;

        Properties pros = new Properties();
        pros.setProperty("mail.transport.protocol", "smtp");
        pros.setProperty("mailhost", mailhost);
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.port" ,"456");
        pros.put("mail.smtp.socketFactory.port", "465");
        pros.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        pros.put("mail.smtp.socketFactory.fallback", "false");
        pros.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(pros,this);
    }
    //protected PasswordAuthentication getPasswordAuthentication(){
      //  return  new PasswordAuthentication(user, password);
//}



    public synchronized void sendMail(String subject , String body, String sender , String recipients) throws Exception{
        try {
            MimeMessage message = new MimeMessage(session);
            DataHandler handler =new DataHandler(new ByteArrayDataSource(body.getBytes(),  "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);
            if(recipients.indexOf(',')>0)
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipients));
            else
                message.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(recipients)));
            Transport.send(message);

        }
        catch (Exception e){

        }



}
}
