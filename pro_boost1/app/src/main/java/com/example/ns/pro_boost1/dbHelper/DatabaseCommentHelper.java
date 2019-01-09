package com.example.ns.pro_boost1.dbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ns.pro_boost1.comment.CommentList;

import java.util.ArrayList;

public class DatabaseCommentHelper {
    private static SQLiteDatabase database;
    private static String createTableCommentSql = "create table if not exists comment" +
            "(" +
            "    _id integer primary key autoincrement, " +
            "    id integer, " +
            "    writer text, " +
            "    movieId integer, "+
            "    dateValue text, " +
            "    rating float, " +
            "    contents text, " +
            "    recommend integer" +
            ")";

    //db이름 boost
    public static void openDatabase(Context context, String databaseName) {
        println("openDatabase호출됨");

        try {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            if (database != null) {
                println("데이터베이스 " + databaseName + " 오픈됨.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCommentTable() {
        println("createCommentTable 호출됨 ");

        if (database != null) {
            database.execSQL(createTableCommentSql);  //table 생성
            println("comment 테이블 생성요청됨");
        } else {
            println("db를 먼저 오픈하세요");
        }
    }


    public static void insertComment(ArrayList<CommentList> commentLists) {
        println("insertcomment()호출됨");

        if (database != null) {

            int movie = commentLists.get(0).movieId;
            int existid = selectExistComment(movie);

            Log.d("test", "id값: "+existid+", "+commentLists.get(0).id);
            //이미있는 댓글이면 수행 x
            boolean exist= false;
            if(commentLists.get(0).id == existid)
                exist = true;
            if (!exist) {

                deleteComment();
                createCommentTable();

                for (int i = 0; i < commentLists.size(); i++) {
                    int id = commentLists.get(i).id;
                    String writer = commentLists.get(i).writer;
                    int movieid = commentLists.get(i).movieId;
                    String dateValue = commentLists.get(i).time;
                    float rating = commentLists.get(i).rating;
                    String contents = commentLists.get(i).contents;
                    int recommend = commentLists.get(i).recommend;


                    String sql = "insert into comment(id, writer, movieId, dateValue, rating, contents, recommend) values(?, ?, ?, ?, ?, ?, ?)";
                    Object[] params = {id, writer, movieid, dateValue, rating, contents, recommend};
                    database.execSQL(sql, params);
                }
                println("comment데이터추가완료");
            }
        }
    }

    public static Cursor selectComment(int id) {
        println("selectmovie()호출");

        if (database != null) {
            String sql = "select _id, id, writer, dateValue, rating, contents, recommend from comment where movieId=" + id;
            Cursor cursor = database.rawQuery(sql, null);
            println("comment조회데이터수: " + cursor.getCount());

            return cursor;
        }
        return null;
    }

    public static int selectExistComment(int movie){
        if (database != null) {
            String sql = "select id from comment where movieId=" + movie;
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            if(cursor.getCount()>0)
                return cursor.getInt(0);  //존재
        }
        return 0;
    }

    public static void deleteComment(){
        database.execSQL("drop table if exists comment");  //기존table삭제
        println("테이블 삭제");

    }


    public static void println(String data) {
        Log.d("test", data);
    }
}
