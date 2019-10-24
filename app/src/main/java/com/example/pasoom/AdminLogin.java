package com.example.pasoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminLogin extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
    }

    public void adminlogin(View view)
    {
        adminNetworkThread nthread = new adminNetworkThread();
        nthread.start();
    }

    class adminNetworkThread extends Thread {
        @Override
        public void run() {
            super.run();
            TextView ID = (TextView) findViewById(R.id.adminID);
            TextView PW = (TextView) findViewById(R.id.adminPW);

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.122.88:8080/RealServer/adminlogin.jsp");

                FormBody.Builder bodyBuilder = new FormBody.Builder();

                bodyBuilder.add("ID", ID.getText().toString());
                bodyBuilder.add("PW", PW.getText().toString());

                FormBody body = bodyBuilder.build();

                builder = builder.post(body);
                Request request = builder.build();

                Call call = client.newCall(request);

                adminNetworkCallback callback = new adminNetworkCallback();
                call.enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class adminNetworkCallback implements Callback {
        @Override
        public void onFailure(Call call, IOException e)
        {
            runOnUiThread(new Runnable() {
                public void run() {
                    final Toast FailureM = Toast.makeText(getApplicationContext(), "연결 실패\n서버와 연결상태를 확인해주세요", Toast.LENGTH_LONG);
                    FailureM.show();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) {
            try {
                final String result = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(result);

                            Boolean ACK = obj.getBoolean("ACK");
                            Boolean PASS = obj.getBoolean("PASS");
                            String Name = obj.getString("Name");
                            if (ACK) {
                                if (PASS) {
                                    Log.d("test",Name);
                                    Intent adminMain = new Intent(getApplicationContext(), AdminMain.class);
                                    adminMain.putExtra("ID",((TextView)findViewById(R.id.adminID)).getText().toString());
                                    adminMain.putExtra("Name",Name);
                                    startActivity(adminMain);
                                } else {
                                    Log.d("server", "fail to login");
                                    final Toast WrongM = Toast.makeText(getApplicationContext(), "로그인 실패\n아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG);
                                    WrongM.show();
                                }
                            } else {
                                Log.d("server", "fail to connect");
                                final Toast badM = Toast.makeText(getApplicationContext(), "연결 실패\n서버와 연결상태를 확인해주세요", Toast.LENGTH_LONG);
                                badM.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void adminRegister(View view)
    {
        Intent intent = new Intent(this, AdminRegister.class);
        startActivity(intent);
    }
}

