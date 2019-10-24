package com.example.pasoom;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserMain extends AppCompatActivity
{
    Intent overwatch;
    String userID, userPID, userPW;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        Intent handler = getIntent();
        userID = handler.getStringExtra("ID");
        userPID = handler.getStringExtra("PID");
        userPW = handler.getStringExtra("PW");

        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if(CamOver.class.getName().equals(service.service.getClassName()))
            {
                stopService(overwatch);
                Log.d("service","close");
            }
        }
        overwatch = new Intent(this, CamOver.class);
        overwatch.putExtra("current ID",userID);
        overwatch.putExtra("current PID",userPID);
        overwatch.putExtra("current PW",userPW);
        startService(overwatch);
        Log.d("service","start");
    }

    protected  void onDestroy()
    {
        super.onDestroy();
            stopService(overwatch);
    }

    public void cut(View view)
    {
        stopService(overwatch);
        Log.d("service","close!");
    }
}
