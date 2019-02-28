package com.example.ns.prac_pagesliding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout page;
    Animation translateLeft;
    Animation translateRight;
    Button bt;

    boolean isPageOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = findViewById(R.id.button);
        page = findViewById(R.id.slidepage);
        translateLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left); //애니메이션로드
        translateRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right); //애니메이션로드

        SlidingAnimationListener listener = new SlidingAnimationListener();
        translateLeft.setAnimationListener(listener);
        translateRight.setAnimationListener(listener);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPageOpen) { //page오픈상태
                    page.startAnimation(translateRight); //없애주기
                   //끝난시점에 사라지게해야함 -> 리스너이용
                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeft);
                }
            }
        });
    }

    class SlidingAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isPageOpen){ //page열려잇는상태면
                page.setVisibility(View.GONE); //없애주기
                bt.setText("열기");
                isPageOpen = false;
            }else{
                bt.setText("닫기");
                isPageOpen=true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {  //반복되는시점

        }
    }
}
