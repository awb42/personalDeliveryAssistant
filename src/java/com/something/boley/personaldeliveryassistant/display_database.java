/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.support.v7.app.AppCompatActivity
 *  android.view.View
 *  android.widget.Button
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.something.boley.personaldeliveryassistant.PhoneBook;
import com.something.boley.personaldeliveryassistant.PhoneBookHandler;
import com.something.boley.personaldeliveryassistant.PhonebookAdapter;
import java.util.List;

public class display_database
extends AppCompatActivity {
    private PhonebookAdapter adapter;
    private Button add;
    private TextView address;
    private TextView amountReceived;
    private List<PhoneBook> contactList;
    PhoneBookHandler db;
    private TextView grandTotal;
    private ListView listView;
    private TextView mileage;
    private TextView name;
    private TextView number;
    private TextView orderTotal;
    private TextView tip;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968602);
        this.listView = (ListView)this.findViewById(2131558492);
        this.db = new PhoneBookHandler((Context)this);
        this.contactList = this.db.getAllContacts();
        this.adapter = new PhonebookAdapter((Activity)this, this.contactList);
        this.listView.setAdapter((ListAdapter)this.adapter);
    }
}

