/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.Button
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DecimalFormat
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.something.boley.personaldeliveryassistant.PhoneBook;
import com.something.boley.personaldeliveryassistant.PhoneBookHandler;
import java.text.DecimalFormat;
import java.util.List;

public class PhonebookAdapter
extends BaseAdapter {
    private Activity activity;
    private List<PhoneBook> contactList;
    private LayoutInflater inflater;

    public PhonebookAdapter(Activity activity, List<PhoneBook> list) {
        this.activity = activity;
        this.contactList = list;
    }

    public int getCount() {
        return this.contactList.size();
    }

    public Object getItem(int n) {
        return this.contactList.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
        }
        if (view == null) {
            view = this.inflater.inflate(2130968605, null);
        }
        TextView textView = (TextView)view.findViewById(2131558504);
        TextView textView2 = (TextView)view.findViewById(2131558505);
        TextView textView3 = (TextView)view.findViewById(2131558509);
        TextView textView4 = (TextView)view.findViewById(2131558511);
        TextView textView5 = (TextView)view.findViewById(2131558513);
        TextView textView6 = (TextView)view.findViewById(2131558515);
        TextView textView7 = (TextView)view.findViewById(2131558518);
        TextView textView8 = (TextView)view.findViewById(2131558521);
        Button button = (Button)view.findViewById(2131558506);
        PhoneBook phoneBook = (PhoneBook)this.contactList.get(n);
        textView.setText((CharSequence)phoneBook.getName());
        textView2.setText((CharSequence)phoneBook.getPhoneNumber());
        textView3.setText((CharSequence)phoneBook.getAddress());
        textView4.setText((CharSequence)phoneBook.getOrderTotal());
        textView5.setText((CharSequence)phoneBook.getAmountReceived());
        textView6.setText((CharSequence)phoneBook.getTip());
        textView7.setText((CharSequence)phoneBook.getMileage());
        textView8.setText((CharSequence)phoneBook.getGrandTotal());
        ListItemClickListener listItemClickListener = new ListItemClickListener(n, phoneBook);
        button.setOnClickListener((View.OnClickListener)listItemClickListener);
        ((TextView)view.findViewById(2131558516)).setText((CharSequence)("Delivery # " + (n + 1)));
        TextView textView9 = (TextView)view.findViewById(2131558517);
        float f = 0.0f;
        for (int i = 0; i <= n; ++i) {
            String string2 = ((PhoneBook)this.contactList.get(i)).getGrandTotal();
            if (string2 == null || string2.length() <= 0) continue;
            f += Float.valueOf((String)string2).floatValue();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        textView9.setText((CharSequence)("Total: $" + decimalFormat.format((double)f)));
        return view;
    }

    private class ListItemClickListener
    implements View.OnClickListener {
        PhoneBook list;
        int position;

        public ListItemClickListener(int n, PhoneBook phoneBook) {
            this.position = n;
            this.list = phoneBook;
        }

        public void onClick(View view) {
            new PhoneBookHandler((Context)PhonebookAdapter.this.activity).deleteContact(this.list);
            PhonebookAdapter.this.contactList.remove(this.position);
            PhonebookAdapter.this.notifyDataSetChanged();
        }
    }

}

