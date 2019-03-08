package com.example.ns.prac_animsummary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Animation transup;
    Animation transdown;
    LinearLayout menuContainer;
    boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transup = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        transdown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);

        transup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuContainer.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        menuContainer = findViewById(R.id.menucontainer);

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow) //보이는상태
                {
                    isShow = !isShow;
                    menuContainer.startAnimation(transup);
                }else
                {
                    menuContainer.setVisibility(View.VISIBLE);
                    menuContainer.startAnimation(transdown); //아래로내려오는효과
                    isShow = !isShow;
                }
            }
        });

    }
}
