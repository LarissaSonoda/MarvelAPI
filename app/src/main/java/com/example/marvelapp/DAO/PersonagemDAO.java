package com.example.marvelapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marvelapp.Conection;
import com.example.marvelapp.Personagens;

public class PersonagemDAO {
    private final Conection conection;
    private final SQLiteDatabase database;

    public PersonagemDAO(Context context) {
        conection = new Conection(context);
        database = conection.getWritableDatabase();
    }

    public long insertPerso(Personagens personagens) {
        ContentValues values = new ContentValues();
        values.put("NameCharacter", personagens.getCharacterName());
        return database.insert("tbPerso", null, values);
    }

    public Personagens selectPerso(String name) {
        Personagens personagens = new Personagens();
        Cursor cursor = database.query("tbUser",
                new String[]{
                        "IdPerso, " +
                                "NameCharacter"
                },
                "NameCharacter = ?",
                new String[]{name},
                null,
                null,
                null,
                String.valueOf(1));

        while (cursor.moveToNext()) {
            personagens.setCharacterCode(cursor.getString(0));
            personagens.setCharacterName(cursor.getString(1));
        }

        return personagens;
    }

}
