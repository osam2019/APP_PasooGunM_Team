package com.example.pasoom;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminRegister extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.admin_register);
    }

    public void adminRegisteration(View view)
    {
        adminRegisterThread aRthread = new adminRegisterThread();
        aRthread.start();
    }

    class adminRegisterThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            TextView Name = (TextView)findViewById(R.id.adminName);
            TextView ID = (TextView)findViewById(R.id.adminRegisterID);
            TextView PW = (TextView)findViewById(R.id.adminRegisterPW);
            TextView PWre = (TextView)findViewById(R.id.adminReigsterPWre);

            if(PW.getText().toString().equals(PWre.getText().toString()))
            {
                try
                {
                    OkHttpClient client = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    builder = builder.url("http://192.168.122.88:8080/RealServer/adminregister.jsp");

                    FormBody.Builder bodyBuilder = new FormBody.Builder();

                    bodyBuilder.add("ID",ID.getText().toString());
                    bodyBuilder.add("PW",PW.getText().toString());
                    bodyBuilder.add("Name",Name.getText().toString());
                    FormBody body = bodyBuilder.build();

                    builder = builder.post(body);
                    Request request = builder.build();

                    Call call = client.newCall(request);

                    adminRegisterCallback callback = new adminRegisterCallback();
                    call.enqueue(callback);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d("PASS","패스워드 불일치");
                        final Toast MistakeM = Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG);
                        MistakeM.show();
                    }
                });
            }
        }
    }
    class adminRegisterCallback implements Callback
    {
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
        public void onResponse(Call call, Response response)
        {
            try {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(result);
                            Boolean ACK = obj.getBoolean("ACK");
                            if(ACK)
                            {
                                final Toast PassM = Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_LONG);
                                PassM.show();
                                finish();
                            }
                            else
                            {
                                final Toast MistakeM = Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG);
                                MistakeM.show();
                                Log.d("Register","Register Failed");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
