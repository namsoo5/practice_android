package com.example.ns.report6;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    static boolean checkstatus[] ={false, false, false}; //check상태저장변수
    static int val=0;  //컬러값저장을위한 변수 0:빨강 1:파랑 2:초록
    static Button btColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btvege = findViewById(R.id.btvegetable);    //checkbox dialog
        btvege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment checkfd = new CheckboxDF();
                checkfd.setCancelable(false);
                checkfd.show(getSupportFragmentManager(),"check");
            }
        });

        Button btexit = findViewById(R.id.btexit);                                 // 아무기능없는 1회용 dialog 생성
        btexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Exit")
                        .setMessage("정말 종료하시겠습니까?")
                        .setIcon(R.drawable.warning)
                        .setPositiveButton("yes", null)
                        .setNegativeButton("no", null)
                        .setCancelable(false)
                        .show();
            }
        });

        Button btset = findViewById(R.id.btset);                          //list dialog
        btset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment listdialog = new ListDialogF();
                listdialog.setCancelable(false);
                listdialog.show(getSupportFragmentManager(), "listdf");
            }
        });

        btColor = findViewById(R.id.btColor);
        btColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment radiodf = new RadioDialogF();
                radiodf.setCancelable(false);
                radiodf.show(getSupportFragmentManager(), "radio");
            }
        });
    }

    public static class ListDialogF extends DialogFragment{
        final CharSequence[] lists = {"디스플레이", "핸드폰정보", "계정"};
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("설정목록")
                     .setItems(lists, new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {  //which는 리스트의 어떤것이 눌렷는지 반환함
                             Toast.makeText(getActivity(), lists[which], Toast.LENGTH_SHORT).show();
                         }
                     })
                     .setIcon(R.drawable.set)
                     .setPositiveButton("Back", null);
            return adBuilder.create();
            //return super.onCreateDialog(savedInstanceState);
        }
    }

    public static  class RadioDialogF extends DialogFragment{
        final CharSequence[] lists = {"빨강", "파랑", "초록"};
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Button Color")
                     .setSingleChoiceItems(lists, -1, new DialogInterface.OnClickListener(){   //-1은 디폴트값으로 아무것도 선택안한것
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             Toast.makeText(getActivity(), "Color Change"+ lists[which], Toast.LENGTH_SHORT).show();
                             val = which;  //컬러값저장
                         }
                     })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (val) {  //OK클릭시 색변경
                                case 0:
                                    btColor.setBackgroundColor(Color.RED);
                                    break;
                                case 1:
                                    btColor.setBackgroundColor(Color.BLUE);
                                    break;
                                case 2:
                                    btColor.setBackgroundColor(Color.GREEN);
                                    break;
                            }
                        }
                    });
            return adBuilder.create();
            //return super.onCreateDialog(savedInstanceState);
        }

    }
    public static class CheckboxDF extends DialogFragment {
    final CharSequence[] lists = {"토마토", "오이", "고기"};
    //boolean[] checkstatus = {false, false, false};
    String s;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
        adBuilder.setTitle("먹기싫은것은?")
                .setMultiChoiceItems(lists, checkstatus, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked)
                            checkstatus[which] = true;
                        else
                            checkstatus[which] = false;
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checkstatus[0] && checkstatus[1] && checkstatus[2])
                            s="토마토, 오이, 고기";
                        else if (checkstatus[0] && checkstatus[1])
                            s="토마토, 오이";
                        else if (checkstatus[0] && checkstatus[2])
                            s="토마토, 고기";
                        else if (checkstatus[1] && checkstatus[2])
                            s="오이, 고기";
                        else if (checkstatus[0])
                            s="토마토";
                        else if (checkstatus[1])
                            s="오이";
                        else if (checkstatus[2])
                            s="고기";
                        else
                            s="아무것도 선택하지않았습니다.";
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                });
        return adBuilder.create();
    }
}
}

