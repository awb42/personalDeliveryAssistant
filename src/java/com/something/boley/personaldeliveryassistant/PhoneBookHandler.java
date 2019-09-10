/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.something.boley.personaldeliveryassistant.PhoneBook;
import com.something.boley.personaldeliveryassistant.SQLiteHelper;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookHandler {
    private SQLiteHelper dbHelper;

    public PhoneBookHandler(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    int addContact(PhoneBook phoneBook) {
        SQLiteDatabase sQLiteDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", phoneBook.getName());
        contentValues.put("phone_number", phoneBook.getPhoneNumber());
        contentValues.put("address", phoneBook.getAddress());
        contentValues.put("total", phoneBook.getOrderTotal());
        contentValues.put("amountReceived", phoneBook.getAmountReceived());
        contentValues.put("tip", phoneBook.getTip());
        contentValues.put("mileage", phoneBook.getMileage());
        contentValues.put("grandTotal", phoneBook.getGrandTotal());
        long l = sQLiteDatabase.insert("contacts", null, contentValues);
        sQLiteDatabase.close();
        return (int)l;
    }

    public void deleteContact(PhoneBook phoneBook) {
        SQLiteDatabase sQLiteDatabase = this.dbHelper.getWritableDatabase();
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = stringBuilder.append("id").append(" = ?").toString();
        String[] arrstring = new String[]{String.valueOf((int)phoneBook.getID())};
        sQLiteDatabase.delete("contacts", string2, arrstring);
        sQLiteDatabase.close();
    }

    public List<PhoneBook> getAllContacts() {
        SQLiteDatabase sQLiteDatabase = this.dbHelper.getWritableDatabase();
        ArrayList arrayList = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder().append("SELECT  * FROM ");
        Cursor cursor = sQLiteDatabase.rawQuery(stringBuilder.append("contacts").toString(), null);
        if (cursor.moveToFirst()) {
            do {
                PhoneBook phoneBook = new PhoneBook();
                phoneBook.setID(Integer.parseInt((String)cursor.getString(0)));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhoneNumber(cursor.getString(2));
                phoneBook.setAddress(cursor.getString(3));
                phoneBook.setOrderTotal(cursor.getString(4));
                phoneBook.setAmountReceived(cursor.getString(5));
                phoneBook.setTip(cursor.getString(6));
                phoneBook.setMileage(cursor.getString(7));
                phoneBook.setGrandTotal(cursor.getString(8));
                arrayList.add((Object)phoneBook);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    PhoneBook getContact(int n) {
        SQLiteDatabase sQLiteDatabase = this.dbHelper.getReadableDatabase();
        String[] arrstring = new String[]{"id", "name", "phone_number", "address", "total", "amountReceived", "tip", "mileage", "grandTotal"};
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = stringBuilder.append("id").append("=?").toString();
        String[] arrstring2 = new String[]{String.valueOf((int)n)};
        Cursor cursor = sQLiteDatabase.query("contacts", arrstring, string2, arrstring2, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new PhoneBook(Integer.parseInt((String)cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
    }

    public int getContactsCount() {
        StringBuilder stringBuilder = new StringBuilder().append("SELECT  * FROM ");
        String string2 = stringBuilder.append("contacts").toString();
        Cursor cursor = this.dbHelper.getReadableDatabase().rawQuery(string2, null);
        cursor.close();
        return cursor.getCount();
    }

    public float sumTotalColumn() {
        StringBuilder stringBuilder = new StringBuilder().append("SELECT SUM(grandTotal) FROM ");
        String string2 = stringBuilder.append("contacts").toString();
        Cursor cursor = this.dbHelper.getReadableDatabase().rawQuery(string2, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getFloat(0);
    }

    public int updateContact(PhoneBook phoneBook) {
        SQLiteDatabase sQLiteDatabase = this.dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", phoneBook.getName());
        contentValues.put("phone_number", phoneBook.getPhoneNumber());
        contentValues.put("address", phoneBook.getAddress());
        contentValues.put("total", phoneBook.getOrderTotal());
        contentValues.put("amountReceived", phoneBook.getAmountReceived());
        contentValues.put("tip", phoneBook.getTip());
        contentValues.put("mileage", phoneBook.getMileage());
        contentValues.put("grandTotal", phoneBook.getGrandTotal());
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = stringBuilder.append("id").append(" = ?").toString();
        String[] arrstring = new String[]{String.valueOf((int)phoneBook.getID())};
        return sQLiteDatabase.update("contacts", contentValues, string2, arrstring);
    }
}

