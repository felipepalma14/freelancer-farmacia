package com.farmacia.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Felipe Palma on 21/06/2019.
 */
public class SQLHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "farmacia.db";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Clientes (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT NOT NULL, Sexo TEXT, UF TEXT NOT NULL, Vip INTEGER NOT NULL);";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
