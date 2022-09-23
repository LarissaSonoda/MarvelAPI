package com.example.marvelapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.marvelapp.Conection;

public class ComicsDAO {
    private final Conection conection;
    private final SQLiteDatabase database;

    public ComicsDAO(Context context) {
        conection = new Conection(context);
        database = conection.getWritableDatabase();
    }


}
