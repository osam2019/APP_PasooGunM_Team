package com.example.pasoom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

//카메라 감시 서비스
public class CamOver extends Service
{
    ThreadClass thread = new ThreadClass();
    String UniqueID, UniquePID;

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
        UniqueID = intent.getStringExtra("current ID");
        UniquePID = intent.getStringExtra("current PID");
        Log.d("ID", UniqueID);
        Log.d("PID", UniquePID);
        thread.start();
        return super.onStartCommand(intent, flags,startId);
    }

    @Override
    public void onDestroy()
    {
        //서비스 종료시 쓰레드에 카메라매니저도 종료
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            thread.manager.unregisterAvailabilityCallback((CameraManager.AvailabilityCallback) thread.callback);
        }
    }

    class ThreadClass extends Thread
    {
        CameraManager manager; //카메라 매니저
        Object callback;  //카메라 매니저 콜백
        Handler camHandler = new Handler();
        long runtime = 0, starttime = 0, fintime =0;  //가동시간 확인용
        Boolean active= FALSE; //가동여부 확인용
        public void run()
        {
            //API 21이상에서 동작
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                manager= (CameraManager)getSystemService(Context.CAMERA_SERVICE);
                callback = new CameraManager.AvailabilityCallback(){
                    @Override
                    //카메라가 사용가능할 시 호출됨
                    public void onCameraAvailable(String cameraId)
                    {
                        super.onCameraAvailable(cameraId);
                        Log.d("test","카메라 사용가능");
                        if(active)
                        {
                            //카메라 가동 종료시간 기록 및 가동 시간 계산
                            fintime = System.currentTimeMillis();
                            runtime += ((fintime - starttime)/1000);
                            active = FALSE;
                            Log.d("time",Integer.toString((int)runtime));

                            try{
                                OkHttpClient client = new OkHttpClient();
                                Request.Builder builder = new Request.Builder();
                                builder = builder.url("http://192.168.122.88:8080/RealServer/camend.jsp");

                                FormBody.Builder bodyBuilder = new FormBody.Builder();
                                Log.d("ID",UniqueID);
                                Log.d("PID",UniquePID);
                                bodyBuilder.add("ID",UniqueID);
                                bodyBuilder.add("PID",UniquePID);
                                bodyBuilder.add("Runtime",Long.toString(runtime));

                                FormBody body = bodyBuilder.build();
                                builder = builder.post(body);

                                Request request = builder.build();

                                Call call = client.newCall(request);

                                camNetworkCallback callback =  new camNetworkCallback();
                                call.enqueue(callback);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    //다른 어플이 카메라 사용시 호출됨
                    public void onCameraUnavailable(String cameraId)
                    {
                        super.onCameraUnavailable(cameraId);
                        Log.d("test","카메라 사용불가");
                        if(active == FALSE)
                        {
                            //카메라 가동 시작시간 기록
                            starttime = System.currentTimeMillis();
                            active = TRUE;
                            //TODO 서버로 카메라 가동정보 전송
                            camonNetworkThread cothread = new camonNetworkThread();
                            cothread.start();
                        }
                    }
                };
                //카메라 매니저 켜기
                manager.registerAvailabilityCallback((CameraManager.AvailabilityCallback)callback,camHandler);
            }
        }
    }

    class camonNetworkThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try{
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.122.88:8080/RealServer/camon.jsp");

                FormBody.Builder bodyBuilder = new FormBody.Builder();

                bodyBuilder.add("ID","someID");
                bodyBuilder.add("PID","somePID");

                FormBody body = bodyBuilder.build();
                builder = builder.post(body);

                Request request = builder.build();

                Call call = client.newCall(request);

                camNetworkCallback callback =  new camNetworkCallback();
                call.enqueue(callback);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    class camNetworkCallback implements Callback{
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) {
            try{

                // 서버가 전달한 데이터를 추출한다.
                final String result = response.body().string();
                JSONObject obj = new JSONObject(result);

                Boolean ACK = obj.getBoolean("ACK");
                if(ACK)
                {
                    Log.d("overwatch","Problem Sent");
                }
                else
                {
                    Log.d("overwatch","Problem Not Sent");
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
