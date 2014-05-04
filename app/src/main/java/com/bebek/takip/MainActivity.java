package com.bebek.takip;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    public ImageButton buttonbasla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

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


  //  @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.main, menu);
      //  return true;
    //}

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
     //   int id = item.getItemId();
      //  if (id == R.id.action_settings) {
       //     return true;
        //}
        //return super.onOptionsItemSelected(item);
    //}

}
}
