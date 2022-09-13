package com.example.marvelapp.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marvelapp.Conection;
import com.example.marvelapp.User;
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
        values.put("UserEmail", user.getUserEmail());
        values.put("UserPassword", user.getUserPassword());
        values.put("UserImage", user.getUserImage());

        return database.insert("tbUser", null, values);
    }

    public Usuario selectUserByEmail(String email) {
        Usuario user = new Usuario();
        Cursor cursor = database.query("tbUser",
                new String[]{
                        "UserCode, " +
                                "UserName, " +
                                "UserEmail, " +
                                "UserPassword," +
                                "UserImage"
                },
                "UserEmail = ?",
                new String[]{email},
                null,
                null,
                null,
                String.valueOf(1));

        while (cursor.moveToNext()) {
            user.setUserCode(cursor.getString(0));
            user.setUserName(cursor.getString(1));
            user.setUserEmail(cursor.getString(2));
            user.setUserPassword(cursor.getString(3));
            user.setUserImage(cursor.getBlob(4));
        }

        return user;
    }

    public long updateUser(Usuario user) {
        ContentValues values = new ContentValues();
        values.put("UserName", user.getUserName());
        values.put("UserEmail", user.getUserEmail());
        values.put("UserPassword", user.getUserPassword());
        values.put("UserImage", user.getUserImage());

        return database.update("tbUser", values, "userCode = ?", new String[]{user.getUserCode()});
    }

    public Boolean checkLogin(String email, String password) {
        Cursor cursor = database.rawQuery("SELECT * FROM tbUser WHERE userEmail = ? AND userPassword = ?", new String[]{email, password});

        return cursor.getCount() > 0;
    }
}
