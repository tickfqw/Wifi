package com.example.tick.wifi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tick on 2016/7/18.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DBNAME="account.db";
    public DBOpenHelper(Context context){
        super(context,DBNAME,null,VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table tb_wifi(name varchar(20),password varchar(20))");
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
