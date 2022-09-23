package com.example.marvelapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conection extends SQLiteOpenHelper {
    private static final String name = "db_appMarvel.db";
    private static final int version = 1;
    public static final String PERSONAGEM_TABLE_NAME = "tbPerso";
    public static final String COLUMN_ID_PERSO = "IdPerso";
    public static final String COLUMN_NAME = "NameCharacter";



    public Conection(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE tbUser(" +
                "UserCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "UserName TEXT NOT NULL," +
                "UserPassword TEXT NOT NULL)");
        String CREATE_PERSONAGEM_TABLE = "CREATE TABLE " + PERSONAGEM_TABLE_NAME + "("
                + COLUMN_ID_PERSO + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_PERSONAGEM_TABLE);

        db.execSQL("CREATE TABLE tbComics(" +
                "ComicsCode INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ComicsName TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS tbUser");
        db.execSQL("DROP TABLE IF EXISTS " + PERSONAGEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS tbComics");

        onCreate(db);
    }

    public Cursor getNovaQuery(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }

    // Operações Serviços
    public boolean insertPerso(Personagens personagens) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT OR IGNORE INTO " + PERSONAGEM_TABLE_NAME
                + "("
                + COLUMN_NAME
                + ") VALUES('"
                + personagens.getCharacterName()
                + "')"
        );

        return true;
    }

    public Cursor getDataPersonagem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM "+ PERSONAGEM_TABLE_NAME + " WHERE " + COLUMN_ID_PERSO + " = " + id, null);
        return cursor;
    }

}