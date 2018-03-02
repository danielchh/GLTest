package com.danielch.gltest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "Pokemon_DB";
    private static final int VERSION = 1;

    private static final String ID = "id";
    static final String URL = "url";
    static final String NAME = "name";

    private static DBOpenHelper db;

    private DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    static DBOpenHelper getDBHelper(Context context) {
        if (db == null) {
            db = new DBOpenHelper(context);
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DB_NAME + " (" +
                ID + " integer primary key autoincrement," +
                URL + " text," +
                "name text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
