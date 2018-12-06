package com.example.ns.activitytest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int REQ_CODE_SUBACTIVITY = 0;
    TextView tv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView2);
        Button bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                intent.putExtra("mainString", tv.getText().toString());  //인텐트에 값추가
                startActivityForResult(intent, REQ_CODE_SUBACTIVITY);  // 인텐트가 하나뿐만아니라 다른엑티비티들도 실행할수있으므로
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //resultcode = result_ok, result_canceled (setResult값)
                                                                                                //   data = intent 에서넘어온data
                                                                                                //requestCode = 결과를 받고싶으면 startactivity가아니라
                                                                                                // startActivityForResult를 써야함
       // super.onActivityResult(requestCode, resultCode, data);

        //엑티비티를 여러개 호출할경우 스위치문으로 엑티비티마다의 인텐트데이터를 구분해야함
        switch ((requestCode)){
            case REQ_CODE_SUBACTIVITY:  //서브엑티비티호출에서 들어온 데이터를 구하기위함
                if(resultCode == RESULT_OK){
                    tv.setText(data.getStringExtra("et"));
                }
                break;
        }
    }
}
