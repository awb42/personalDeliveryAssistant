/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.something.boley.personaldeliveryassistant.Hotel;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler
extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hotelsManager";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_FNAME = "fname";
    private static final String KEY_ID = "id";
    private static final String KEY_PHOTO = "photo";
    private static final String TABLE_HOTELS = "hotels";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void addHotels(Hotel hotel) {
        SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FNAME, hotel.getFName());
        contentValues.put(KEY_PHOTO, hotel.getImage());
        sQLiteDatabase.insert(TABLE_HOTELS, null, contentValues);
        sQLiteDatabase.close();
    }

    public void deleteHotel(int n) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        String[] arrstring = new String[]{String.valueOf((int)n)};
        sQLiteDatabase.delete(TABLE_HOTELS, "id = ?", arrstring);
        sQLiteDatabase.close();
    }

    public List<Hotel> getAllHotels() {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM hotels", null);
        if (cursor.moveToFirst()) {
            do {
                Hotel hotel = new Hotel();
                hotel.setID(Integer.parseInt((String)cursor.getString(0)));
                hotel.setFName(cursor.getString(1));
                hotel.setImage(cursor.getBlob(2));
                arrayList.add((Object)hotel);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE hotels(id INTEGER PRIMARY KEY,fname TEXT,photo BLOB)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS hotels");
        this.onCreate(sQLiteDatabase);
    }

    public int updateHotel(Hotel hotel, int n) {
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FNAME, hotel.getFName());
        contentValues.put(KEY_PHOTO, hotel.getImage());
        String[] arrstring = new String[]{String.valueOf((int)n)};
        return sQLiteDatabase.update(TABLE_HOTELS, contentValues, "id = ?", arrstring);
    }
}

