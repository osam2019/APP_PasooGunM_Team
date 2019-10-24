package com.example.pasoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminMain extends AppCompatActivity
{
    List idlist = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);
        Intent handler = getIntent();
        String name = handler.getStringExtra("Name");
        TextView adminPid = (TextView)findViewById(R.id.adminMainName);
        adminPid.setText(name);
        adminMainThread aMThread = new adminMainThread();
        aMThread.start();

    }

    public void refresh(View view)
    {
        adminMainThread aMThread = new adminMainThread();
        aMThread.start();
    }

/*
    class deletion implements View.OnClickListener
    {
        public onClick(View view) {
            Log.d("del", "start");
          //  String num = ((Button) view).getText().toString().replace("번삭제", "");
            try {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.122.88:8080/RealServer/userdelete.jsp");

                FormBody.Builder bodyBuilder = new FormBody.Builder();

               // bodyBuilder.add("Num", num);

                FormBody body = bodyBuilder.build();

                builder = builder.post(body);
                Request request = builder.build();

                Call call = client.newCall(request);

                delCallback callback = new delCallback();
                call.enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adminMainThread aMThread = new adminMainThread();
            aMThread.start();
        }
    }
*/
    class adminMainThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try
            {
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.122.88:8080/RealServer/adminon.jsp");

                FormBody.Builder bodyBuilder = new FormBody.Builder();
                Intent handler = getIntent();

                String PID = handler.getStringExtra("ID");


                bodyBuilder.add("PID", PID);

                FormBody body = bodyBuilder.build();

                builder = builder.post(body);
                Request request = builder.build();

                Call call = client.newCall(request);

                adminMainCallback callback = new adminMainCallback();
                call.enqueue(callback);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    class adminMainCallback implements Callback
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
                            int num = obj.getInt("num");
                            ListView list1 = (ListView)findViewById(R.id.userList);
                            ArrayList<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();
                            idlist = new ArrayList();
                            for(int i=1; i<= num; i++){
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("ID", obj.getString("ID"+i));
                                idlist.add(obj.getString("ID"+i));
                                map.put("CT", obj.getString("CT"+i));
                                map.put("Name", obj.getString("Name"+i));
                                data_list.add(map);
                            }
                            String [] keys = {"ID", "CT", "Name"};
                            int [] shows = {R.id.rowID, R.id.rowCT, R.id.rowName};
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data_list, R.layout.row, keys, shows);
                            list1.setAdapter(adapter);
                            Log.d("TesT","TT");
                            ListListener listener = new ListListener();
                            list1.setOnItemClickListener(listener);
                              Log.d("TesT","TT");


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

    class ListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                Log.d("TesT","TTTTT");
                String delID = idlist.get(position).toString();
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder = builder.url("http://192.168.122.88:8080/RealServer/userdelete.jsp");

                FormBody.Builder bodyBuilder = new FormBody.Builder();

                bodyBuilder.add("delID", delID);

                FormBody body = bodyBuilder.build();

                builder = builder.post(body);
                Request request = builder.build();

                Call call = client.newCall(request);

                delCallback callback = new delCallback();
                call.enqueue(callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adminMainThread aMThread = new adminMainThread();
            aMThread.start();

        }
    }

    class delCallback implements Callback
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
                                final Toast CorrectM = Toast.makeText(getApplicationContext(), "삭제성공", Toast.LENGTH_LONG);
                                CorrectM.show();
                            }
                            else
                            {
                                final Toast WrongM = Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_LONG);
                                WrongM.show();
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
