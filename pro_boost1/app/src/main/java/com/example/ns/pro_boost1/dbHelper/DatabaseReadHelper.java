package com.example.ns.pro_boost1.dbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ns.pro_boost1.readmovie.ReadMovie;

public class DatabaseReadHelper {
    private static SQLiteDatabase database;
    private  static String createTableReadSql = "create table if not exists read"+
            "("+
            "    _id integer primary key autoincrement, "+
            "    title text, "+
            "    id integer, "+
            "    dateValue text, "+
            "    user_rating float, "+
            "    audience_rating float, "+
            "    reviewer_rating float, "+
            "    reservation_rate float, "+
            "    reservation_grade integer, "+
            "    grade integer, "+
            "    thumb text, "+
            "    image text, "+
            "    photos text, "+
            "    videos text, "+
            "    outlinks text, "+
            "    genre text, "+
            "    duration integer, "+
            "    audience integer, "+
            "    synopsis text, "+
            "    director text, "+
            "    actor text, "+
            "    likes integer, "+
            "    dislikes integer"+
            ")";


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

    public static void createReadTable(){
        println("createreadTable 호출됨 ");

        if(database !=null){
            database.execSQL(createTableReadSql);  //table 생성
            println("read 테이블 생성요청됨");
        }else{
            println("db를 먼저 오픈하세요");
        }
    }

    public static void insertRead(ReadMovie readMovie){
        println("insertread()호출됨");

        if(database != null) {
            String title = readMovie.title.trim();
            int id = readMovie.id;
            String date = readMovie.date.trim();
            float user_rating = readMovie.user_rating;
            float reservation_rate = readMovie.reservation_rate;
            int reservation_grade = readMovie.reservation_grade;
            int grade = readMovie.grade;
            String thumb = readMovie.thumb.trim();
            String genre = readMovie.genre.trim();
            int duration = readMovie.duration;
            int audience = readMovie.audience;
            String synopsis= readMovie.synopsis;
            String director = readMovie.director;
            String actor= readMovie.actor;
            int like= readMovie.like;
            int dislike= readMovie.dislike;

            String sql = "insert into read(id, title, grade, dateValue, genre, duration, likes, dislikes," +
                    " reservation_grade, reservation_rate, user_rating, audience, synopsis, director," +
                    " actor, thumb) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {id, title, grade, date, genre, duration, like, dislike, reservation_grade,
                    reservation_rate, user_rating, audience, synopsis, director, actor, thumb};
            database.execSQL(sql, params);
        }else
        {
            Log.d("test","insert실패");
        }
    }

    public static Cursor selectRead(int id){
        println("selectread()호출");
        if(database!=null ){
            String sql = "select id, title, grade, dateValue, genre, duration, likes, dislikes," +
                    " reservation_grade, reservation_rate, user_rating, audience, synopsis, director," +
                    " actor, thumb from read where id="+id;
            Cursor cursor = database.rawQuery(sql, null);
            println("read조회데이터수: "+cursor.getCount());

            return cursor;
        }
        return null;
    }




    public static void println(String data){
        Log.d("test", data);
    }
}
