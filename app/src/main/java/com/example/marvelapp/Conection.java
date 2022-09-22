package com.example.marvelapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conection extends SQLiteOpenHelper {
    private static final String name = "db_appMarvel.db";
    private static final int version = 1;

    public Conection(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE tbUser(" +
                "UserCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserName TEXT NOT NULL," +
                "UserPassword TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS tbUser");

        onCreate(db);
    }

    public Cursor getNovaQuery(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }
}