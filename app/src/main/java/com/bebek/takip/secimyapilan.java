package com.bebek.takip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yasemin on 4/27/14.
 */
public class secimyapilan extends Activity{
    public Button buttonAile, buttonBebekOdası;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secimyapilan);
        buttonAile = (Button) findViewById(R.id.buttonAile);
        buttonBebekOdası = (Button) findViewById(R.id.buttonBebekOdasi);

        //Listening to button event
        buttonAile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen1 = new Intent(getApplicationContext(), bebekhakkindaaciklama.class);

                // starting new activity
                startActivity(nextScreen1);

            }
        });
        buttonBebekOdası.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen2 = new Intent(getApplicationContext(), bebekodasi.class);

                // starting new activity
                startActivity(nextScreen2);

            }
        });

    }
}
