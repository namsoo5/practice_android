package com.example.ns.saverestorestatetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int mcount =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.textView);

        Button btincrease = findViewById(R.id.bt_increase);
        btincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcount++;
                tv.setText("현재 개수 = "+mcount);
            }
        });


        Button btdecrease = findViewById(R.id.bt_decrease);
        btdecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(--mcount<0){
                    mcount=0;
                }
                tv.setText("현재 개수 = "+mcount);
            }
        });

        if(savedInstanceState != null){ // 최초실행이아닌것
            mcount = savedInstanceState.getInt("mcount");
        }
        tv.setText("현재 개수 = "+mcount);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);  // 위젯들돌면서 체크하는것 반드시넣어줘야함
        outState.putInt("mcount",mcount);  //값저장

    }
}
