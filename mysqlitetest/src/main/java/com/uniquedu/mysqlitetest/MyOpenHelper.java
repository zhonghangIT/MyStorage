package com.uniquedu.mysqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ZhongHang on 2015/10/31.
 */
public class MyOpenHelper extends SQLiteOpenHelper{
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //1应用的引用 2name数据库的名称Database的名称 3一般传null 4version 数据库的版本号
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            //创建数据库时调用，数据库升级时不会调用
        Log.d("createDatabase","调用了创建数据库的方法onCreate");
        //创建表的语句放置的位置 sqlite3特点弱数据类型
        String sql="create table if not exists student(_id integer primary key autoincrement,name varchar(20),sex varchar(20),age integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库升级时调用
        Log.d("createDatabase","调用升级数据库的方法onUpgrade");
    }
}
