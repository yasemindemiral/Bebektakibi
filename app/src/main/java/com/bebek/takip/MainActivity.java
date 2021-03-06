package com.bebek.takip;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Button;




public class MainActivity extends Activity {
    public Button buttonbasla;
    public Button exit;
    public Button developers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        Notify("Bebek Takibi:", " Hoşgeldiniz..");
        //  Drawable loginActivityBackground = findViewById(R.id.layout).getBackground();
        //loginActivityBackground.setAlpha(127);
        exit= (Button) findViewById(R.id.cikis);
        exit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });

        buttonbasla = (Button) findViewById(R.id.buttonBasla);

        //Listening to button event
        buttonbasla.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), secimyapilan.class);

                // starting new activity
                startActivity(nextScreen);

            }
        });
        developers = (Button) findViewById(R.id.developers);
        developers.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), developers.class);

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

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        notification.setLatestEventInfo(MainActivity.this, notificationTitle,
                notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
    }

    @Override
    public void onBackPressed()
    {

        //thats it
    }


        }





