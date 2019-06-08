package com.example.p.jumptime.Model;

import android.support.v4.app.FragmentActivity;

public class FastItemForRecycler {

    private String itemText;
    private int image;
    public FragmentActivity activity;

    public FastItemForRecycler(String itemText, int image, FragmentActivity activity) {

        this.itemText = itemText;
        this.image = image;
        this.activity = activity;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public String getName() {
        return this.itemText;
    }

    public void setName(String name) {
        this.itemText = name;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
