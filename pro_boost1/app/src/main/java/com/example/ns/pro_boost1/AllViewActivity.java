package com.example.ns.pro_boost1;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ns.pro_boost1.comment.CommentInfo;
import com.example.ns.pro_boost1.comment.CommentList;
import com.example.ns.pro_boost1.comment.CommentListArray;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AllViewActivity extends AppCompatActivity {

    final int REQ_CODE_EditReviewActivity2 = 9;

    ListView lv;
    ArrayAdapter adapter;
    TextView title;
    ImageView im_age;
    TextView score;
    TextView vote;
    RatingBar ratingBar;
    DecimalFormat pattern;

    int age=0;
    ArrayList<CommentList> commentLists;

    int movie_id=0;
    String commenturl = "http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList?id=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_view);


        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        movie_id = getIntent().getIntExtra("id", 0);
        sendCommentRequest(movie_id, commenturl);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("한줄평 목록");
        lv = findViewById(R.id.lv_allview_activity);


        score = findViewById(R.id.tv_allview_score);
        score.setText(getIntent().getStringExtra("score"));

        im_age = findViewById(R.id.im_allview_age);
        selectImage(getIntent().getIntExtra("age", 0));

        title = findViewById(R.id.tv_allview_title);
        title.setText(getIntent().getStringExtra("title"));

        vote = findViewById(R.id.tv_allview_vote);
        pattern = new DecimalFormat("###,###");


        ratingBar = findViewById(R.id.ratingbar_allview);
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("score")));




         //앱바에 뒤로가기버튼달기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        Button bt_edit = findViewById(R.id.bt_allview_review_edit);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditReviewActivity.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("age", age);  //관람나이
                intent.putExtra("id", movie_id);

                startActivityForResult(intent, REQ_CODE_EditReviewActivity2);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQ_CODE_EditReviewActivity2:
                if(resultCode==RESULT_OK){
                    adapter.notifyDataSetChanged();
                    sendCommentRequest(movie_id, commenturl);
                }
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }


    void selectImage(int age){
        this.age = age;
        switch (age){
            case 0:
                im_age.setImageResource(R.drawable.ic_all);
                break;
            case 12:
                im_age.setImageResource(R.drawable.ic_12);
                break;
            case 15:
                im_age.setImageResource(R.drawable.ic_15);
                break;
            case 19:
                im_age.setImageResource(R.drawable.ic_19);
                break;
            default :
                im_age.setImageResource(R.drawable.ic_all);
                break;
        }
    }


    public void sendCommentRequest(int id, String url) {
        String commenturl = url + id;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                commenturl,
                new Response.Listener<String>() {//응답을 문자열로받아서 넣어달라는뜻
                    @Override
                    public void onResponse(String response) {

                        processCommentResponse(response);  // 사용자가만든 gson변환함수

                    }
                },
                new Response.ErrorListener() {  //에러시실행
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        //자동캐싱기능이있음 이전결과가 그대로보여질수도있다.
        request.setShouldCache(false); //이전결과가잇더라도 새로요청해서 결과보여줌
        AppHelper.requestQueue.add(request);

    }

    public void processCommentResponse(String response) {   //json parsing

        Gson gson = new Gson();
        CommentInfo commentInfo = gson.fromJson(response, CommentInfo.class);  //리뷰화면

        if (commentInfo.code == 200) {
            CommentListArray commentListArray = gson.fromJson(response, CommentListArray.class);

            String s = pattern.format(commentListArray.result.size());
            vote.setText("("+s+"명 참여)");
            commentLists = commentListArray.result;
            adapter = new ListView_review(this, R.layout.listview_review, commentLists);   //listview review목록
            lv.setAdapter(adapter);


        }


    }
}
