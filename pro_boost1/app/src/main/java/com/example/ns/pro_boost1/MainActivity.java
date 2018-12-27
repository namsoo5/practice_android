package com.example.ns.pro_boost1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int REQ_CODE_EditReviewActivity = 1;
    final int REQ_CODE_AllViewActivity = 2;

    int likecount;
    int unlikecount;
    boolean likecheck= false;
    boolean unlikecheck=false;

    Button bt_like;
    Button bt_unlike;
    TextView tv_like;
    TextView tv_unlike;
    ListView lv_review;
    ArrayAdapter adapter;
    ArrayList<String> al;
    ArrayList<Float> rating;
    TextView tv_score;
    ScrollView scrollView;
    RatingBar ratingBar;
    TextView tv_title;
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

        tv_score = findViewById(R.id.tv_score);  //평점텍스트
        ratingBar = findViewById(R.id.ratingbar_main); //평점별
        tv_title = findViewById(R.id.tv_title);//타이틀제목

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
        rating = new ArrayList<Float>();

        adapter = new ListView_review(this, R.layout.listview_review, al, rating);   //listview review목록
        lv_review.setAdapter(adapter);

        Button bt_review_edit = findViewById(R.id.bt_review_edit);
        bt_review_edit.setOnClickListener(new View.OnClickListener() {   //작성하기클릭시
            @Override
            public void onClick(View v) {               //작성하기
                Intent intent = new Intent(getApplicationContext(), EditReviewActivity.class);
                intent.putExtra("title", tv_title.getText().toString());
                intent.putExtra("age", 15);  //관람나이

                startActivityForResult(intent, REQ_CODE_EditReviewActivity);

            }
        });

        Button bt_allview = findViewById(R.id.bt_allview);
        bt_allview.setOnClickListener(new View.OnClickListener() {    //모두보기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllViewActivity.class);
                intent.putExtra("title", tv_title.getText().toString());
                intent.putExtra("age", 15);  //관람나이
                intent.putExtra("score", tv_score.getText().toString()); //평점

                float[] ratingarray = new float[rating.size()];
                for(int i=0; i<rating.size(); i++){
                    ratingarray[i] = rating.get(i);
                }

                intent.putExtra("ratingarray", ratingarray); //댓글별점
                intent.putExtra("al",al);   //댓글

                startActivityForResult(intent, REQ_CODE_AllViewActivity);
            }
        });

        scrollView = findViewById(R.id.ScrollView_main);
        lv_review.setOnTouchListener(new View.OnTouchListener() {  //스크롤뷰안에서 리스트뷰쓰기위함
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQ_CODE_EditReviewActivity:
                if(resultCode==RESULT_OK){
                    al.add(data.getStringExtra("review"));
                    rating.add(data.getFloatExtra("rating",0));
                    updateRating();
                    adapter.notifyDataSetChanged();

                }
                break;
            case REQ_CODE_AllViewActivity:
                if(resultCode==RESULT_OK) {

                    al =  data.getStringArrayListExtra("al");
                    float[] arr = data.getFloatArrayExtra("floatarray");

                    ArrayList<Float> temp = new ArrayList<Float>();
                    for (int i = 0; i < arr.length; i++) {
                        temp.add(arr[i]);
                    }
                    rating = (ArrayList<Float>) temp.clone();
                    updateRating();

                    adapter = new ListView_review(this, R.layout.listview_review, al, rating);   //listview review목록
                    lv_review.setAdapter(adapter);
                }
                break;

        }
    }

    void updateRating(){
        float sum=0;
        for(float star : rating){
            sum +=star;
        }
        sum /= rating.size();
        tv_score.setText(String.format("%.1f", sum));
        ratingBar.setRating(sum);
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
