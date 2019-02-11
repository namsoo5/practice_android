package com.example.ns.prac_recorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaRecorder recorder;
    String filename;

    MediaPlayer player;
    int position = 0; //재생위치저장


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File sdcard = Environment.getExternalStorageDirectory(); //sdcard폴더
        File file = new File(sdcard, "recorded.mp4");
        filename = file.getAbsolutePath();
        Log.d("test1", "저장할파일명: "+filename);


        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        Button bt2 = findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
            }
        });

        Button bt3 = findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeAudio();
            }
        });

        Button bt4 = findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        Button bt5 = findViewById(R.id.button5);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAudio();
            }
        });

        Button bt6 = findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stoprecord();
            }
        });
    }

    public  void recordAudio() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //마이크로부터
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//압축포멧
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(filename); //파일지정
        try {
            recorder.prepare();
            recorder.start(); //녹음시작

            Toast.makeText(getApplicationContext(), "녹음시작", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stoprecord() {
        if(recorder != null){
            recorder.stop();
            recorder.release();
            recorder = null;

            Toast.makeText(getApplicationContext(), "녹음중지", Toast.LENGTH_SHORT).show();

        }
    }




    public void playAudio() {

        try {
            closePlayer(); //여러번누를수도있으므로

            player = new MediaPlayer();
           //player.setDataSource(url);
            player.setDataSource(filename);
            player.prepare();
            player.start();

            Toast.makeText(getApplicationContext(), "재생 시작", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pauseAudio() {
        if(player != null){
            position = player.getCurrentPosition(); //현재 재생위치
            player.pause(); //일시정지
            Toast.makeText(this, "일시정지", Toast.LENGTH_SHORT).show();
        }
    }

    public void resumeAudio(){
        if(player != null && !player.isPlaying()){  //재생중이아니면 재시작
            player.seekTo(position); //현위치부터재생
            player.start();
            Toast.makeText(this, "재시작", Toast.LENGTH_SHORT).show();
        }
    }

    public void stopAudio() {
        if(player!=null && player.isPlaying()){ //재생중이면
            player.stop();
            Toast.makeText(this, "정지", Toast.LENGTH_SHORT).show();
        }
    }

    public void closePlayer() {
        if(player != null){
            player.release(); //자원해제
            player = null;
        }
    }


}
