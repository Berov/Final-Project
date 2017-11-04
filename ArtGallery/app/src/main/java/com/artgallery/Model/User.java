package com.artgallery.Model;

import android.graphics.Bitmap;

import com.artgallery.Util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Berov on 28.10.2017 г..
 */


public class User implements Serializable {
    private String name;
    private String email;
    private String password;
    private double wallet;
    private String phoneNumber;
    private byte[] userImageBytes = null;
    private String address;
    private boolean isAdmin = false;
    private boolean hasNewSale = false;
//    private ArrayList<Item> itemsForSale;
//    private ArrayList<Item> soldItems;
//    private ArrayList<Item> boughtItems;

    private ArrayList<Item> userItems;
    private int id;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String phoneNumber, String address, double wallet, int hasNewSaleAsInteger) {
        //TODO validations
        this(email, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isAdmin = false;
        this.wallet = wallet;
//        this.itemsForSale = new ArrayList<>();
//        this.soldItems = new ArrayList<>();
//        this.boughtItems = new ArrayList<>();
        this.userItems = new ArrayList<>();
        this.hasNewSale = false;

        if (hasNewSaleAsInteger != 0) {
            this.hasNewSale = true;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getWallet() {
        return Util.twoDecimalPlaces(wallet);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getId() {
        return id;
    }

    public byte[] getUserImageBytes() {
        return this.userImageBytes;
    }

//    public List<Item> getItemsForSale() {
//        return Collections.unmodifiableList(itemsForSale);
//    }
//
//    public List<Item> getSoldItems() {
//        return Collections.unmodifiableList(soldItems);
//    }
//
//    public List<Item> getBoughtItems() {
//        return Collections.unmodifiableList(boughtItems);
//    }

    public void addNewItem(Item newItem) {
        userItems.add(newItem);
//        boughtItems.add(newItem);
    }

    public void setAdmin(boolean b) {
        this.isAdmin = b;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public void setItemsForSale(ArrayList<Item> itemsForSale) {
//        this.itemsForSale = itemsForSale;
//    }
//
//    public void setSoldItems(ArrayList<Item> soldItems) {
//        this.soldItems = soldItems;
//    }
//
//    public void setBoughtItems(ArrayList<Item> boughtItems) {
//        this.boughtItems = boughtItems;
//    }

    public void setUserImageBytes(byte[] userImageBytes) {
        this.userImageBytes = userImageBytes;
    }

//    public void addItemForSale(Item item) {
//        itemsForSale.add(item);
//    }

    public void addItem(Item item) {
        userItems.add(item);
    }

    public void setSaleFlag(boolean saleFlag) {
        this.hasNewSale = saleFlag;
    }

    public boolean getSaleFlag() {
        return hasNewSale;
    }

    public int getSaleFlagAsInteger() {

        int flag = 0;

        if (hasNewSale) {
            flag = 1;
        }
        return flag;
    }
}

