package com.danielch.gltest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.danielch.gltest.models.PokemonPojo;

import java.util.ArrayList;

public class DBUtils {

    public static boolean isPokemonsInDBAvailable(Context context) {
        SQLiteDatabase db = DBOpenHelper.getDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.DB_NAME, null, null, null, null, null, null);
        boolean hasItems = cursor.moveToFirst();
        cursor.close();
        DBOpenHelper.getDBHelper(context).close();

        return hasItems;
    }

    public static void putPokemonsIntoDB(Context context, ArrayList<PokemonPojo> items) {
        SQLiteDatabase db = DBOpenHelper.getDBHelper(context).getWritableDatabase();

        for (PokemonPojo item :
                items) {
            ContentValues cv = new ContentValues();
            String url = item.getUrl();
            String name = item.getName();
            cv.put(DBOpenHelper.URL, url);
            cv.put(DBOpenHelper.NAME, name);

            db.insert(DBOpenHelper.DB_NAME, null, cv);
        }
        DBOpenHelper.getDBHelper(context).close();
    }

    public static void dropDB(Context context) {
        SQLiteDatabase db = DBOpenHelper.getDBHelper(context).getWritableDatabase();

        db.execSQL("delete from " + DBOpenHelper.DB_NAME);

        DBOpenHelper.getDBHelper(context).close();
    }

    public static ArrayList<PokemonPojo> getAllPokemons(Context context) {
        ArrayList<PokemonPojo> items = new ArrayList<>();
        SQLiteDatabase db = DBOpenHelper.getDBHelper(context).getReadableDatabase();
        Cursor cursor = db.query(DBOpenHelper.DB_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int urlColIndex = cursor.getColumnIndex(DBOpenHelper.URL);
            int nameColIndex = cursor.getColumnIndex(DBOpenHelper.NAME);
            do {
                items.add(new PokemonPojo(cursor.getString(urlColIndex), cursor.getString(nameColIndex)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        DBOpenHelper.getDBHelper(context).close();

        return items;
    }
}
