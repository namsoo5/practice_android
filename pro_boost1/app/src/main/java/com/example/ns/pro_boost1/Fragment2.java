package com.example.ns.pro_boost1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.ns.pro_boost1.data.MovieList;
import com.example.ns.pro_boost1.data.MovieListArray;
import com.example.ns.pro_boost1.data.MovieListInfo;
import com.example.ns.pro_boost1.dbHelper.DatabaseHelper;
import com.google.gson.Gson;

public class Fragment2 extends Fragment {
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
        DatabaseHelper.openDatabase(activity, "boost");
        sendRequest();
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
                activity.ExecuteMain(1);
            }
        });

        tv_title = rootView.findViewById(R.id.tv_f1_title);
        iv = rootView.findViewById(R.id.iv_main);
        reservation = rootView.findViewById(R.id.tv_fragment1_reservation);
        age = rootView.findViewById(R.id.tv_fragment1_age);
        day = rootView.findViewById(R.id.tv_fragment1_day);


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

            MovieList movielist = movieListArray.result.get(1);

            tv_title.setText(movielist.id+". "+movielist.title);
            reservation.setText("예매율 "+movielist.reservation_rate+"%");
            age.setText(movielist.grade+"세 관람가");
            day.setText(movielist.date+"일 개봉");

            Glide.with(activity)
                    .load(movielist.image)
                    .into(iv);

            DatabaseHelper.createMovieTable();
            DatabaseHelper.insertMovie(movielist.id, movielist.title, movielist.reservation_rate,
                    movielist.grade, movielist.date, movielist.image);

        }
    }

}
