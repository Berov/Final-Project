package com.artgallery.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Created by Berov on 28.10.2017 г..
 */

public class Item implements Serializable {

    private String title;
    private String description;
    private double price;
    private String author;
    private int owner_id;
    private int subtype_id;
    private int buyer_id = 0;
    private int id;
    private byte[] picture;

    public Item(int subtype_id, String title, String description, double price, String author, int owner_id, byte[] picture, int buyer_id) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.author = author;
        this.owner_id = owner_id;
        this.picture = picture;
        this.subtype_id = subtype_id;
        if (buyer_id != 0) {
            this.buyer_id = buyer_id;
        }
    }

    public Item(int id, int subtype_id, String title, String description, double price, String author, int owner_id, byte[] picture, int buyer_id) {

        this(subtype_id, title, description, price, author, owner_id, picture, buyer_id);
        this.id = id;
    }


    public String getTitle() {

        return title;
    }

    public String getDescription() {

        return description;
    }

    public double getPrice() {

        return price;
    }

    public String getAuthor() {

        return author;
    }

    public int getOwnerID() {

        return owner_id;
    }

    public int getId() {

        return id;
    }

    public int getBuyerID() {

        return buyer_id;
    }

    public byte[] getBytePicture() {

        return picture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubtypeID() {

        return subtype_id;
    }

    public Bitmap getImage() {

        return BitmapFactory.decodeByteArray(picture, 0, picture.length);
    }

    public void setBuyerID(int buyerID) {

        this.buyer_id = buyerID;
    }
}
