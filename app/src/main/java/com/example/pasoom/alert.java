package com.example.pasoom;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class alert extends Service {

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        RecieverThread rthread = new RecieverThread();
        rthread.start();

        return super.onStartCommand(intent, flags,startId);
    }

    @Override
    public void onDestroy()
    {

    }

    class RecieverThread extends Thread
    {
        public void run()
        {
            try {
                Socket socket = new Socket("192.168.123.95", 8088);

                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                DataInputStream dis = new DataInputStream(is);
                DataOutputStream dos = new DataOutputStream(os);

                dos.writeInt(2);
                dos.writeUTF("someID");

                int ack = dis.readInt();

                if (ack == 0) {
                    //Fail to Connect to the Server
                    String fail = dis.readUTF();
                    Log.e("Error", fail);
                } else {
                    if (ack == 2) {
                        //Emergency State
                        String troubleId = dis.readUTF();
                        String troubleState = dis.readUTF();
                        ack = dis.readInt();
                    }
                    //Normal State
                    String leftId = dis.readUTF();
                    String leftState = dis.readUTF();

                }

                socket.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
