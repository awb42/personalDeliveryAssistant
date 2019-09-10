/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.v7.app.AppCompatActivity
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  java.lang.Class
 *  java.lang.Object
 */
package com.something.boley.personaldeliveryassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.something.boley.personaldeliveryassistant.OrderDetails;
import com.something.boley.personaldeliveryassistant.PrefsActivity;
import com.something.boley.personaldeliveryassistant.display_database;
import com.something.boley.personaldeliveryassistant.display_hotels;

public class MainActivity
extends AppCompatActivity {
    private Button launchDeliveryActivity;

    private void launchActivity() {
        this.startActivity(new Intent((Context)this, OrderDetails.class));
    }

    public void buttonClickFunction(View view) {
        this.startActivity(new Intent(this.getApplicationContext(), display_database.class));
    }

    public void launchMapsActivity(View view) {
        this.startActivity(new Intent(this.getApplicationContext(), display_hotels.class));
    }

    public void launchSettingsActivity(View view) {
        this.startActivity(new Intent(this.getApplicationContext(), PrefsActivity.class));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968603);
        this.launchDeliveryActivity = (Button)this.findViewById(2131558493);
        this.launchDeliveryActivity.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this.launchActivity();
            }
        });
    }

}

