package com.example.ns.pro_boost1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.ns.pro_boost1.comment.CommentInfo;
import com.example.ns.pro_boost1.comment.CommentList;
import com.example.ns.pro_boost1.comment.CommentListArray;
import com.example.ns.pro_boost1.data.MovieList;
import com.example.ns.pro_boost1.data.MovieListArray;
import com.example.ns.pro_boost1.data.MovieListInfo;
import com.example.ns.pro_boost1.readmovie.ReadMoveListArray;
import com.example.ns.pro_boost1.readmovie.ReadMovie;
import com.example.ns.pro_boost1.readmovie.ReadMovieInfo;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MainActivity extends Fragment {
    @Nullable


    final int REQ_CODE_EditReviewActivity = 1;
    final int REQ_CODE_AllViewActivity = 2;

    int likecount;
    int unlikecount;
    boolean likecheck = false;

    boolean unlikecheck = false;

    Button bt_like;
    Button bt_unlike;
    TextView tv_like;
    TextView tv_unlike;
    ListView lv_review;
    ArrayAdapter adapter;
    TextView tv_score;
    ScrollView scrollView;
    RatingBar ratingBar;
    TextView tv_title;

    Context activity;

    ImageView iv;
    ImageView iv_age;
    TextView tv_date;
    TextView tv_hour;
    TextView tv_reservation;
    TextView tv_audience;
    TextView tv_synopsis;
    TextView tv_director;
    TextView tv_actor;

    ArrayList<CommentList> commentLists;

    int age = 0;
    int movie_id=0;
    ArrayList<Float> rating;
    ArrayList<String> contents;
    ArrayList<String> id;
    ArrayList<String> time;
    ArrayList<Integer> recommend;

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context != null)
            activity = context;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_main, container, false);

        bt_like = rootView.findViewById(R.id.bt_like);
        bt_unlike = rootView.findViewById(R.id.bt_unlike);
        tv_like = rootView.findViewById(R.id.tv_like);
        tv_unlike = rootView.findViewById(R.id.tv_unlike);

        tv_score = rootView.findViewById(R.id.tv_score);  //평점텍스트
        ratingBar = rootView.findViewById(R.id.ratingbar_main); //평점별
        tv_title = rootView.findViewById(R.id.tv_title);//타이틀제목

        bt_like.setOnClickListener(new View.OnClickListener() {  //좋아요 버튼클릭
            @Override
            public void onClick(View v) {
                if (likecheck) {
                    likeDecrease();
                } else {
                    likecount++;
                    if (unlikecheck) {
                        unlikeDecrease();
                    }
                    tv_like.setText("" + likecount);
                    bt_like.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    likecheck = true;
                }
            }
        });

        bt_unlike.setOnClickListener(new View.OnClickListener() {  //싫어요 버튼클릭
            @Override
            public void onClick(View v) {
                if (unlikecheck) {
                    unlikeDecrease();
                } else {
                    unlikecount++;
                    if (likecheck) {
                        likeDecrease();
                        ;
                    }
                    tv_unlike.setText("" + unlikecount);
                    bt_unlike.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                    unlikecheck = true;
                }
            }
        });

        lv_review = rootView.findViewById(R.id.lv_review);
        contents = new ArrayList<String>();
        rating = new ArrayList<Float>();



        Button bt_review_edit = rootView.findViewById(R.id.bt_review_edit);
        bt_review_edit.setOnClickListener(new View.OnClickListener() {   //작성하기클릭시
            @Override
            public void onClick(View v) {               //작성하기
                Intent intent = new Intent(activity, EditReviewActivity.class);
                intent.putExtra("title", tv_title.getText().toString());
                intent.putExtra("age", age);  //관람나이
                intent.putExtra("id", movie_id);//영화구분

                startActivityForResult(intent, REQ_CODE_EditReviewActivity);

            }
        });

        Button bt_allview = rootView.findViewById(R.id.bt_allview);
        bt_allview.setOnClickListener(new View.OnClickListener() {    //모두보기
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AllViewActivity.class);
                intent.putExtra("title", tv_title.getText().toString());
                intent.putExtra("age", age);  //관람나이
                intent.putExtra("score", tv_score.getText().toString()); //평점

                intent.putExtra("id", movie_id);

                startActivityForResult(intent, REQ_CODE_AllViewActivity);
            }
        });

        scrollView = rootView.findViewById(R.id.ScrollView_main);
        lv_review.setOnTouchListener(new View.OnTouchListener() {  //스크롤뷰안에서 리스트뷰쓰기위함
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        iv = rootView.findViewById(R.id.im_title);
        iv_age = rootView.findViewById(R.id.im_age);
        tv_date = rootView.findViewById(R.id.tv_date);
        tv_hour = rootView.findViewById(R.id.tv_hour);
        tv_reservation = rootView.findViewById(R.id.tv_reservation);
        tv_audience = rootView.findViewById(R.id.tv_audience);
        tv_synopsis = rootView.findViewById(R.id.tv_synopsis);
        tv_director = rootView.findViewById(R.id.tv_director);
        tv_actor = rootView.findViewById(R.id.tv_actor);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String commenturl = "http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList?id=";

        switch (requestCode) {
            case REQ_CODE_EditReviewActivity:
                if (resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged();
                    sendCommentRequest( movie_id, commenturl);
                }
                break;
            case REQ_CODE_AllViewActivity:
                if (resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged();
                    sendCommentRequest( movie_id, commenturl);  //댓글갱신
                   }
                break;

        }
    }

    void likeDecrease() {  //싫어요클릭시 좋아요감소, 좋아요클릭상태시 좋아요감소
        likecount--;
        tv_like.setText("" + likecount);
        bt_like.setBackgroundResource(R.drawable.ic_thumb_up);
        likecheck = false;
    }

    void unlikeDecrease() { //좋아요클릭시 싫어요감소, 싫어요클릭상태시 싫어요감소
        unlikecount--;
        tv_unlike.setText("" + unlikecount);
        bt_unlike.setBackgroundResource(R.drawable.ic_thumb_down);
        unlikecheck = false;
    }


    public void sendRequest(int id, String url) {
        this.movie_id = id;
        String movieurl = url + id;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                movieurl,
                new Response.Listener<String>() {//응답을 문자열로받아서 넣어달라는뜻
                    @Override
                    public void onResponse(String response) {

                        processResponse(response);  // 사용자가만든 gson변환함수

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

    public void processResponse(String response) {   //json parsing

        Gson gson = new Gson();
        ReadMovieInfo readMovieInfo = gson.fromJson(response, ReadMovieInfo.class);  //영화상세화면

        if (readMovieInfo.code == 200) {
            ReadMoveListArray readMoveListArray = gson.fromJson(response, ReadMoveListArray.class);

            ReadMovie readMovie = readMoveListArray.result.get(0);


            tv_title.setText(readMovie.title);
            selectImage(readMovie.grade); //관람가이미지
            tv_date.setText(readMovie.date + " 개봉");
            tv_hour.setText(readMovie.genre + " / " + readMovie.duration + " 분");
            tv_like.setText("" + readMovie.like);
            likecount = readMovie.like;
            unlikecount = readMovie.dislike;
            tv_unlike.setText("" + readMovie.dislike);
            tv_reservation.setText(readMovie.reservation_grade + "위 " + readMovie.reservation_rate + "%");
            ratingBar.setRating(readMovie.user_rating);
            tv_score.setText("" + readMovie.user_rating);
            tv_audience.setText(readMovie.audience + "명");
            tv_synopsis.setText(readMovie.synopsis);
            tv_director.setText(readMovie.director);
            tv_actor.setText(readMovie.actor);

            Glide.with(activity)
                    .load(readMovie.thumb)
                    .into(iv);


        }
    }

    void selectImage(int age) {
        this.age = age;
        switch (age) {
            case 0:
                iv_age.setImageResource(R.drawable.ic_all);
                break;
            case 12:
                iv_age.setImageResource(R.drawable.ic_12);
                break;
            case 15:
                iv_age.setImageResource(R.drawable.ic_15);
                break;
            case 19:
                iv_age.setImageResource(R.drawable.ic_19);
                break;
            default:
                iv_age.setImageResource(R.drawable.ic_all);
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

            commentLists = commentListArray.result;
            adapter = new ListView_review(activity, R.layout.listview_review,commentLists);   //listview review목록
            lv_review.setAdapter(adapter);

        }
    }

}
