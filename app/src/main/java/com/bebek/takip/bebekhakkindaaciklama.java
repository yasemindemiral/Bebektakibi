package com.bebek.takip;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Button;



/**
 * Created by yasemin on 4/27/14.
 */
public class bebekhakkindaaciklama extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebekhakkindaaciklama);
        Notify("Bebek Takibi:" ,"Bebeğiniz Takip Ediliyor..");


    }
    @SuppressWarnings("deprecation")
    private void Notify(String notificationTitle, String notificationMessage) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new Notification(R.drawable.ic_launcher,
                "Bebek Takibi", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, bebekhakkindaaciklama.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        notification.setLatestEventInfo(bebekhakkindaaciklama.this, notificationTitle,
                notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
    }



}
