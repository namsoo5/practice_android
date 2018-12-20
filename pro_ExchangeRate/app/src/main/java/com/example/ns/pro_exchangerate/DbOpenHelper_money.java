package com.example.ns.pro_exchangerate;
/*
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbOpenHelper_money {
    public static final String DATABASE_NAME = "main.db";
    private static final  int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DbHelper_money mDBHelper;
    private Context mCtx;

    private class DbHelper_money extends SQLiteOpenHelper{

        public DbHelper_money(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreatDB_money._CREATE1) ;  //db생성
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+CreatDB_money._TABLENAME1);
            onCreate(db);
        }
    }
    public DbOpenHelper_money(Context context){this.mCtx = context;}

    public DbOpenHelper_money open() throws SQLException{
        mDBHelper = new DbHelper_money(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }
    public void create(){mDBHelper.onCreate(mDB);}
    public void close(){mDB.close();}

    public String getexchangmoney(String exchange){
        Cursor cursor = mDB.rawQuery("select exchangemoeny from subtable where exchage="+exchange, null);
       /*
        int num=0;
        switch (exchange) {
            case "달러":
                num=0;
                break;
            case "앤":
                num=1;
                break;
            case "위안":
                num=2;
                break;
            case "유로":
                num=3;
                break;
            default :

        }*/
       /* if(cursor.moveToFirst()){
            do{
                return cursor.getString(0);
            }while(cursor.moveToNext());
        }
        return "0";
    }
/*
    public ArrayList<String> getexchange(){
        ArrayList<String> kindof = new ArrayList<String>();
        Cursor cursor = mDB.rawQuery("select exchangemoney from subtable", null);
        if(cursor.moveToFirst()){
            do{
               kindof.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return kindof;
    }
*/
       /*
}
*/