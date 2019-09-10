/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 *  java.lang.String
 */
package com.something.boley.personaldeliveryassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper
extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactsManager";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_AMOUNT_RECEIVED = "amountReceived";
    public static final String KEY_CONTACT = "phone_number";
    public static final String KEY_DELIVERY_TOTAL = "total";
    public static final String KEY_GRAND_TOTAL = "grandTotal";
    public static final String KEY_ID = "id";
    public static final String KEY_MILEAGE = "mileage";
    public static final String KEY_NAME = "name";
    public static final String KEY_TIP = "tip";
    protected static final String TABLE_CONTACTS = "contacts";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY,name TEXT,phone_number TEXT, address TEXT, total TEXT, amountReceived TEXT, tip TEXT, mileage TEXT, grandTotal TEXT)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        this.onCreate(sQLiteDatabase);
    }
}

