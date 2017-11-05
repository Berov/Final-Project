package com.artgallery.Model;

import com.artgallery.Util.Util;

import java.io.Serializable;
import java.util.ArrayList;

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
    private ArrayList<Item> userItems;
    private int id;

    public User(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String phoneNumber, String address, double wallet, int hasNewSaleAsInteger) {

        this(email, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isAdmin = false;
        this.wallet = wallet;
        this.userItems = new ArrayList<>();
        this.hasNewSale = false;
        this.hasNewSale = (hasNewSaleAsInteger != 0);
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

    public void setUserImageBytes(byte[] userImageBytes) {

        this.userImageBytes = userImageBytes;
    }

    public void setIUserItems(ArrayList<Item> userItems) {

        this.userItems = userItems;
    }

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


    public ArrayList<Item> getItemsForSale() {

        ArrayList<Item> itemsForSale = new ArrayList<>();

        for (int i = 0; i < userItems.size(); i++) {
            if (userItems.get(i).getBuyerID() == 0) {
                itemsForSale.add(userItems.get(i));
            }
        }

        return itemsForSale;
    }


    public ArrayList<Item> getSoldItems() {

        ArrayList<Item> soldItems = new ArrayList<>();

        for (int i = 0; i < userItems.size(); i++) {
            if (userItems.get(i).getBuyerID() != 0 && userItems.get(i).getBuyerID() != id) {
                soldItems.add(userItems.get(i));
            }
        }

        return soldItems;
    }


    public ArrayList<Item> getBoughtItems() {

        ArrayList<Item> boughtItems = new ArrayList<>();

        for (int i = 0; i < userItems.size(); i++) {
            if (userItems.get(i).getBuyerID() == id) {
                boughtItems.add(userItems.get(i));
            }
        }

        return boughtItems;
    }
}

