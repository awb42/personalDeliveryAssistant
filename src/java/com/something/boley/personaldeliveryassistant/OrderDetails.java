/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.support.v7.app.AppCompatActivity
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.View
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Double
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.text.DecimalFormat
 *  java.util.List
 */
package com.something.boley.personaldeliveryassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.something.boley.personaldeliveryassistant.PhoneBook;
import com.something.boley.personaldeliveryassistant.PhoneBookHandler;
import com.something.boley.personaldeliveryassistant.PhonebookAdapter;
import com.something.boley.personaldeliveryassistant.display_database;
import java.text.DecimalFormat;
import java.util.List;

public class OrderDetails
extends AppCompatActivity {
    private PhonebookAdapter adapter;
    private Button add;
    private TextView address;
    private TextView amountReceived;
    private List<PhoneBook> contactList;
    double dAdd;
    double dAmountReceived;
    double dGrandTotal;
    double dMileage;
    double dMilesDriven;
    double dMultiply;
    double dRatePerMile;
    double dSubtract;
    double dTip;
    double dTotalCost;
    PhoneBookHandler db;
    EditText eAddress;
    EditText eAmountReceived;
    EditText eGrandTotal;
    EditText eMileage;
    EditText eMilesDriven;
    EditText eTip;
    EditText eTotalCost;
    private TextView grandTotal;
    private ListView listView;
    private TextView mileage;
    String milesDriven;
    private TextView name;
    private TextView number;
    private TextView orderTotal;
    String ratePerDelivery;
    String ratePerMile;
    String sAddress;
    String sAmountReceived;
    String sGrandTotal;
    String sMileage;
    String sTip;
    String sTotalCost;
    private TextView tip;

    public void buttonClickFunction(View view) {
        this.startActivity(new Intent(this.getApplicationContext(), display_database.class));
    }

    public void onClick(View view) {
        this.name = (EditText)this.findViewById(2131558541);
        this.number = (EditText)this.findViewById(2131558542);
        this.address = (EditText)this.findViewById(2131558543);
        this.orderTotal = (EditText)this.findViewById(2131558544);
        this.amountReceived = (EditText)this.findViewById(2131558545);
        this.tip = (EditText)this.findViewById(2131558546);
        this.mileage = (EditText)this.findViewById(2131558547);
        this.grandTotal = (EditText)this.findViewById(2131558549);
        String string2 = this.name.getText().toString();
        String string3 = this.number.getText().toString();
        String string4 = this.address.getText().toString();
        String string5 = this.orderTotal.getText().toString();
        String string6 = this.amountReceived.getText().toString();
        String string7 = this.tip.getText().toString();
        String string8 = this.mileage.getText().toString();
        String string9 = this.grandTotal.getText().toString();
        int n = this.db.addContact(new PhoneBook(string2, string3, string4, string5, string6, string7, string8, string9));
        this.contactList.add((Object)new PhoneBook(n, string2, string3, string4, string5, string6, string7, string8, string9));
        this.adapter.notifyDataSetChanged();
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Entry Successfully Created.", (int)1).show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968616);
        this.listView = (ListView)this.findViewById(2131558492);
        this.db = new PhoneBookHandler((Context)this);
        this.contactList = this.db.getAllContacts();
        this.adapter = new PhonebookAdapter((Activity)this, this.contactList);
        this.eAddress = (EditText)this.findViewById(2131558543);
        this.eTotalCost = (EditText)this.findViewById(2131558544);
        this.eAmountReceived = (EditText)this.findViewById(2131558545);
        this.eTip = (EditText)this.findViewById(2131558546);
        this.eMileage = (EditText)this.findViewById(2131558547);
        this.eGrandTotal = (EditText)this.findViewById(2131558549);
        this.eMilesDriven = (EditText)this.findViewById(2131558548);
        this.ratePerDelivery = PreferenceManager.getDefaultSharedPreferences((Context)this).getString("ratePerDeliveryPref", null);
        this.ratePerMile = PreferenceManager.getDefaultSharedPreferences((Context)this).getString("ratePerMilePref", null);
        this.eAmountReceived.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void afterTextChanged(Editable editable) {
                if (OrderDetails.this.getCurrentFocus() == OrderDetails.this.eAmountReceived) {
                    OrderDetails.this.sAmountReceived = OrderDetails.this.eAmountReceived.getText().toString();
                    OrderDetails.this.sTotalCost = OrderDetails.this.eTotalCost.getText().toString();
                    OrderDetails.this.sMileage = OrderDetails.this.eMileage.getText().toString();
                    OrderDetails.this.sTip = OrderDetails.this.eTip.getText().toString();
                    try {
                        OrderDetails.this.dAmountReceived = Double.parseDouble((String)OrderDetails.this.sAmountReceived);
                        OrderDetails.this.dTotalCost = Double.parseDouble((String)OrderDetails.this.sTotalCost);
                        OrderDetails.this.dSubtract = OrderDetails.this.dAmountReceived - OrderDetails.this.dTotalCost;
                        OrderDetails.this.dMileage = Double.parseDouble((String)OrderDetails.this.sMileage);
                        OrderDetails.this.dGrandTotal = OrderDetails.this.dSubtract + OrderDetails.this.dMileage;
                    }
                    catch (NumberFormatException numberFormatException) {}
                    OrderDetails.this.sTip = String.valueOf((double)OrderDetails.this.dSubtract);
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    OrderDetails.this.sTip = decimalFormat.format(OrderDetails.this.dSubtract);
                    OrderDetails.this.eTip.setText((CharSequence)OrderDetails.this.sTip);
                    OrderDetails.this.sGrandTotal = String.valueOf((double)OrderDetails.this.dGrandTotal);
                    OrderDetails.this.sGrandTotal = decimalFormat.format(OrderDetails.this.dGrandTotal);
                    OrderDetails.this.eGrandTotal.setText((CharSequence)OrderDetails.this.sGrandTotal);
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.eTip.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void afterTextChanged(Editable editable) {
                if (OrderDetails.this.getCurrentFocus() == OrderDetails.this.eTip) {
                    OrderDetails.this.sAmountReceived = OrderDetails.this.eAmountReceived.getText().toString();
                    OrderDetails.this.sTotalCost = OrderDetails.this.eTotalCost.getText().toString();
                    OrderDetails.this.sMileage = OrderDetails.this.eMileage.getText().toString();
                    OrderDetails.this.sTip = OrderDetails.this.eTip.getText().toString();
                    try {
                        OrderDetails.this.dTip = Double.parseDouble((String)OrderDetails.this.sTip);
                        OrderDetails.this.dTotalCost = Double.parseDouble((String)OrderDetails.this.sTotalCost);
                        OrderDetails.this.dAdd = OrderDetails.this.dTotalCost + OrderDetails.this.dTip;
                        OrderDetails.this.dMileage = Double.parseDouble((String)OrderDetails.this.sMileage);
                        OrderDetails.this.dGrandTotal = OrderDetails.this.dTip + OrderDetails.this.dMileage;
                    }
                    catch (NumberFormatException numberFormatException) {}
                    OrderDetails.this.sAmountReceived = String.valueOf((double)OrderDetails.this.dAdd);
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    OrderDetails.this.sAmountReceived = decimalFormat.format(OrderDetails.this.dAdd);
                    OrderDetails.this.eAmountReceived.setText((CharSequence)OrderDetails.this.sAmountReceived);
                    OrderDetails.this.sGrandTotal = String.valueOf((double)OrderDetails.this.dGrandTotal);
                    OrderDetails.this.sGrandTotal = decimalFormat.format(OrderDetails.this.dGrandTotal);
                    OrderDetails.this.eGrandTotal.setText((CharSequence)OrderDetails.this.sGrandTotal);
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        if (this.ratePerDelivery != null) {
            this.eMileage.setText((CharSequence)this.ratePerDelivery);
        }
        if (this.ratePerMile != null) {
            this.eMilesDriven.addTextChangedListener(new TextWatcher(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public void afterTextChanged(Editable editable) {
                    if (OrderDetails.this.getCurrentFocus() == OrderDetails.this.eMilesDriven) {
                        OrderDetails.this.sAmountReceived = OrderDetails.this.eAmountReceived.getText().toString();
                        OrderDetails.this.sTotalCost = OrderDetails.this.eTotalCost.getText().toString();
                        OrderDetails.this.sMileage = OrderDetails.this.eMileage.getText().toString();
                        OrderDetails.this.sTip = OrderDetails.this.eTip.getText().toString();
                        try {
                            OrderDetails.this.milesDriven = OrderDetails.this.eMilesDriven.getText().toString();
                            OrderDetails.this.dMilesDriven = Double.parseDouble((String)OrderDetails.this.milesDriven);
                            OrderDetails.this.dRatePerMile = Double.parseDouble((String)OrderDetails.this.ratePerMile);
                            OrderDetails.this.dMultiply = OrderDetails.this.dRatePerMile * OrderDetails.this.dMilesDriven;
                            OrderDetails.this.dGrandTotal = OrderDetails.this.dSubtract + OrderDetails.this.dMultiply;
                        }
                        catch (NumberFormatException numberFormatException) {}
                        OrderDetails.this.sMileage = String.valueOf((double)OrderDetails.this.dMultiply);
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        OrderDetails.this.sMileage = decimalFormat.format(OrderDetails.this.dMultiply);
                        OrderDetails.this.eMileage.setText((CharSequence)OrderDetails.this.sMileage);
                        OrderDetails.this.sGrandTotal = String.valueOf((double)OrderDetails.this.dGrandTotal);
                        OrderDetails.this.sGrandTotal = decimalFormat.format(OrderDetails.this.dGrandTotal);
                        OrderDetails.this.eGrandTotal.setText((CharSequence)OrderDetails.this.sGrandTotal);
                    }
                }

                public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                }

                public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                }
            });
        }
    }

    public void sendMessage(View view) {
        this.eAddress = (EditText)this.findViewById(2131558543);
        this.sAddress = this.eAddress.getText().toString();
        this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)("http://maps.google.com/maps?q=" + this.sAddress))));
    }

}

