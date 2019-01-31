package com.example.ns.prac_audioplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaPlayer player;
    int position = 0; //재생위치저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    public void playAudio() {

        try {
            closePlayer(); //여러번누를수도있으므로

            player = new MediaPlayer();
            player.setDataSource(url);
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
