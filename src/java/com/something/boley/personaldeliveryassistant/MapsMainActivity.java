/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.net.Uri
 *  android.os.Bundle
 *  android.support.v7.app.AppCompatActivity
 *  android.text.Editable
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.ListView
 *  android.widget.Toast
 *  java.io.ByteArrayOutputStream
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.something.boley.personaldeliveryassistant.DatabaseHandler;
import com.something.boley.personaldeliveryassistant.Hotel;
import com.something.boley.personaldeliveryassistant.dataAdapter;
import com.something.boley.personaldeliveryassistant.display_hotels;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MapsMainActivity
extends AppCompatActivity {
    private Bitmap bp;
    private dataAdapter data;
    private Hotel dataModel;
    private DatabaseHandler db;
    private String f_name;
    private EditText fname;
    private ListView lv;
    private byte[] photo;
    private ImageView pic;

    private void addHotel() {
        this.getValues();
        this.db.addHotels(new Hotel(this.f_name, this.photo));
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Saved successfully", (int)0).show();
    }

    private void getValues() {
        this.f_name = this.fname.getText().toString();
        this.photo = this.profileImage(this.bp);
    }

    private byte[] profileImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, (OutputStream)byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void showRecords() {
        final ArrayList arrayList = new ArrayList(this.db.getAllHotels());
        this.data = new dataAdapter((Context)this, (ArrayList<Hotel>)arrayList);
        this.data.notifyDataSetChanged();
        this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                MapsMainActivity.this.dataModel = (Hotel)arrayList.get(n);
                Toast.makeText((Context)MapsMainActivity.this.getApplicationContext(), (CharSequence)String.valueOf((int)MapsMainActivity.this.dataModel.getID()), (int)0).show();
            }
        });
    }

    public void buttonClicked(View view) {
        switch (view.getId()) {
            default: {
                return;
            }
            case 2131558502: {
                if (this.fname.getText().toString().trim().equals((Object)"")) {
                    Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Name is empty, Enter name", (int)1).show();
                    return;
                }
                this.addHotel();
                return;
            }
            case 2131558501: {
                this.startActivity(new Intent(this.getApplicationContext(), display_hotels.class));
                return;
            }
            case 2131558498: 
        }
        this.selectImage();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Bitmap decodeUri(Uri uri, int n) {
        int n2;
        int n3;
        int n4;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream((InputStream)this.getContentResolver().openInputStream(uri), null, (BitmapFactory.Options)options);
            n3 = options.outWidth;
            n4 = options.outHeight;
            n2 = 1;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        do {
            if (n3 / 2 < n || n4 / 2 < n) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = n2;
                return BitmapFactory.decodeStream((InputStream)this.getContentResolver().openInputStream(uri), null, (BitmapFactory.Options)options);
            }
            n3 /= 2;
            n4 /= 2;
            n2 *= 2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onActivityResult(int n, int n2, Intent intent) {
        switch (n) {
            default: {
                return;
            }
            case 2: {
                Uri uri;
                if (n2 != -1 || (uri = intent.getData()) == null) return;
                this.bp = this.decodeUri(uri, 400);
                this.pic.setImageBitmap(this.bp);
                return;
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968604);
        this.db = new DatabaseHandler((Context)this);
        this.lv = (ListView)this.findViewById(2131558524);
        this.pic = (ImageView)this.findViewById(2131558498);
        this.fname = (EditText)this.findViewById(2131558499);
    }

    public void selectImage() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(intent, 2);
    }

}

