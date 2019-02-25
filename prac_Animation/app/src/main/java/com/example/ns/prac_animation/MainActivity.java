package com.example.ns.prac_animation;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Drawable> imageList = new ArrayList<Drawable>();
    ImageView imageView;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        Resources res = getResources();
        imageList.add(res.getDrawable(R.drawable.t2));
        imageList.add(res.getDrawable(R.drawable.t3));
        imageList.add(res.getDrawable(R.drawable.t4));
        imageList.add(res.getDrawable(R.drawable.t5));
        imageList.add(res.getDrawable(R.drawable.t1));

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationThread thread = new AnimationThread();
                thread.start();
            }
        });
    }

    class AnimationThread extends Thread{
        @Override
        public void run() {
            int index = 0;
            for(int i =0; i<100; i++){
                index = i % 5;  //5개의 이미지반복
                final Drawable drawable = imageList.get(index);

                handler.post(new Runnable() { //스레드는 ui직접접근불가하므로핸들러사용
                    @Override
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
