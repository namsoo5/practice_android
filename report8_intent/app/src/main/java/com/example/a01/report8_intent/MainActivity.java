package com.example.a01.report8_intent;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arraylist;
    ArrayAdapter<String> adapter;
    TextView tv;
    ListView listView;
    static final int RESULT_CODE_INPUT0 = 1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {  //의도치않은 종료시 값저장
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("arraylist",arraylist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_add = findViewById(R.id.bt_main_add);
        tv = findViewById(R.id.tv_main);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                startActivityForResult(intent, RESULT_CODE_INPUT0);  //결과값을 받아오는 인텐트 실행
            }
        });
        listView = findViewById(R.id.lv);
        arraylist = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        listView.setAdapter(adapter);

        if(savedInstanceState != null) {   //의도치않은 종료시 다시 만들어준다
            arraylist = savedInstanceState.getStringArrayList("arraylist");
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //인텐트의 결과를 받는함수
        switch (requestCode){
            case RESULT_CODE_INPUT0:
                arraylist.add(data.getStringExtra("data"));
                adapter.notifyDataSetChanged();
        }
    }
}
