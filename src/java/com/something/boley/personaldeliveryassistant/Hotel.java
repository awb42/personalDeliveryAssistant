/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.something.boley.personaldeliveryassistant;

public class Hotel {
    String _fname;
    int _id;
    byte[] _img;

    public Hotel() {
    }

    public Hotel(int n, String string2, byte[] arrby) {
        this._id = n;
        this._fname = string2;
        this._img = arrby;
    }

    public Hotel(String string2, byte[] arrby) {
        this._fname = string2;
        this._img = arrby;
    }

    public String getFName() {
        return this._fname;
    }

    public int getID() {
        return this._id;
    }

    public byte[] getImage() {
        return this._img;
    }

    public void setFName(String string2) {
        this._fname = string2;
    }

    public void setID(int n) {
        this._id = n;
    }

    public void setImage(byte[] arrby) {
        this._img = arrby;
    }
}

