package com.example.ns.pro_boost1;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.ns.pro_boost1.data.MovieList;
import com.example.ns.pro_boost1.data.MovieListArray;
import com.example.ns.pro_boost1.data.MovieListInfo;
import com.example.ns.pro_boost1.dbHelper.DatabaseMovieHelper;
import com.google.gson.Gson;

public class Fragment5 extends Fragment {
    DrawerActivity activity;
    TextView tv_title;
    ImageView iv;
    TextView reservation;
    TextView age;
    TextView day;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (DrawerActivity)context;
        DatabaseMovieHelper.openDatabase(activity, "boost");

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        Button bt = rootView.findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.ExecuteMain();
                MainActivity.movie_id= 5;
            }
        });

        tv_title = rootView.findViewById(R.id.tv_f1_title);
        iv = rootView.findViewById(R.id.iv_main);
        reservation = rootView.findViewById(R.id.tv_fragment1_reservation);
        age = rootView.findViewById(R.id.tv_fragment1_age);
        day = rootView.findViewById(R.id.tv_fragment1_day);

        if(DrawerActivity.status == NetworkStatus.TYPE_NOT_CONNECTED)
            notConnectNetwork(5);  //인터넷연결x시 db에서불러옴
        else
            sendRequest();

        return rootView;
    }


    public void sendRequest() {
        String url = "http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>(){//응답을 문자열로받아서 넣어달라는뜻
                    @Override
                    public void onResponse(String response) {

                        processResponse(response);  // 사용자가만든 gson변환함수
                    }
                },
                new Response.ErrorListener(){  //에러시실행
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
        MovieListInfo movieListInfo = gson.fromJson(response, MovieListInfo.class);
        if (movieListInfo.code == 200) {
            MovieListArray movieListArray = gson.fromJson(response, MovieListArray.class);

            MovieList movielist = movieListArray.result.get(4);

            tv_title.setText(movielist.id+". "+movielist.title);
            reservation.setText("예매율 "+movielist.reservation_rate+"%");
            age.setText(movielist.grade+"세 관람가");
            day.setText(movielist.date+"일 개봉");

            Glide.with(activity)
                    .load(movielist.image)
                    .into(iv);

            DatabaseMovieHelper.createMovieTable();
            DatabaseMovieHelper.insertMovie(movielist.id, movielist.title, movielist.reservation_rate,
                    movielist.grade, movielist.date, movielist.image);

        }
    }

    public void notConnectNetwork(int id) {
        Cursor cursor = DatabaseMovieHelper.selectMovie(id);
        if (cursor!=null) { // 에러방지
            cursor.moveToLast();  //최근저장내역가져옴
            String title = cursor.getString(0);
            float reservation_rate = cursor.getFloat(1);
            int grade = cursor.getInt(2);
            String date = cursor.getString(3);
            String image = cursor.getString(4);


            tv_title.setText("" + id + ". " + title);
            reservation.setText("예매율 " + reservation_rate + "%");
            age.setText(grade + "세 관람가");
            day.setText(date + "일 개봉");

            Glide.with(activity)
                    .load(image)
                    .into(iv);

            cursor.close();
        }
    }
}
