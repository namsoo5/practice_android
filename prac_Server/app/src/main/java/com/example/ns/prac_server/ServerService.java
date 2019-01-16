package com.example.ns.prac_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService extends Service {
    public ServerService() {


    }

    @Override
    public void onCreate() {  //초기화시점
        super.onCreate();

        ServerThread thread = new ServerThread();
        thread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

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
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
