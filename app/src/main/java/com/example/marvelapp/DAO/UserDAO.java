package com.example.marvelapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marvelapp.Conection;
import com.example.marvelapp.Usuario;

public class UserDAO {
    private final Conection conection;
    private final SQLiteDatabase database;

    public UserDAO(Context context) {
        conection = new Conection(context);
        database = conection.getWritableDatabase();
    }

    public long insertUser(Usuario user) {
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("UserPassword", user.getUserPassword());

        return database.insert("tbUser", null, values);
    }

    public Usuario selectUserByName(String name) {
        Usuario user = new Usuario();
        Cursor cursor = database.query("tbUser",
                new String[]{
                        "UserCode, " +
                                "UserName, " +
                                "UserPassword"
                },
                "UserName = ?",
                new String[]{name},
                null,
                null,
                null,
                String.valueOf(1));

        while (cursor.moveToNext()) {
            user.setUserCode(cursor.getString(0));
            user.setUserName(cursor.getString(1));
            user.setUserPassword(cursor.getString(2));
        }

        return user;
    }

    public long updateUser(Usuario user) {
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("UserPassword", user.getUserPassword());

        return database.update("tbUser", values, "userCode = ?", new String[]{user.getUserCode()});
    }

    public Boolean checkLogin(String name, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM tbUser WHERE userName = ? AND userPassword = ?", new String[]{name, password});

        return cursor.getCount() > 0;
    }
}
