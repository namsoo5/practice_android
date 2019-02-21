package com.example.ns.prac_videoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView vv;
    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv = findViewById(R.id.videoView);

        MediaController controller = new MediaController(this);
        vv.setMediaController(controller);
        vv.setVideoURI(Uri.parse(url));
        vv.requestFocus(); //파일정보일부가져옴

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //준비과정이끝난것
                Toast.makeText(getApplicationContext(), "동영상준비됨", Toast.LENGTH_SHORT).show();
            }
        });


        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.seekTo(0); //재생위치지정
                vv.start();  //플레이
            }
        });
    }
}
