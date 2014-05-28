package com.bebek.takip;

/**
 * Created by yasemin on 5/27/14.
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.Menu;

public class MyReceiver extends DeviceAdminReceiver {

    Camera kamera;
    static private boolean usingCamera;

    public MyReceiver() {
        // TODO Auto-generated constructor stub


    }

    public void onPasswordFailed(Context context, Intent intent) {
        // TODO Auto-generated method stub

        kamera = Camera.open();
        final AudioManager manager = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);

        if (manager.getStreamVolume(AudioManager.STREAM_SYSTEM) != 0) {
            manager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0,
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }


        kamera.startPreview();

        kamera.takePicture(null, null, new PictureCallback() {
            public void onPictureTaken(byte[] data, Camera camera) {
                FileOutputStream outStream = null;
                try {

                    String timeStamp = new SimpleDateFormat(
                            "yyyyMMdd_HHmmss").format(new Date());
                    outStream = new FileOutputStream("/sdcard/Picture_"
                            + timeStamp + ".jpg");
                    outStream.write(data);
                    outStream.close();

                } catch (FileNotFoundException e) {
                    Log.d("Exception", "File Not Found");
                } catch (IOException e) {
                    Log.d("Exceptin","String Format Failed");
                } finally {
                    camera.release();

                    usingCamera = false;
                    if (manager.getStreamVolume(AudioManager.STREAM_SYSTEM) == 0) {
                        manager.setStreamVolume(
                                AudioManager.STREAM_SYSTEM,
                                manager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM),
                                AudioManager.FLAG_ALLOW_RINGER_MODES);
                    }
                }

            }
        });

    }
}