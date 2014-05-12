package com.bebek.takip.canlıekrangörüntüsü;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by yasemin on 5/12/14.
 */
public class ServerThread implements  Runnable{

    private static final String TAG = "FarCam";

    private Context mycontext;
    ServerSocket serversocket;

    final static Handler mHandler = new Handler();

    boolean isRunning=false;

    private String host;

    TakePictureHelper _picHelper = null;

    private int _port = 1234;

    public static ServerThread NewServerThread(Context mycontext, TakePictureHelper picHelper, int port)
    {
        ServerThread serverThread = new ServerThread(mycontext, picHelper, port);
        Thread th = new Thread(serverThread);
        th.start();
        return serverThread;
    }

    ServerThread(Context mycontext, TakePictureHelper picHelper, int port) {
        this._port = port;
        this.mycontext=mycontext;
        this._picHelper = picHelper;
    }

    public void run()
    {

        isRunning=true;

        try {
            host = getLocalIpAddress();

            serversocket = new ServerSocket(_port);
            serversocket.setReuseAddress(true);
            Thread.sleep(10000);

            while (isRunning) {

                Socket clientsocket = serversocket.accept();

                ClientThread.NewClientThread(clientsocket, _picHelper);
            }
        } catch (Exception ex) {
            Log.e(TAG, "" + ex);
        }

        Log.d(TAG, "Server closing");
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("ex getLocalIpAddress", ex.toString());
        }
        return null;
    }

    String getStringFromInput(BufferedReader input) {
        StringBuilder sb = new StringBuilder();
        String sTemp;
        try {
            while (!(sTemp = input.readLine()).equals(""))  {
                sb.append(sTemp).append("\n");
            }
        } catch (IOException e) {
            return "";
        }

        return sb.toString();
    }

    public String getHost() {
        return host;
    }

    void stop() {
        try {
            serversocket.close();
        } catch (Exception ex) {
            Log.e(TAG, ""+ex);
        }

        isRunning=false;
    }


}




