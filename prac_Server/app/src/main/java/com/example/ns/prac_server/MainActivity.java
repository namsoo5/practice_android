package com.example.ns.prac_server;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ServerThread thread = new ServerThread();
               // thread.start();

                Intent intent = new Intent(getApplicationContext(), ServerService.class);
                startService(intent); //서비스실행
            }
        });
    }
/*
    class ServerThread extends Thread{
        @Override
        public void run() {
            int port = 5001;
            try {
                ServerSocket server = new ServerSocket(port);
                Log.d("ServerThread", "서버가 실행됨");

                while (true){
                    Socket socket = server.accept();  //5001번대기중... 클라이언트접속시 소켓객체리턴

                    ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());//들어오는데이터처리
                    Object input = instream.readObject();
                    Log.d("ServerThread", "input: "+input);

                    ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                    outstream.writeObject(input + " from server.");
                    outstream.flush();//버퍼비우기

                    Log.d("ServerThread", "output 보냄");

                    socket.close();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
