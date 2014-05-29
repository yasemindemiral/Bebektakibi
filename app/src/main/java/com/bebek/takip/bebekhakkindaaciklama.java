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
import android.widget.ImageButton;

import com.bebek.takip.canlıekrangörüntüsü.FarCamActivity;

/**
 * Created by yasemin on 4/27/14.
 */
public class bebekhakkindaaciklama extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebekhakkindaaciklama);
        Notify("Bebek Takibi:" ,"Bebeğiniz Takip Ediliyor..");

        ImageButton gor = (ImageButton)findViewById(R.id.buttonIzleniyor);
        gor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
              //  String url = "http://ydemiral.blogspot.com";
                //Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(url));
                //startActivity(i);
                Intent nextScreen = new Intent(getApplicationContext(), FarCamActivity.class);

                // starting new activity
                startActivity(nextScreen);
            }





        });
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

    @Override
    public void onBackPressed()
    {

        //thats it
    }

}
