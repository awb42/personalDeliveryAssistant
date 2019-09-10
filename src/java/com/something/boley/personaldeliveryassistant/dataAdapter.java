/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.widget.ArrayAdapter
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.something.boley.personaldeliveryassistant.Hotel;
import com.something.boley.personaldeliveryassistant.TouchImageView;
import java.util.ArrayList;
import java.util.List;

public class dataAdapter
extends ArrayAdapter<Hotel> {
    Context context;
    ArrayList<Hotel> mHotel;

    public dataAdapter(Context context, ArrayList<Hotel> arrayList) {
        super(context, 2130968607, arrayList);
        this.context = context;
        this.mHotel = arrayList;
    }

    private Bitmap convertToBitmap(byte[] arrby) {
        return BitmapFactory.decodeByteArray((byte[])arrby, (int)0, (int)arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        Holder holder;
        final Hotel hotel = (Hotel)this.getItem(n);
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from((Context)this.getContext()).inflate(2130968607, viewGroup, false);
            holder.nameFV = (TextView)view.findViewById(2131558525);
            holder.pic = (TouchImageView)view.findViewById(2131558526);
            view.setTag((Object)holder);
        } else {
            holder = (Holder)view.getTag();
        }
        holder.nameFV.setText((CharSequence)hotel.getFName());
        holder.pic.setImageBitmap(this.convertToBitmap(hotel.getImage()));
        holder.pic.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Dialog dialog = new Dialog(dataAdapter.this.context);
                dialog.getWindow().requestFeature(1);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(1500, 1500);
                layoutParams.addRule(13);
                TouchImageView touchImageView = new TouchImageView(dataAdapter.this.context);
                touchImageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                touchImageView.setImageBitmap(dataAdapter.this.convertToBitmap(hotel.getImage()));
                dialog.addContentView((View)touchImageView, (ViewGroup.LayoutParams)layoutParams);
                dialog.show();
            }
        });
        return view;
    }

    public class Holder {
        TextView nameFV;
        TouchImageView pic;
    }

}

