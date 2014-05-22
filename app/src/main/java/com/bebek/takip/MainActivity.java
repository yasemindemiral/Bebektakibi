package com.bebek.takip;

import android.app.Activity;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



public class MainActivity extends Activity {
    public ImageButton buttonbasla;
    public ImageButton exit;
    public ImageButton developers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
      //  Drawable loginActivityBackground = findViewById(R.id.layout).getBackground();
        //loginActivityBackground.setAlpha(127);



        buttonbasla = (ImageButton) findViewById(R.id.buttonBasla);

        //Listening to button event
        buttonbasla.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), secimyapilan.class);

                // starting new activity
                startActivity(nextScreen);

            }
        });
        developers = (ImageButton) findViewById(R.id.developers);
        developers.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), developers.class);

                // starting new activity
                startActivity(nextScreen);

            }
        });

        exit= (ImageButton) findViewById(R.id.cikis);
        exit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                    System.exit(0);
                }
            });

        }
    }




