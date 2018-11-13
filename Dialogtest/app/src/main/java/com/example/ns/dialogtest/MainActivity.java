package com.example.ns.dialogtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        //프레그먼트를이용한 dialog생성하기
        Button btNoticeDialog = findViewById(R.id.btNoticefg);
        btNoticeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment noticedialogfrag = new NoticeDialogFragment();
                noticedialogfrag.setCancelable(false);
                noticedialogfrag.show(getSupportFragmentManager(), "noticedialogfrag");  //스트링은 그냥 관리하기위한이름임 아무거나해도상관없음

            }
        });

        final DialogFragment listdialog = new ListDialogFragment();  //버튼을 누를때마다가아닌 한번만 생성하는게 좋을거같음!
        //리스트버튼

        Button btlistdialog = findViewById(R.id.btlistdialog);
        btlistdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listdialog.setCancelable(false);
                listdialog.show(getSupportFragmentManager(), "listdialogfrag");  //스트링은 그냥 관리하기위한이름임 아무거나해도상관없음

            }
        });


    }

    //프레그먼트dialog클래스생성
    public static class NoticeDialogFragment extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setMessage("Time out!!")
            .setTitle("Notice")
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
                    .setCancelable(false);  //만드는곳이지 관리하진않음 -> fragment의 컨테이너에서 할거냐말거냐해야함
            return adBuilder.create();
         //   return super.onCreateDialog(savedInstanceState);
        }
    }


    //list dialog
    public static class ListDialogFragment extends DialogFragment{
        final CharSequence[] items = {"Red", "Green", "Blue"};
        boolean[] checkitem =  {false,true,false}; //checkboxlist의 초기값을위한 저장 item수와같아야함
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            //adBuilder.setMessage("Time out!!")
            adBuilder.setTitle("list")   //버튼영역 컨텐트영역 타이틀영역으로 되어있음 아이템을 추가하면 컨텐츠영역의 메시지와 겹침 ->아이템안뜸
            .setIcon(R.mipmap.ic_launcher)
            /*.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { //whitch가 리스트의 뭐가눌렷는지반환해주는것
                    Toast.makeText(getActivity(), items[which], Toast.LENGTH_SHORT).show();
                }
            })*/

            /*  라디오버튼
            .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), items[which], Toast.LENGTH_SHORT).show();
                }
            })*/

            .setMultiChoiceItems(items, checkitem, new DialogInterface.OnMultiChoiceClickListener() {  //여러개를 고를수있기떄문에 핸들러가다르다
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    String str = (isChecked)? "check!" : "uncheck!";
                    Toast.makeText(getActivity(), items[which]+str, Toast.LENGTH_SHORT).show();

                }
            })
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setCancelable(false);  //만드는곳이지 관리하진않음 -> fragment의 컨테이너에서 할거냐말거냐해야함
            return adBuilder.create();

           // return super.onCreateDialog(savedInstanceState);
        }
    }



}
