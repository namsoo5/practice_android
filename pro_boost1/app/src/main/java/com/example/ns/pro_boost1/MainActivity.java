package com.example.ns.pro_boost1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.ns.pro_boost1.dbHelper.DatabaseCommentHelper;
import com.example.ns.pro_boost1.dbHelper.DatabaseReadHelper;
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
    TextView mtv_title;

    Context activity;

    ImageView miv;
    ImageView miv_age;
    TextView mtv_date;
    TextView mtv_hour;
    TextView tv_reservation;
    TextView tv_audience;
    TextView tv_synopsis;
    TextView tv_director;
    TextView tv_actor;

    ArrayList<CommentList> commentLists;

    int age = 0;
    public static int movie_id=0;
    ArrayList<Float> rating;
    ArrayList<String> contents;
    ArrayList<String> id;
    ArrayList<String> time;
    ArrayList<Integer> recommend;

    String movieurl= "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovie?id=";
    String commenturl = "http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList?id=";

    RecyclerView recyclerView;
    ThumbnailAdapter thumbadapter;

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

        DrawerActivity.status = NetworkStatus.getConnectivityStatus(activity);  //네트워크연결상태


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
        mtv_title = rootView.findViewById(R.id.tv_title);//타이틀제목

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
                intent.putExtra("title", mtv_title.getText().toString());
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
                intent.putExtra("title", mtv_title.getText().toString());
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


        miv = rootView.findViewById(R.id.im_title);
        miv_age = rootView.findViewById(R.id.im_age);
        mtv_date = rootView.findViewById(R.id.tv_date);
        mtv_hour = rootView.findViewById(R.id.tv_hour);
        tv_reservation = rootView.findViewById(R.id.tv_reservation);
        tv_audience = rootView.findViewById(R.id.tv_audience);
        tv_synopsis = rootView.findViewById(R.id.tv_synopsis);
        tv_director = rootView.findViewById(R.id.tv_director);
        tv_actor = rootView.findViewById(R.id.tv_actor);



        if(DrawerActivity.status == NetworkStatus.TYPE_NOT_CONNECTED){
            notConnectReadNetwork(movie_id);
            notConnectCommentNetwork(movie_id);
        }else{
            sendRequest(movie_id);
            sendCommentRequest(movie_id);
        }


        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        thumbadapter = new ThumbnailAdapter(activity);

        thumbnail(movie_id);  //영화에따른 섬네일이미지
        recyclerView.setAdapter(thumbadapter);


        return rootView;
    }

    public void thumbnail(int movie_id){
        switch (movie_id){
            case 1:
                thumbadapter.addPhotoItem("https://i.ytimg.com/vi/y422jVFruic/maxresdefault.jpg");
                thumbadapter.addPhotoItem("http://pub.chosun.com/up_fd/wc_news/2017-11/simg_org/ggun.jpg");
                thumbadapter.addPhotoItem("http://cphoto.asiae.co.kr/listimglink/1/2017102509384995266_1.jpg");
                thumbadapter.addPhotoItem("http://www.ccdailynews.com/news/photo/201711/945589_380351_436.jpg");
                thumbadapter.addMovieItem("https://youtu.be/JNL44p5kzTk");
                break;
            case 2:
                thumbadapter.addPhotoItem("http://img.cgv.co.kr/Movie/Thumbnail/Poster/000080/80085/80085_1000.jpg");
                thumbadapter.addPhotoItem("http://img.hani.co.kr/imgdb/resize/2017/1115/00503432_20171115.JPG");
                thumbadapter.addPhotoItem("http://mblogthumb2.phinf.naver.net/20160924_277/turnxing2_14746907429362fDez_JPEG/c0c48a84-f35b-4def-9a48-e94f484b9ee4.png.jpg?type=w800");
                thumbadapter.addPhotoItem("http://hqwallpapersgalaxy.com/Uploads/18-3-2018/26252/thumb2-2017-gotham-city-justice-leagu-flash-batman.jpg");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String commenturl = "http://boostcourse-appapi.connect.or.kr:10000/movie/readCommentList?id=";

        switch (requestCode) {
            case REQ_CODE_EditReviewActivity:
                if (resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged();

                }
                break;
            case REQ_CODE_AllViewActivity:
                if (resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged();
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


    public void sendRequest(int id) {
        this.movie_id = id;
        movieurl = movieurl + id;
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


            mtv_title.setText(readMovie.title);
            selectImage(readMovie.grade); //관람가이미지
            mtv_date.setText(readMovie.date + " 개봉");
            mtv_hour.setText(readMovie.genre + " / " + readMovie.duration + " 분");
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
                    .into(miv);

            DatabaseReadHelper.openDatabase(activity, "boost");
            DatabaseReadHelper.createReadTable();
            DatabaseReadHelper.insertRead(readMovie);  //서버연결시 db저장


        }
    }

    void selectImage(int age) {
        this.age = age;
        switch (age) {
            case 0:
                miv_age.setImageResource(R.drawable.ic_all);
                break;
            case 12:
                miv_age.setImageResource(R.drawable.ic_12);
                break;
            case 15:
                miv_age.setImageResource(R.drawable.ic_15);
                break;
            case 19:
                miv_age.setImageResource(R.drawable.ic_19);
                break;
            default:
                miv_age.setImageResource(R.drawable.ic_all);
                break;
        }
    }


    public void sendCommentRequest(int id) {
        commenturl = commenturl + id;
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

            DatabaseCommentHelper.openDatabase(activity, "boost");
            DatabaseCommentHelper.createCommentTable();

            Thread thread = new Thread(new Runnable() {   //오래걸리는 작업 스레드작업으로
                @Override
                public void run() {
                    DatabaseCommentHelper.insertComment(commentLists);
                }
            });
            thread.start();


        }
    }

    public void notConnectReadNetwork(int id){
        DatabaseReadHelper.openDatabase(activity, "boost");
        Cursor cursor = DatabaseReadHelper.selectRead(id);

        if(cursor!=null) { // 에러방지
            cursor.moveToLast();  //최근저장내역가져옴

            String title = cursor.getString(1);
            int grade = cursor.getInt(2);
            String date = cursor.getString(3);
            String genre = cursor.getString(4);
            int duration = cursor.getInt(5);
            int like= cursor.getInt(6);
            int dislike= cursor.getInt(7);
            int reservation_grade = cursor.getInt(8);
            float reservation_rate = cursor.getFloat(9);
            float user_rating = cursor.getFloat(10);
            int audience = cursor.getInt(11);
            String synopsis= cursor.getString(12);
            String director =cursor.getString(13);
            String actor= cursor.getString(14);
            String thumb = cursor.getString(15);



            mtv_title.setText(title);
            selectImage(grade); //관람가이미지
            mtv_date.setText(date + " 개봉");
            mtv_hour.setText(genre + " / " + duration + " 분");
            tv_like.setText("" + like);
            likecount = like;
            unlikecount = dislike;
            tv_unlike.setText("" + dislike);
            tv_reservation.setText(reservation_grade + "위 " + reservation_rate + "%");
            ratingBar.setRating(user_rating);
            tv_score.setText("" + user_rating);
            tv_audience.setText(audience + "명");
            tv_synopsis.setText(synopsis);
            tv_director.setText(director);
            tv_actor.setText(actor);


            Glide.with(activity)
                    .load(thumb)
                    .into(miv);

            cursor.close();

        }
    }
    public void notConnectCommentNetwork(int id) {
        DatabaseCommentHelper.openDatabase(activity, "boost");
        Cursor cursor = DatabaseCommentHelper.selectComment(id);


        ArrayList<CommentList> dbcommentLists = new ArrayList<CommentList>();
        CommentList dbcommentList;

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            dbcommentList = new CommentList();
            dbcommentList.id = cursor.getInt(1);
            dbcommentList.writer = cursor.getString(2);
            dbcommentList.time = cursor.getString(3);
            dbcommentList.rating = cursor.getFloat(4);
            dbcommentList.contents = cursor.getString(5);
            dbcommentList.recommend = cursor.getInt(6);

            dbcommentLists.add(dbcommentList); //댓글1개추가
        }
        adapter = new ListView_review(activity, R.layout.listview_review, dbcommentLists);   //listview review목록
        lv_review.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        cursor.close();
    }

}
