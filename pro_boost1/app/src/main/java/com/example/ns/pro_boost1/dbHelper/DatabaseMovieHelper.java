package com.example.ns.pro_boost1.dbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseMovieHelper {
    private static SQLiteDatabase database;
    private static boolean exist[] = {false, false, false, false, false}; //db없을때 에러방지
    private  static String createTableMovieSql = "create table if not exists movie"+
            "("+
            "    _id integer primary key autoincrement, "+
            "    id integer, "+
            "    title text, "+
            "    title_eng text, "+
            "    dateValue text, "+
            "    user_rating float, "+
            "    audience_rating float, "+
            "    reviewer_rating float, "+
            "    reservation_rate float, "+
            "    reservation_grade integer, "+
            "    grade integer, "+
            "    thumb text, "+
            "    image text"+
            ")";

    //db이름 boost
    public static  void openDatabase(Context context, String databaseName){
        println("openDatabase호출됨");

        try {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            if (database != null) {
                println("데이터베이스 " + databaseName + " 오픈됨.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createMovieTable(){
        println("createmovieTable 호출됨 ");

        if(database !=null){
                database.execSQL(createTableMovieSql);  //table 생성
                println("movie 테이블 생성요청됨");
        }else{
            println("db를 먼저 오픈하세요");
        }
    }

    public static void insertMovie(int id, String title, float reservation_rate, int grade, String date, String image){
        println("insertData()호출됨");

        if(database != null){
            String sql = "insert into movie(id, title, reservation_rate, grade, dateValue, image) values(?, ?, ?, ?, ?, ?)";
            Object[] params = {id, title, reservation_rate, grade, date, image};
            database.execSQL(sql, params);

            exist[id-1] = true;
            println("데이터추가완료");
        }
    }

    public static Cursor selectMovie(int id){
        println("selectmovie()호출");

        if(database!=null && exist[id-1]){
            String sql = "select title, reservation_rate, grade, dateValue, image from movie where id="+id;
            Cursor cursor = database.rawQuery(sql, null);
            println("조회데이터수: "+cursor.getCount());

            return cursor;
        }
        return null;
    }




    public static void println(String data){
        Log.d("test", data);
    }
}
