package com.bebek.takip.hareket.monitor;

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

import com.bebek.takip.R;

/**
 * Created by yasemin on 5/3/14.
 */
public class SmartSurveillanceActivity extends Activity{
    private static final String TAG = "SurvCamTest";
    public static final String PREF_NAME = "com_bebek_takip_hareket_monitor_SmartSurveillance";
    private static int smsLength = 124;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hareket);
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);

        ((EditText)findViewById(R.id.phone_number)).setText(settings.getString("phoneNumber", ""));
        TextView charCount = (TextView)findViewById(R.id.message_length);
        final EditText SMS = (EditText)findViewById(R.id.sms_content);
        SMS.setText(settings.getString("SMSContent", ""));
        charCount.setText("Characters remaining: " +(smsLength - SMS.getText().length()));
        TextWatcher smsTextWatcher =new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView charCount = (TextView)findViewById(R.id.message_length);
                charCount.setText("Characters remaining: " +(smsLength - s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        SMS.addTextChangedListener(smsTextWatcher);
        Log.e(TAG, "onCreate");
        ImageButton startButton = (ImageButton)findViewById(R.id.startbutton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.e(TAG, "onClick");
                String phoneNumber = ((EditText)findViewById(R.id.phone_number)).getText().toString();
                String smsContent = ((EditText)findViewById(R.id.sms_content)).getText().toString();
                if(phoneNumber.length() == 0 || smsContent.length() == 0){
                    String msg = (String) getText(R.string.empty_phone_sms_field);
                    Toast.makeText(SmartSurveillanceActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                else if(smsContent.length() > 124){
                    String msg = "Message is too long.";//(String) getText(R.string.empty_phone_sms_field);
                    Toast.makeText(SmartSurveillanceActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("phoneNumber", phoneNumber);
                    editor.putString("SMSContent", smsContent);
                    editor.commit();
                    Intent startMotionDetection = new Intent("com.neu.nur.MONITORMOTIONACTIVITY");
                    startActivity(startMotionDetection);
                }
				/*Button b = (Button)arg0;
				String buttonState = b.getText().toString();
				if(buttonState.equalsIgnoreCase("start")){
					b.setText("Stop");
					timer = new Timer();
					timer.scheduleAtFixedRate(
							new ShootPictureTask(SmartSurveillanceActivity.this),
							DELAY, REPEATE);
				}
				else{
					b.setText("Start");
					timer.cancel();
				}*/
            }
        });


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

