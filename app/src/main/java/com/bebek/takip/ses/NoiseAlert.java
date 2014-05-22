package com.bebek.takip.ses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;


import com.bebek.takip.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//thresold 8 ; 8 i geçince kırmızı oluyor.
/**
 * Created by yasemin on 5/2/14.
 */
public class NoiseAlert extends Activity  {

    public static long lastSMSsentAt = 0;

    private static final String TAG = "NoiseAlert";
    public static final String PREF_NAME = "com.bebek.takip.ses.NOISE";
    private static SurfaceView preview = null;
    public static String smsContent = null;
    public static String phoneNumber = null;
    public  static NoiseAlert ref= null;
    /* constants */
    private static final int POLL_INTERVAL = 300;

    /** running state **/
    private boolean mRunning = false;

    /** config state **/
    private int mThreshold;

    private PowerManager.WakeLock mWakeLock;

    private Handler mHandler = new Handler();

    /* References to view elements */
    private TextView mStatusView;
   // private SoundLevelView mDisplay;

    /* sound data source */
    private SoundMeter mSensor;

    /****************** Define runnable thread again and again detect noise *********/

    private Runnable mSleepTask = new Runnable() {
        public void run() {

            start();
        }
    };

    // Create runnable thread to Monitor Voice
    private Runnable mPollTask = new Runnable() {
        public void run() {

            double amp = mSensor.getAmplitude();
            //Log.i("Noise", "runnable mPollTask");
           // updateDisplay("Monitoring Voice...", amp);

            if ((amp > mThreshold)) {
                callForHelp();
                //Log.i("Noise", "==== onCreate ===");

            }

            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);

        }
    };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = this;
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        phoneNumber = settings.getString("phoneNumber", "");
        smsContent = settings.getString("SMSContent", "");

        // Defined SoundLevelView in main.xml file
        setContentView(R.layout.preview_surface);
       // mStatusView = (TextView) findViewById(R.id.status);

        // Used to record voice
        mSensor = new SoundMeter();
        //mDisplay = (SoundLevelView) findViewById(R.id.volume);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");
    }


    @Override
    public void onResume() {
        super.onResume();
        //Log.i("Noise", "==== onResume ===");

        initializeApplicationConstants();
        //mDisplay.setLevel(0, mThreshold);

        if (!mRunning) {
            mRunning = true;
            start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Log.i("Noise", "==== onStop ===");

        //Stop noise monitoring
        stop();

    }

    private void start() {
        //Log.i("Noise", "==== start ===");

        mSensor.start();
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }

        //Noise monitoring start
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }

    private void stop() {
        Log.i("Noise", "==== Stop Noise Monitoring===");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        //mDisplay.setLevel(0,0);
      //  updateDisplay("stopped...", 0.0);
        mRunning = false;

    }


    private void initializeApplicationConstants() {
        // Set Noise Threshold
        mThreshold = 10;

    }


    public static void sendSMS(NoiseAlert context) {
        long now = System.currentTimeMillis();
        long diff = now - lastSMSsentAt;
        Log.d(TAG, "diff : " + diff);
        Log.d(TAG, "pre: " + lastSMSsentAt + " şimdi: " + now);
        if (diff > 50000) {
            lastSMSsentAt = now;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpledf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            SmsManager sms = SmsManager.getDefault();
            //telefon no yu alamıyor
            String destinationAddress = "05336600429";

           // String destinationAddress = phoneNumber;
            String message = "ses" + "\n";
            message += "zaman: " + simpledf.format(calendar.getTime()) + "";
            sms.sendTextMessage(destinationAddress, null, message, null, null);
            Log.d(TAG, "sms sent");
        }
    }

    private void callForHelp() {
  //      Toast.makeText(getApplicationContext(), "Yüksek Ses.",
//                Toast.LENGTH_LONG).show();

       sendSMS(ref);

        long now = System.currentTimeMillis();


        //stop();

        // Show alert when noise thersold crossed

    }

};