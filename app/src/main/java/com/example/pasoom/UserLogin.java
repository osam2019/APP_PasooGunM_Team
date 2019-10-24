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

public class UserLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        /*OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder = builder.url("http://192.168.122.88:8080/RealServer/userlogin.jsp");

        FormBody.Builder bodyBuilder = new FormBody.Builder();

        bodyBuilder.add("CODE", "USERLOGIN");
        bodyBuilder.add("ID", ID.getText().toString());
        bodyBuilder.add("PW", PW.getText().toString());

        FormBody body = bodyBuilder.build();

        builder = builder.post(body);
        Request request = builder.build();

        Call call = client.newCall(request);

        userNetworkCallback callback = new userNetworkCallback();
        call.enqueue(callback);*/
    }

    public void userlogin(View view) {
        UserNetworkThread nthread = new UserNetworkThread();
        nthread.start();
    }


    class UserNetworkThread extends Thread {
        @Override
        public void run() {
            super.run();
            TextView ID = (TextView) findViewById(R.id.userID);
            TextView PW = (TextView) findViewById(R.id.userPW);

            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.122.88:8080/RealServer/userlogin.jsp");

                FormBody.Builder bodyBuilder = new FormBody.Builder();

                bodyBuilder.add("ID", ID.getText().toString());
                bodyBuilder.add("PW", PW.getText().toString());

                FormBody body = bodyBuilder.build();

                builder = builder.post(body);
                Request request = builder.build();

                Call call = client.newCall(request);

                userNetworkCallback callback = new userNetworkCallback();
                call.enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class userNetworkCallback implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("test", "Fail?");
            runOnUiThread(new Runnable() {
                public void run() {
                    final Toast FailureM = Toast.makeText(getApplicationContext(), "연결 실패\n서버와 연결상태를 확인해주세요", Toast.LENGTH_LONG);
                    FailureM.show();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) {
            Intent userMain = new Intent(getApplicationContext(), UserMain.class);
            userMain.putExtra("ID",((TextView)findViewById(R.id.userID)).getText().toString());
            userMain.putExtra("PW",((TextView)findViewById(R.id.userPW)).getText().toString());
            try {
                final String result = response.body().string();
                Log.d("check",result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(result);

                            Boolean ACK = obj.getBoolean("ACK");
                            Boolean PASS = obj.getBoolean("PASS");

                            if (ACK) {
                                if (PASS) {
                                    String PID = obj.getString("PID");

                                    Intent userMain = new Intent(getApplicationContext(), UserMain.class);
                                    userMain.putExtra("ID",((TextView)findViewById(R.id.userID)).getText().toString());
                                    userMain.putExtra("PID",PID);
                                    userMain.putExtra("PW",((TextView)findViewById(R.id.userPW)).getText().toString());
                                    startActivity(userMain);
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

    public void userRegister(View view)
    {
        Intent intent = new Intent(this, UserRegister.class);
        startActivity(intent);
    }

}
