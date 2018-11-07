package com.example.ns.dialogtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btDialog = findViewById(R.id.button);
        btDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this);//getaplicationcontext, getactivity, this중 엑티비티를 가르켜야함 this면 리스너를가르킴 , mainactivity.this
                adBuilder.setMessage("Time out!!");
                adBuilder.setTitle("Notice");
                adBuilder.setIcon(R.mipmap.ic_launcher);  //아이콘은 title영역에 포함됌
                adBuilder.show(); //*/

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Time out!!")
                        .setTitle("Notice")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() { //버튼눌렀을때 어떤일을할건지
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //비워놓으면 dialog 사라지게 하는역할
                                //버튼을달았으니 버튼을통해서만 닫기를원함
                            }
                        })
                        .setNegativeButton("cancel",null)
                        .setCancelable(false) //취소는 버튼을통해서만 없앨수잇음
                        .show();
            }
        });
    }
}
