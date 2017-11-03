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
    private int id;
    private byte[] picture;

    public Item(int subtype_id, String title, String description, double price, String author, int owner_id, byte[] picture) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.author = author;
        this.owner_id = owner_id;
        this.picture = picture;
        this.subtype_id = subtype_id;
    }

    public Item(int id, int subtype_id, String title, String description, double price, String author, int owner_id, byte[] picture) {
        this(subtype_id, title, description, price, author, owner_id, picture);
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
//        Bitmap bm = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        return BitmapFactory.decodeByteArray(picture, 0, picture.length);
    }

    public void setOwnerID(int newOwnerID) {
        owner_id = newOwnerID;
    }
}
