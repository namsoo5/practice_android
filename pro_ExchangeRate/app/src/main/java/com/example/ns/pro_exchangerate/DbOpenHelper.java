package com.example.ns.pro_exchangerate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {
    public static final  String DATABASE_NAME = "main.db";
    private static final  int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DbHelper mDBHelper;
    private Context mCtx;

    private class DbHelper extends SQLiteOpenHelper{


        public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CreatDB._CREATE0);  //db생성
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+CreatDB._TABLENAME0);
            onCreate(db);
        }
    }

    public  DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DbHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }
    public void close(){
        mDB.close();
    }

    ///////////////insert문

    public long insertColumn(int category, String memo, String money, String exchange){
        ContentValues values = new ContentValues();
        values.put(CreatDB.CATEGORY, category);
        values.put(CreatDB.MEMO, memo);
        values.put(CreatDB.MONEY, money);
        values.put(CreatDB.EXCHANGE, exchange);
        return mDB.insert(CreatDB._TABLENAME0, null, values);
    }

    /////////////select
    public int getCategory(){
        Cursor cursor = mDB.rawQuery("select category from maintable", null);
        if(cursor.moveToFirst()) {
            do {
                return cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        return -1;
    }
    public void getMemo(){

    }

    public void getMoney(){

    }

    public void getExchange(){

    }


    ///////update
    public boolean updateColumn(long id, int category, String memo, String money, String exchange){
        ContentValues values = new ContentValues();
        values.put(CreatDB.CATEGORY, category);
        values.put(CreatDB.MEMO, memo);
        values.put(CreatDB.MONEY, money);
        values.put(CreatDB.EXCHANGE, exchange);
        return mDB.update(CreatDB._TABLENAME0, values, "id="+id, null)>0;
    }

    ///////delete
    public boolean deleteColumn(long id){
        return mDB.delete(CreatDB._TABLENAME0, "id="+id, null)>0;
    }


}
