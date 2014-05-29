package com.bebek.takip.hareket.monitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.bebek.takip.R;
import com.bebek.takip.hareket.SensorActivity;
import com.bebek.takip.hareket.data.GlobalData;
import com.bebek.takip.hareket.data.Preferences;
import com.bebek.takip.hareket.detection.AggregateLumaMotionDetection;
import com.bebek.takip.hareket.detection.IMotionDetection;
import com.bebek.takip.hareket.detection.LumaMotionDetection;
import com.bebek.takip.hareket.detection.RgbMotionDetection;
import com.bebek.takip.hareket.image.ImageProcessing;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yasemin on 5/3/14.
 */
public class MotionMonitorActivity extends SensorActivity {
    private static final String TAG = "MotionMonitorActivity";
    private static SurfaceView preview = null;
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;
    private static boolean inPreview = false;
    private static IMotionDetection detector = null;
    public  static MotionMonitorActivity ref= null;
    public static final String PREF_NAME = "com.bebek.takip.kamera";
    public static String phoneNumber = null;
    public static String smsContent = null;
    public static long lastSMSsentAt = 0;
    private volatile static AtomicBoolean processingStarted = new AtomicBoolean(false);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = this;
        setContentView(R.layout.surface_view);
        Log.e("motionMonitor","onCreate");
        preview = (SurfaceView)findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        if(Preferences.USE_RGB){
            detector = new RgbMotionDetection();
        }else if (Preferences.USE_LUMA){
            detector = new LumaMotionDetection();
        }
        else{
            detector = new AggregateLumaMotionDetection();
        }
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        phoneNumber = settings.getString("phoneNumber", "");
        Toast.makeText(getApplicationContext(), phoneNumber,
                Toast.LENGTH_LONG).show();
        smsContent = settings.getString("SMSContent", "");
    }
    public static void sendSMS(MotionMonitorActivity context){
        long now = System.currentTimeMillis();
        long diff = now - lastSMSsentAt;
        Log.d(TAG, "diff : " + diff);
        Log.d(TAG, "pre: " + lastSMSsentAt + " şimdi: " + now);
        if(diff> 30000){
            lastSMSsentAt = now;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpledf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            SmsManager sms = SmsManager.getDefault();
            String destinationAddress = phoneNumber;
            String message = smsContent +"\n";
            message += "zaman: " + simpledf.format(calendar.getTime()) + "";
            sms.sendTextMessage(destinationAddress, null, message, null,null);
            Log.d(TAG,"sms sent");
        }
    }
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    public void onPause(){
        super.onPause();
        try{
            camera.setPreviewCallback(null);
            if(inPreview)camera.stopPreview();
            camera.release();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            inPreview = false;
            camera = null;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        if(camera == null)camera = Camera.open();
    }
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera cam){
            if(data == null) return;
            Camera.Size size = cam.getParameters().getPreviewSize();
            if(size == null) return;
            if(!GlobalData.isPhoneInMotion()){
                DetectionThread thread = new DetectionThread(data, size.width, size.height);
                thread.start();
            }
        }
    };
    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback(){
        @Override
        public void surfaceCreated(SurfaceHolder holder){
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e("surfaceCallback","Exception in setPreviewDisplay",e);
            }
        }
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = getBestPreviewSize(width,height,parameters);
            if(size != null){
                parameters.setPreviewSize(size.width, size.height);
                Log.d(TAG, "using with="+size.width+" height="+size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
            inPreview = true;
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder){
            if(camera != null){
                camera.startPreview();
                camera.release();
                camera = null;
            }
        }
    };
    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters){
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                } else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;
                    if (newArea>resultArea) result=size;
                }
            }
        }

        return result;
    }



    private final class DetectionThread extends Thread{
        private byte[] data;
        private int width;
        private int height;
        public DetectionThread(byte[] data, int width, int height){
            this.data = data;
            this.width = width;
            this.height = height;
        }
        @Override
        public void run(){
            if(!processingStarted.compareAndSet(false,true)) return;
            try{
                Log.d(TAG, "Process started.");
                //System.out.println("processing thread running");
                long bConversion = System.currentTimeMillis();
                int[] img = null;
                if(Preferences.USE_RGB){

                    img = ImageProcessing.decodeYUV420SPtoRGB(data, width, height);
                }else{
                    img = ImageProcessing.decodeYUV420SPtoLuma(data, width, height);
                }
                long aConversion = System.currentTimeMillis();
                Log.d(TAG, "conversion=" + (aConversion - bConversion));
                if(img!=null &&detector.detect(img, width, height)){
                    sendSMS(ref);

                    long now = System.currentTimeMillis();
                    Log.d(TAG, "zaman: "+ now);

                    Intent startNoise = new Intent("com.bebek.takip.ses.NOISE");
                    startActivity(startNoise);
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                processingStarted.set(false);
            }

            Log.d(TAG,"End processing");
            processingStarted.set(false);
        }
    }
}
