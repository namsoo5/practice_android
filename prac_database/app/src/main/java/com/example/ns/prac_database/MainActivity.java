package com.example.ns.prac_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database;
    EditText et;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        et4 = findViewById(R.id.editText4);
        et5 = findViewById(R.id.editText5);
        tv = findViewById(R.id.textView);

        Button bt = findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbname = et.getText().toString(); //edittext에서 db이름가져옴
                openDatabase(dbname);
            }
        });

        Button bt2 = findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tablename = et2.getText().toString();
                createTable(tablename);
            }
        });

        Button bt3 = findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et3.getText().toString().trim();
                String ageStr = et4.getText().toString().trim();  //공백시 에러발생가능
                String mobile = et5.getText().toString().trim();

                int age = -1;
                try {
                    age = Integer.parseInt(ageStr); //db에서 int형으로 저장되므로변환
                }catch (Exception e){}

                insertData(name, age, mobile);
            }
        });

        Button bt4 = findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tablename = et2.getText().toString();
                selectData(tablename);
            }
        });


    }


    //db에 추가 조회 삭제하기위해선 db를 먼저 오픈해야함
    public void openDatabase(String dbname) {
        println("openDatabase() 호출");

        //Helper사용
        DatabaseHelper helper = new DatabaseHelper(this, dbname, null, 1);
        database = helper.getWritableDatabase(); //db에쓸수있는권한


        /*  openOrCreateDatabase사용
        database = openOrCreateDatabase(dbname, MODE_PRIVATE, null);
        if(database != null){
            println("데이터베이스 open!");
        }*/
    }

    public void createTable(String tablename){
        println("createTable()호출됌");

        if(database != null){
            String sql = "create table if not exists "+tablename+"(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text);";
            database.execSQL(sql);//sql실행문

            println("테이블생성");
        }else{
            println("먼저 데이터베이스를 오픈하세요");
        }
    }


    public void insertData(String name, int age, String mobile) {
        println("insertData()호출됌");

        if(database != null){
            String sql ="insert into customer(name, age, mobile) values(?, ?, ?)";
            Object[] params = { name, age, mobile };
            database.execSQL(sql, params);   //2번째인자로 ?를 대체함

            println("데이터 추가함");
        }else
        {
            println("먼저 데이터베이스 오픈하세요");
        }
    }

    public void selectData(String tablename) {
        println("selectData() 호출됌");

        if(database != null){
            String sql="select name, age, mobile from "+tablename;
            Cursor cursor = database.rawQuery(sql, null);//결과값이존재하는 쿼리문
            println("조회된 데이터 개수 : "+ cursor.getCount());
            int i = 0;
            while(cursor.moveToNext()){
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);
                println("#"+i+" -> "+name+", "+age+", "+mobile);
                i++;
            }
            /* 방법2
            for(int i=0; i<cursor.getCount(); i++){
                cursor.moveToNext();
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);
                println("#"+i+" -> "+name+", "+age+", "+mobile);
            }
            */
            cursor.close();
        }
    }


    public void println(String data) {
        tv.append(data + "\n");
    }

    class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            println("onCreate()호출됌");
            String tablename = "customer";

            String sql = "create table if not exists " + tablename + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text);";
            db.execSQL(sql);//sql실행문

            println("테이블생성");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  //version바뀔시 호출
            println("onUpgrade 호출됌 : "+oldVersion + ", "+ newVersion);

            if(newVersion>1){
                String tableName = "customer";
                db.execSQL("drop table if exists "+tableName);  //기존db삭제
                println("테이블 삭제");

                String sql = "create table if not exists " + tableName + "(_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text);";
                db.execSQL(sql);//

                println("테이블 재생성");
            }
        }
    }
}
