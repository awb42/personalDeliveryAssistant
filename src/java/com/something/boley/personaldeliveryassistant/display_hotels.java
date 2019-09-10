/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.os.Bundle
 *  android.support.v7.app.AppCompatActivity
 *  android.view.View
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Comparator
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.something.boley.personaldeliveryassistant.DatabaseHandler;
import com.something.boley.personaldeliveryassistant.Hotel;
import com.something.boley.personaldeliveryassistant.MainActivity;
import com.something.boley.personaldeliveryassistant.MapsMainActivity;
import com.something.boley.personaldeliveryassistant.dataAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class display_hotels
extends AppCompatActivity {
    private Bitmap bp;
    private dataAdapter data;
    private Hotel dataModel;
    private DatabaseHandler db;
    private String f_name;
    private EditText fname;
    private ListView lv;
    private ArrayList<Hotel> mHotel;
    private byte[] photo;
    private ImageView pic;

    public void buttonClickBackToMain(View view) {
        this.startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
    }

    public void buttonClickFunction(View view) {
        this.startActivity(new Intent(this.getApplicationContext(), MapsMainActivity.class));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968606);
        this.lv = (ListView)this.findViewById(2131558524);
        this.db = new DatabaseHandler((Context)this);
        this.pic = (ImageView)this.findViewById(2131558498);
        this.fname = (EditText)this.findViewById(2131558499);
        this.data = new dataAdapter((Context)this, (ArrayList<Hotel>)new ArrayList(this.db.getAllHotels()));
        this.data.sort((Comparator)new Comparator<Hotel>(){

            public int compare(Hotel hotel, Hotel hotel2) {
                return hotel.getFName().compareTo(hotel2.getFName());
            }
        });
        this.data.notifyDataSetChanged();
        this.lv.setAdapter((ListAdapter)this.data);
    }

}

