package com.example.ns.progressbartest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
static final String TAG = "progress";
static final int FILE_SIZE = 200;
static final double DONWLOAD_SPEED = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.bt_down);
        final DialogFragment downloaddialogfragment = new DownloadFileProgressDialogFragment();


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownLoadFileTask(downloaddialogfragment, FILE_SIZE).execute(DONWLOAD_SPEED);
            }
        });



    }

//alertdialog 프레그먼트클래스만들기
    public static class DownloadFileProgressDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder adbuilder = new AlertDialog.Builder(getActivity());
            adbuilder.setTitle("다운중...")
                    .setView(View.inflate(getActivity(), R.layout.dialog, null));
            return adbuilder.create();
        }
    }


    private class DownLoadFileTask extends AsyncTask<Double, Double, Void>{

        DialogFragment mdownloaddialogfragment;
        int filesize;
        ProgressBar mprogressbar;

        public DownLoadFileTask(DialogFragment downloaddialogfragment, int filesize){
            mdownloaddialogfragment = downloaddialogfragment;
            this.filesize =filesize;
        }
        @Override
        protected void onPreExecute() {  //생성시초기화
           // super.onPreExecute();
            mdownloaddialogfragment.setCancelable(false);
            mdownloaddialogfragment.show(getSupportFragmentManager(), "download");
            getSupportFragmentManager().executePendingTransactions();

            mprogressbar = mdownloaddialogfragment.getDialog().findViewById(R.id.progressBar);
            //progressbar는 alertdialog에 잇는 구성요소
            mprogressbar.setMax(filesize);  //progressbar의 크기
            mprogressbar.setProgress(0);   //progressbar초기값

        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // super.onPostExecute(aVoid);
            mdownloaddialogfragment.dismiss();
            Toast.makeText(getApplicationContext(), filesize+"Kbytes file download completed.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            //super.onProgressUpdate(values);
            mprogressbar.incrementProgressBy(values[0].intValue());
            Log.i(TAG,"getprogress(): "+ mprogressbar.getProgress());

        }

        @Override
        protected Void doInBackground(Double... params) {
            double downloadspeed = params[0];
                    int remainder = filesize;

                    while(remainder>0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "remainder: " + remainder);
                        publishProgress(downloadspeed);
                        remainder -= downloadspeed;
                    }

            return null;
        }
    }

}
