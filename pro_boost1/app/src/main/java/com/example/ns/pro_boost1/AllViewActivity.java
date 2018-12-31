package com.example.ns.pro_boost1;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AllViewActivity extends AppCompatActivity {

    final int REQ_CODE_EditReviewActivity2 = 9;

    ListView lv;
    ArrayList<String> al;
    ArrayList<Float> rating;
    ArrayAdapter adapter;
    TextView title;
    ImageView im_age;
    TextView score;
    TextView vote;
    RatingBar ratingBar;
    DecimalFormat pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("한줄평 목록");
        lv = findViewById(R.id.lv_allview_activity);
        al = new ArrayList<String>();
        rating = new ArrayList<Float>();

        al = getIntent().getStringArrayListExtra("al");
        float[] ratingarray;
        ratingarray = getIntent().getFloatArrayExtra("ratingarray");

        for(int i=0; i<ratingarray.length; i++){
            rating.add(ratingarray[i]);
        }

        score = findViewById(R.id.tv_allview_score);
        score.setText(getIntent().getStringExtra("score"));

        im_age = findViewById(R.id.im_allview_age);
        selectImage(getIntent().getIntExtra("age", 0));

        title = findViewById(R.id.tv_allview_title);
        title.setText(getIntent().getStringExtra("title"));

        vote = findViewById(R.id.tv_allview_vote);
        pattern = new DecimalFormat("###,###");
        int num= 0;
        if(al!=null){
            num = al.size();
        }
        String s = pattern.format(num);
        vote.setText("("+s+"명 참여)");

        ratingBar = findViewById(R.id.ratingbar_allview);
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("score")));


        adapter = new ListView_review(this, R.layout.listview_review, al, rating);   //listview review목록
        lv.setAdapter(adapter);

         //앱바에 뒤로가기버튼달기
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button bt_edit = findViewById(R.id.bt_allview_review_edit);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditReviewActivity.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("age", 15);  //관람나이

                startActivityForResult(intent, REQ_CODE_EditReviewActivity2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQ_CODE_EditReviewActivity2:
                if(resultCode==RESULT_OK){
                    al.add(data.getStringExtra("review"));
                    rating.add(data.getFloatExtra("rating",0));
                    adapter.notifyDataSetChanged();
                    updateRating();
                }
                break;

        }
    }
    void updateRating(){
        float sum=0;
        for(int i=0; i<rating.size(); i++){
            sum += (float) rating.get(i);
        }
        sum /= rating.size();
        score.setText(String.format("%.1f", sum));
        ratingBar.setRating(sum);
        String s = pattern.format(rating.size());
        vote.setText("("+s+"명 참여)");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();

                float arr[] = new float[rating.size()];
                for(int i=0; i<rating.size(); i++){
                    arr[i] = rating.get(i);
                }

                intent.putExtra("al", al);
                intent.putExtra("floatarray", arr);

                setResult(RESULT_OK, intent);
                finish();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }


    void selectImage(int age){
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
}
