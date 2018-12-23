package com.example.ns.pro_boost1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int likecount;
    int unlikecount;
    boolean likecheck= false;
    boolean unlikecheck=false;

    Button bt_like;
    Button bt_unlike;
    TextView tv_like;
    TextView tv_unlike;
    ListView lv_review;
    ArrayAdapter<String> adapter;
    ArrayList<String> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_like = findViewById(R.id.bt_like);
        bt_unlike = findViewById(R.id.bt_unlike);
        tv_like = findViewById(R.id.tv_like);
        tv_unlike = findViewById(R.id.tv_unlike);

        likecount = Integer.parseInt(tv_like.getText().toString());  //좋아요수
        unlikecount = Integer.parseInt(tv_unlike.getText().toString());  //싫어요수

        bt_like.setOnClickListener(new View.OnClickListener() {  //좋아요 버튼클릭
            @Override
            public void onClick(View v) {
                if(likecheck){
                   likeDecrease();
                }else {
                    likecount++;
                    if(unlikecheck){
                        unlikeDecrease();
                    }
                    tv_like.setText(""+likecount);
                    bt_like.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    likecheck=true;
                }
            }
        });

        bt_unlike.setOnClickListener(new View.OnClickListener() {  //싫어요 버튼클릭
            @Override
            public void onClick(View v) {
                if(unlikecheck){
                   unlikeDecrease();
                }
                else{
                    unlikecount++;
                    if(likecheck){
                        likeDecrease();;
                    }
                    tv_unlike.setText(""+unlikecount);
                    bt_unlike.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                    unlikecheck=true;
                }
            }
        });

        lv_review = findViewById(R.id.lv_review);
        al = new ArrayList<String>();
        al.add("재미있는영화!!");
        adapter = new ListView_review(this, R.layout.listview_review, al);   //listview review목록
        lv_review.setAdapter(adapter);

        Button bt_review_edit = findViewById(R.id.bt_review_edit);
        bt_review_edit.setOnClickListener(new View.OnClickListener() {   //작성하기클릭시
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "작성하기 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        Button bt_allview = findViewById(R.id.bt_allview);
        bt_allview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "모두보기 클릭", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void likeDecrease (){  //싫어요클릭시 좋아요감소, 좋아요클릭상태시 좋아요감소
        likecount--;
        tv_like.setText(""+likecount);
        bt_like.setBackgroundResource(R.drawable.ic_thumb_up);
        likecheck = false;
    }

    void unlikeDecrease(){ //좋아요클릭시 싫어요감소, 싫어요클릭상태시 싫어요감소
        unlikecount--;
        tv_unlike.setText(""+unlikecount);
        bt_unlike.setBackgroundResource(R.drawable.ic_thumb_down);
        unlikecheck=false;
    }
}
