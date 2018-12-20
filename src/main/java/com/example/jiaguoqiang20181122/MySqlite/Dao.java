package com.example.jiaguoqiang20181122.MySqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {

    private final SQLiteDatabase db;

    public Dao(Context context){
        Mysqlite mysqlite = new Mysqlite(context);

        db = mysqlite.getWritableDatabase();
    }

    public void insertData(String murl, String s) {
        //先删除再添加
        db.delete("yuekao","url=?",new String[]{murl});

        //添加到数据库
        ContentValues values = new ContentValues();

        values.put("url",murl);
        values.put("title",s);
        long insert = db.insert("yuekao", null, values);

        Log.e("zzz","insert=="+insert);
    }


    //查询
    public String queryData(String murl) {
        String title="";

        Cursor cursor = db.query("yuekao", null, "url=?", new String[]{murl}, null, null, null);

        while (cursor.moveToNext()){
             title = cursor.getString(cursor.getColumnIndex("title"));
        }

        return title;
    }
}
