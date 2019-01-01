package com.example.ns.prac_volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.imageview);
        Button bt2 = findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImageRequest();
            }
        });

        tv = findViewById(R.id.textView);
        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        if(AppHelper.requestQueue == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        //시작하면 reqestQueue가 만들어짐
    }

    public void sendImageRequest() {
        String url = "https://upload.wikimedia.org/wikipedia/ko/thumb/e/ea/%EC%98%81%ED%99%94_%EB%B3%B4%ED%97%A4%EB%AF%B8%EC%95%88_%EB%9E%A9%EC%86%8C%EB%94%94.jpg/250px-%EC%98%81%ED%99%94_%EB%B3%B4%ED%97%A4%EB%AF%B8%EC%95%88_%EB%9E%A9%EC%86%8C%EB%94%94.jpg";
        ImageLoadTask task = new ImageLoadTask(url, iv);
        task.execute();
    }


    public void sendRequest() {
        String url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20120101";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>(){//응답을 문자열로받아서 넣어달라는뜻
                    @Override
                    public void onResponse(String response) {
                        println("응답 -> "+response);

                        processResponse(response);
                    }

                },
                new Response.ErrorListener(){  //에러시실행
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 -> "+error.getMessage());
                    }
                }
        ){ //get이아닌 post방식으로 하고싶은경우 {}를 추가하고 재정의가능
            //getheader 헤더를 추가하고싶은경우
            //getparams 요청파라미터를 추가하고싶은경우
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<String, String>();
               //params.put("key", "value");

               return params;
            }
        };

        //자동캐싱기능이있음 이전결과가 그대로보여질수도있다.
        request.setShouldCache(false); //이전결과가잇더라도 새로요청해서 결과보여줌
        AppHelper.requestQueue.add(request);
        println("요청 보냄");
    }


    public void processResponse(String response){
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response, MovieList.class);

        if(movieList != null){
            int countMovie = movieList.boxOfficeResult.dailyBoxOfficeList.size();
            println("박스오피스 타입"+movieList.boxOfficeResult.boxofficeType);
            println("응답받은 영화 개수: "+countMovie);
        }
    }




    public void println(String data){
        tv.append(data+"\n");
    }
}
