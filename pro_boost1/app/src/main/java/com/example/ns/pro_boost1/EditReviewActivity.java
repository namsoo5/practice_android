package com.example.ns.pro_boost1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EditReviewActivity extends AppCompatActivity {
    ImageView im_editreview_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review);

        TextView tv_editreview_title = findViewById(R.id.tv_editreview_title);
        im_editreview_age = findViewById(R.id.im_editreview_age);

        tv_editreview_title.setText(getIntent().getStringExtra("title"));
        selectImage(getIntent().getIntExtra("age", 0));

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
