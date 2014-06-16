package com.bebek.takip;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yasemin on 5/15/14.
 */
public class developers extends Activity {

    Button yasemin;
Button github;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers);

        yasemin = (Button) findViewById(R.id.yasemin);

        //Listening to button event
        yasemin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://ydemiral.blogspot.com"));
                startActivity(myWebLink);
            }
        });
        github= (Button) findViewById(R.id.github);
       github.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://github.com/yasemindemiral/Bebektakibi"));
                startActivity(myWebLink);
            }
        });
    }
}
