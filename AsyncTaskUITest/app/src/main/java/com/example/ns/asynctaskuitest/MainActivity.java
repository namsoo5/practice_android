package com.example.ns.asynctaskuitest;

import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "AsyncTaskUITest";
    boolean mRunning = false;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRunning = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        CounterTask tkCounter = new CounterTask();   //객체생성
        mRunning = true;
        tkCounter.execute();  //onpreexecute로 들어감(처음 초기화) <-작성안햇음
    }

    private class CounterTask extends AsyncTask<Void, Integer, Integer> {                    //doinBackgound의 리턴값 == onPost의 인자값

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            tv.setText("[" + Thread.currentThread().getName() + "] Count: " +result);
            Log.i(TAG, "[" + Thread.currentThread().getName() + "] LastCount: " + result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {   //중간중간 들어오는 값
            super.onProgressUpdate(values);
            tv.setText("[" + Thread.currentThread().getName() + "] Count: " + values[0]);
            Log.i(TAG, "[" + Thread.currentThread().getName() + "] Count: " + values[0]);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            int i;
            for ( i = 0; i < 10 && mRunning; i++) {
                Log.i(TAG, "[" + Thread.currentThread().getName() + "] Count: " + i);
                publishProgress(i); //값이 onProgress로전달
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return i;  //10까지찍음  onPost로들어감
        }
    }
}
