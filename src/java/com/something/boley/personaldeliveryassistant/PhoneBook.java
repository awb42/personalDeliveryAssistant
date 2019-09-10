/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.something.boley.personaldeliveryassistant;

public class PhoneBook {
    int _id;
    String address;
    String amountReceived;
    String grandTotal;
    String mileage;
    String name;
    String orderTotal;
    String phoneNumber;
    String tip;

    public PhoneBook() {
    }

    public PhoneBook(int n, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9) {
        this._id = n;
        this.name = string2;
        this.phoneNumber = string3;
        this.address = string4;
        this.orderTotal = string5;
        this.amountReceived = string6;
        this.tip = string7;
        this.mileage = string8;
        this.grandTotal = string9;
    }

    public PhoneBook(String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9) {
        this.name = string2;
        this.phoneNumber = string3;
        this.address = string4;
        this.orderTotal = string5;
        this.amountReceived = string6;
        this.tip = string7;
        this.mileage = string8;
        this.grandTotal = string9;
    }

    public String getAddress() {
        return this.address;
    }

    public String getAmountReceived() {
        return this.amountReceived;
    }

    public String getGrandTotal() {
        return this.grandTotal;
    }

    public int getID() {
        return this._id;
    }

    public String getMileage() {
        return this.mileage;
    }

    public String getName() {
        return this.name;
    }

    public String getOrderTotal() {
        return this.orderTotal;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getTip() {
        return this.tip;
    }

    public void setAddress(String string2) {
        this.address = string2;
    }

    public void setAmountReceived(String string2) {
        this.amountReceived = string2;
    }

    public void setGrandTotal(String string2) {
        this.grandTotal = string2;
    }

    public void setID(int n) {
        this._id = n;
    }

    public void setMileage(String string2) {
        this.mileage = string2;
    }

    public void setName(String string2) {
        this.name = string2;
    }

    public void setOrderTotal(String string2) {
        this.orderTotal = string2;
    }

    public void setPhoneNumber(String string2) {
        this.phoneNumber = string2;
    }

    public void setTip(String string2) {
        this.tip = string2;
    }
}

