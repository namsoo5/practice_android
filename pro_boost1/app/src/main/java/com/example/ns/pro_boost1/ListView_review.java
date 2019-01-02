package com.example.ns.pro_boost1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ns.pro_boost1.comment.CommentList;

import java.util.ArrayList;

public class ListView_review extends ArrayAdapter {

    private Context context;
    ArrayList<CommentList> commentLists;
    String createurl = "http://boostcourse-appapi.connect.or.kr:10000/movie/increaseRecommend?review_id=";


    public ListView_review(Context context, int resource, ArrayList<CommentList> commentLists){
        super(context, resource);
        this.context = context;

        this.commentLists = commentLists;
    }
    @Override
    public int getCount() {
        return commentLists.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(context);
        }

        View rowView = View.inflate(context, R.layout.listview_review, null);

        RatingBar ratingBar = rowView.findViewById(R.id.ratingbar_listreview);
        TextView note = rowView.findViewById(R.id.tv_listreview_note);
        TextView id = rowView.findViewById(R.id.tv_listreview_id);
        TextView date = rowView.findViewById(R.id.tv_listreview_time);
        TextView recommend = rowView.findViewById(R.id.tv_listreview_recommend);


        ratingBar.setRating(commentLists.get(position).rating);
        note.setText(commentLists.get(position).contents);
        id.setText(commentLists.get(position).writer);
        date.setText(commentLists.get(position).time);
        recommend.setText("추천 "+commentLists.get(position).recommend);

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int review_id = commentLists.get(position).id;
                sendCommentRequest(review_id);

                AlertDialog.Builder adbuilder = new AlertDialog.Builder(context);
                adbuilder.setMessage("추천 되었습니다.")
                        .setPositiveButton("확인", null)
                        .setCancelable(false)
                        .show();

            }
        });


        return rowView;
    }
    public void sendCommentRequest(int id) {
        String url = createurl + id;
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


}
