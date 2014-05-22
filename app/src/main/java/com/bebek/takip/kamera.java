package com.bebek.takip;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yasemin on 5/1/14.
 */
public class kamera extends Activity {

    private static final String TAG = "Bebektakibi";
    public static final String PREF_NAME = "com.bebek.takip.kamera";
    // private static int smsLength = 124;

    String smsContent="Bebeğiniz uyanabilir";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bebekodasi);
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);

        ((EditText)findViewById(R.id.editTelNo)).setText(settings.getString("phoneNumber", ""));

                String phoneNumber = ((EditText)findViewById(R.id.editTelNo)).getText().toString();
              //  String smsContent = ((EditText)findViewById(R.id.sms_content)).getText().toString();
                if(phoneNumber.length() == 0 ){
                    Toast.makeText(getApplicationContext(),
                            "Lütfen telefon no'su giriniz", Toast.LENGTH_LONG).show();
                   // String msg = (String) getText(R.string.empty_phone_sms_field);
                    //Toast.makeText(SmartSurveillanceActivity.this, msg, Toast.LENGTH_SHORT).show();
                }

                else{

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("phoneNumber", phoneNumber);
                    editor.putString("SMSContent", smsContent);
                    editor.commit();
                    Intent startMotionDetection = new Intent("com.bebek.takip.hareket.monitor.MONITOR");
                    startActivity(startMotionDetection);
                }

            }

    @Override
    public void onResume(){
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause(){
        Log.e(TAG, "onPause");
        super.onPause();
    }
}