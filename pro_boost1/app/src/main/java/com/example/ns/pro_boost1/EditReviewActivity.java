package com.example.ns.pro_boost1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.ns.pro_boost1.dbHelper.DatabaseCommentHelper;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditReviewActivity extends AppCompatActivity {
    ImageView im_editreview_age;
    int movie_id=0;
    String createurl = "http://boostcourse-appapi.connect.or.kr:10000/movie/createComment?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        TextView tv_editreview_title = findViewById(R.id.tv_editreview_title);
        im_editreview_age = findViewById(R.id.im_editreview_age);

        tv_editreview_title.setText(getIntent().getStringExtra("title"));
        selectImage(getIntent().getIntExtra("age", 0));

        Button bt_save = findViewById(R.id.bt_editreview_save);
        Button bt_cancel = findViewById(R.id.bt_editreview_cancel);
        final EditText et = findViewById(R.id.et_editreview);
        final RatingBar ratingBar = findViewById(R.id.ratingbar_editreview);


        movie_id = getIntent().getIntExtra("id", 0);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String writer = "nam";
                float rating = ratingBar.getRating();
                if(DrawerActivity.status == NetworkStatus.TYPE_NOT_CONNECTED && !EditReviewActivity.this.isFinishing()) {
                    AlertDialog.Builder adbuilder = new AlertDialog.Builder(EditReviewActivity.this);
                    adbuilder.setTitle("통신이 안좋습니다")
                            .setMessage("인터넷과 연결이 되어있지않습니다.")
                            .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }else if(DrawerActivity.status != NetworkStatus.TYPE_NOT_CONNECTED) {
                    sendCommentRequest(movie_id, writer, rating, et.getText().toString());
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public void sendCommentRequest(int id, String writer, float rating, String contents) {

        String url = createurl + "id=" + id + "&writer=" + writer + "&rating=" + rating + "&contents=" + contents;
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {//응답을 문자열로받아서 넣어달라는뜻
                    @Override
                    public void onResponse(String response) {

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


    void selectImage(int age){
        switch (age){
            case 0:
                im_editreview_age.setImageResource(R.drawable.ic_all);
                break;
            case 12:
                im_editreview_age.setImageResource(R.drawable.ic_12);
                break;
            case 15:
                im_editreview_age.setImageResource(R.drawable.ic_15);
                break;
            case 19:
                im_editreview_age.setImageResource(R.drawable.ic_19);
                break;
            default :
                im_editreview_age.setImageResource(R.drawable.ic_all);
                break;
        }
    }

}
