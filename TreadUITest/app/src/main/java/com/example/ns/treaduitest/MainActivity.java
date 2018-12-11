package com.example.ns.treaduitest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "ThreadUITest";
    boolean mRunning = false;  //스레드 플레그  (스레드를 종료하기위한 조건)
    TextView tv; //쓰레드에서 참조하기위해서
    static final int MSG_CODE_THCOUNTER = 0;  //쓰레드구분코드

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CODE_THCOUNTER:
                    tv.setText("["+Thread.currentThread().getName() + "] Count: "+msg.arg1);
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Thread threadCount = new Thread(new CountThread());
       // mRunning=true;
       // threadCount.start();
        tv = findViewById(R.id.tv);
    }

    @Override
    protected void onStart() {   //onstop상태엿다가 다시 킬때 onstart로 돌아오므로 홈버튼누르고 다시 돌아올경우 카운트다시시작
        super.onStart();
        Thread threadCount = new Thread(new CountThread());
        mRunning=true;
        threadCount.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRunning=false;
    }

    private class CountThread implements Runnable{
        @Override
        public void run() {
            for(int i=0; i<10 && mRunning; i++){
                Log.i(TAG, "["+Thread.currentThread().getName() + "] Count: "+i);
               // tv.setText("["+Thread.currentThread().getName() + "] Count: "+i);  //메인엑티비티의 텍스트뷰에도 찍겟다
//                tv.post(new Runnable(){
//                    @Override  //텍스트뷰에 runnable던져줘서 텍스트뷰가 실행하는것임
 //                   public void run() {
 //                       tv.setText("["+Thread.currentThread().getName() + "] Count: "+i);  //메인엑티비티의 텍스트뷰에도 찍겟다 복잡해지면 잘안씀
//                    }
 //               });

                //핸들러에게 값(message)을보내줘도되고 실행코드(runnalbe)를보내줘도됌
                Message msg = new Message();
                msg.arg1 = i;
                msg.what = MSG_CODE_THCOUNTER;  //여러쓰레드에서값이 들어올수있음, 어느쓰레드에서 온것인지 구분하는코드
                mHandler.sendMessage(msg);


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
