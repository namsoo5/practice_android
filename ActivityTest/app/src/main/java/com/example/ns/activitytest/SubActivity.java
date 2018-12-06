package com.example.ns.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Button btcancel = findViewById(R.id.bt_subcancel);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED); //넘기는값이없다 생략가능
                finish();
            }
        });
        Button btok = findViewById(R.id.bt_subok);
        final EditText et = findViewById(R.id.editText);

        if(getIntent().getStringExtra("mainString")!=null){  //인텐트에서 보낸값 받아옴
            et.setHint(getIntent().getStringExtra("mainString"));
        }

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();  //다시메인으로돌아가므로 인텐트를 명시하지않는다. 호출하고하는게아니라 데이터를넘겨주기위한 인텐트
                intent.putExtra("et",et.getText().toString());
                setResult(RESULT_OK, intent);  //넘기는 값이 있음
                finish();
            }
        });
    }
}
