package com.example.ns.prac_youtubeapiconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView ytv;
    Button button;
    YouTubePlayer.OnInitializedListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.youtubeButton);
        ytv = (YouTubePlayerView)findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {   //유투브 리스너
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {  //재생버튼 으로 생성성공시
                youTubePlayer.loadVideo("D3ZFtSoWtRc");  //비디오영상
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        button.setOnClickListener(new View.OnClickListener(){ //버튼클릭시
            @Override
            public void onClick(View v) {
                ytv.initialize("AIzaSyCvPm3xIZ8lorbl5JpoJ3mdCbOj6umfye8",listener);  //사용자인증키입력하고 youtube뷰생성
            }
        });
    }
}
