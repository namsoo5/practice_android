package com.example.ns.prac_http;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    String urlStr;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);
        tv = findViewById(R.id.textView);

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlStr = et.getText().toString();

                RequestThread thread = new RequestThread();
                thread.start();
            }

        });
    }

    class RequestThread extends Thread{
        @Override
        public void run() {

            try {
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();  //이를이용하여 요청을보내고받음
                if(conn != null){
                    conn.setConnectTimeout(10000); //10초동안기다려서 응답없으면 종료
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true); //입력가능(수신)
                    conn.setDoOutput(true);  //출력가능(송신)

                    int resCode = conn.getResponseCode();
                    if(resCode == HttpURLConnection.HTTP_OK) { // 모든 응답을받을경우 아래같이 바로 선언
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));//1줄씩읽을수잇음
                        String line = null;

                        while (true) {
                            line = reader.readLine();
                            if (line == null) {
                                break;  //다읽음
                            }

                            printLn(line);//텍스트뷰로출력
                        }
                        reader.close();
                        conn.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void printLn(final String data){
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv.append(data + "\n");
            }
        });
    }
}

